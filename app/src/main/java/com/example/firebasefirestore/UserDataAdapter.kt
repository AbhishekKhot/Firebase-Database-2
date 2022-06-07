package com.example.firebasefirestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item.view.*


class UserDataAdapter(val list: List<User>) :
    RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder>() {

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
}