package com.haniffarhan.training.pertemuan_14_firebase.AdapterC

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.haniffarhan.training.pertemuan_14_firebase.Add_Data.Users
import com.haniffarhan.training.pertemuan_14_firebase.R
import com.haniffarhan.training.pertemuan_14_firebase.ShowData

class Adapter(val mCtx: Context, val layoutResId: Int,
              val list: List<Users>): ArrayAdapter<Users>(mCtx,layoutResId,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textName = view.findViewById<TextView>(R.id.text_view_name)
        val textStatus = view.findViewById<TextView>(R.id.text_view_status)
        val buttonUpdate = view.findViewById<Button>(R.id.button_update)
        val buttonDelete = view.findViewById<Button>(R.id.button_delete)

        val user = list[position]

        textName.text = user.name
        textStatus.text = user.status

        buttonUpdate.setOnClickListener {
            showUpdateDialog(user)
        }

        buttonDelete.setOnClickListener {
            DeleteInfo(user)
        }

        return  view
    }

    private fun showUpdateDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.activity_update_data, null)
        val textName = view.findViewById<EditText>(R.id.update_edit_text_name)
        val textStatus = view.findViewById<EditText>(R.id.update_edit_text_status)
        textName.setText(user.name)
        textStatus.setText(user.status)
        builder.setView(view)
        builder.setPositiveButton("Update"){
            dialog, which ->
            val dbUser = FirebaseDatabase.getInstance().getReference("USERS")
            val name = textName.text.toString().trim()
            val ststus = textStatus.text.toString().trim()
            if (name.isEmpty()){
                textName.error = "Please enter name"
                textName.requestFocus()
                return@setPositiveButton
            }
            val user = Users(user.id, name, ststus)

            dbUser.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx, "Update", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No"){
            dialog, which ->
        }

        val alert = builder.create()
        alert.show()

    }

    private fun DeleteInfo(user: Users) {
        val progressDialog = ProgressDialog(context, R.style.AppTheme)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting....")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx, "DELETE!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ShowData::class.java)
        context.startActivity(intent)
        progressDialog.dismiss()
    }

}