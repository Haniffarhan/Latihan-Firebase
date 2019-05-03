package com.haniffarhan.training.pertemuan_14_firebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.google.firebase.database.*
import com.haniffarhan.training.pertemuan_14_firebase.AdapterC.Adapter
import com.haniffarhan.training.pertemuan_14_firebase.Add_Data.Users

class ShowData : AppCompatActivity() {

    lateinit var refDb : DatabaseReference
    lateinit var list : MutableList<Users>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        refDb = FirebaseDatabase.getInstance()
            .getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.list_view_data)

        refDb.addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0!!.exists()){
                        list.clear()
                        for (h in p0.children){
                            val user = h.getValue(Users::class.java)
                            list.add(user!!)
                        }
                        val adapter = Adapter(this@ShowData,
                            R.layout.activity_show_user, list)
                        listView.adapter = adapter
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            }
        )
    }

}