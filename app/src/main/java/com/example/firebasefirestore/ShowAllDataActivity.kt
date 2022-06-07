package com.example.firebasefirestore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show_all_data.*


class ShowAllDataActivity : AppCompatActivity() {
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_data)

        val list = mutableListOf<User>()
        val adapter = UserDataAdapter(list)

        RecyclerView.apply {
            this.hasFixedSize()
            this.layoutManager = LinearLayoutManager(this@ShowAllDataActivity)
            this.adapter = adapter
        }

        firestore.collection("AllUsers").get()
            .addOnCompleteListener {
                list.clear()
                for (snapshot in it.result) {
                    //val user = snapshot.data as User
                    val user: User = User(snapshot.getString("id"), snapshot.getString("name"),
                        snapshot.getString("city"),
                        snapshot.getString("country"))
                    list.add(user)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
    }
}