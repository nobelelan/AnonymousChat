package com.example.anonymuschat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anonymuschat.R
import com.example.anonymuschat.User
import com.example.anonymuschat.databinding.VerticalUserLayoutBinding

class VerticalUserAdapter(val userList: ArrayList<User>):
    RecyclerView.Adapter<VerticalUserAdapter.VerticalUserViewHolder>() {

    class VerticalUserViewHolder(val binding: VerticalUserLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalUserViewHolder {
        return VerticalUserViewHolder(VerticalUserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: VerticalUserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.textViewUserName.text = currentUser.fullName
        Glide.with(holder.itemView.context).load(currentUser.imageUrl)
            .placeholder(R.color.black)
            .into(holder.binding.imageProfile)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}