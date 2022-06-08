package com.example.firebasefirestore

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    val firestore = FirebaseFirestore.getInstance()
    var userId: String? = null
    var userName: String? = null
    var userCity: String? = null
    var userCountry: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bundle = intent.extras
        if (bundle != null) {
            ButtonSave.text = "UPDATE"
            userId = bundle.getString("user_id")
            userName = bundle.getString("user_name")
            userCity = bundle.getString("user_city")
            userCountry = bundle.getString("user_country")
            EditTextName.text = userName as Editable
            EditTextCity.text = userCity as Editable
            EditTextCountry.text = userCountry as Editable
        } else {
            ButtonSave.text = "SAVE"
        }

        ButtonSeeAll.setOnClickListener {
            startActivity(Intent(this, ShowAllDataActivity::class.java))
        }

        ButtonSave.setOnClickListener {
            val name: String = EditTextName.text.toString()
            val city: String = EditTextCity.text.toString()
            val country: String = EditTextCountry.text.toString()

            val bundle1 = intent.extras
            if (bundle1 != null) {
                val id: String = userId.toString()
                updateData(id, name, city, country)
            } else {
                val id = UUID.randomUUID().toString()
                saveData(id, name, city, country)
            }
        }
    }

    private fun updateData(id: String, name: String, city: String, country: String) {
        firestore.collection("AllUsers").document(id)
            .update("name", name, "city", city, "country", country)
            .addOnCompleteListener {
                Toast.makeText(this, "Successfully updated data", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveData(id: String, name: String, city: String, country: String) {
        if (name.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty()) {
            val user_data: HashMap<String, Any> = HashMap()
            user_data["id"] = id
            user_data["name"] = name
            user_data["city"] = city
            user_data["country"] = country

            firestore.collection("AllUsers").document(id).set(user_data)
                .addOnCompleteListener {
                    Toast.makeText(this, "User data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }


}