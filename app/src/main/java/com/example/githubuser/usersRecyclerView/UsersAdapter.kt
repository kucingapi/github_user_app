package com.example.githubuser.usersRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.api.User
import com.example.githubuser.databinding.ItemRowUserBinding

class UsersAdapter(private val dataSet: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String, url: String) {
            with(binding) {
                usernameTextView.text = text
                Glide.with(itemView.context)
                    .load(url)
                    .into(profilePictureImageView)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newDataSet: List<User>){
        dataSet.clear()
        dataSet.addAll(newDataSet)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentData = dataSet[position]
        viewHolder.bind(currentData.login ?: "failed", currentData.avatarUrl ?: "failed")
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}