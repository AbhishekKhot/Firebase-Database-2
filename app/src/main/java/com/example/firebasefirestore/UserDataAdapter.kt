package com.example.firebasefirestore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.rv_item.view.*


class UserDataAdapter(val list: MutableList<User>, val context: Activity) :
    RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder>() {

    val firestore = FirebaseFirestore.getInstance()

    inner class UserDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return UserDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        holder.itemView.apply {
            UserName.text = list[position].name
            UserCity.text = list[position].city
            UserCountry.text = list[position].country
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun deleteItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        val item = list[position]
        firestore.collection("AllUsers").document(item.id.toString()).delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, list.size)
                    Snackbar.make(viewHolder.itemView,
                        "Item removed successfully",
                        Snackbar.LENGTH_LONG).setAction("UNDO") {
                        list.add(position, item)
                        notifyItemInserted(position)
                    }.show()
                } else {
                    Snackbar.make(viewHolder.itemView,
                        it.exception.toString(),
                        Snackbar.LENGTH_LONG).show()
                }
            }
    }

    fun updateItem(position: Int) {
        val item = list[position]
        val bundle = Bundle()
        bundle.putString("user_id", item.id)
        bundle.putString("user_name", item.name)
        bundle.putString("user_city", item.city)
        bundle.putString("user_country", item.country)
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}