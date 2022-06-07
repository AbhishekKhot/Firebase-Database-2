package com.example.firebasefirestore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButtonSeeAll.setOnClickListener {
            startActivity(Intent(this, ShowAllDataActivity::class.java))
        }

        ButtonSave.setOnClickListener { v ->
            val name = EditTextName.text.toString()
            val city = EditTextCity.text.toString()
            val country = EditTextCountry.text.toString()
            val id = UUID.randomUUID().toString()

            if (name.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty()) {
                val user_data: HashMap<String, Any> = HashMap()
                user_data["id"] = id
                user_data["name"] = name
                user_data["city"] = city
                user_data["country"] = country

                firestore.collection("AllUsers").document(id).set(user_data)
                    .addOnCompleteListener {
                        Snackbar.make(v,
                            "User data saved successfully to the database",
                            Snackbar.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Snackbar.make(v,
                            "Failed to save data to the database",
                            Snackbar.LENGTH_SHORT).show()
                    }
            } else {
                Snackbar.make(v, "Empty fields are not allowed", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}