package com.haniffarhan.training.pertemuan_14_firebase.Add_Data

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.haniffarhan.training.pertemuan_14_firebase.R
import com.haniffarhan.training.pertemuan_14_firebase.ShowData
import kotlinx.android.synthetic.main.activity_add_data.*

class AddDataAct : AppCompatActivity() {
    lateinit var refDb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        refDb = FirebaseDatabase.getInstance()
            .getReference("USERS")

        button_save.setOnClickListener {
            saveData()
            val intent = Intent(this, ShowData::class.java)
            startActivity(intent)
        }

        button_show_data.setOnClickListener {
            val intent = Intent(this, ShowData::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        val name = edit_text_name.text.toString()
        val status = edit_text_status.text.toString()

        val userId = refDb.push().key.toString()
        val user = Users(userId, name, status)

        refDb.child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this,
                    "SUCCESS", Toast.LENGTH_SHORT).show()
                edit_text_name.setText("")
                edit_text_status.setText("")
            }
    }
}