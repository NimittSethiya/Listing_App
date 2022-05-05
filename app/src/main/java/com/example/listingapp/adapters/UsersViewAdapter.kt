package com.example.listingapp.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listingapp.R
import com.example.listingapp.databinding.UsersProfileBinding
import com.example.listingapp.model.Result
import com.example.listingapp.model.Users

class UsersViewAdapter : RecyclerView.Adapter<UsersViewHolder>() {

    var usersList = mutableListOf<Result>()

    fun setUsers(users: Users) {
        this.usersList = users.results.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = UsersProfileBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val users = usersList[position]
        Glide.with(holder.itemView.context).load(users.picture.large).into(holder.binding.userImage)

        holder.binding.userImage.setOnClickListener{
           val bundle = Bundle()
           bundle.putString("lat",users.location.coordinates.latitude)
           bundle.putString("long",users.location.coordinates.longitude)
            Navigation.findNavController(holder.binding.root).navigate(R.id.action_usersList2_to_weatherReport,bundle)
        }

    }

    override fun getItemCount(): Int {
        return usersList.size
    }



}

class UsersViewHolder(val binding: UsersProfileBinding) : RecyclerView.ViewHolder(binding.root){

}