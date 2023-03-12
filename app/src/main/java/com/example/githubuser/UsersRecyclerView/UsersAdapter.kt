package com.example.githubuser.UsersRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.api.ResponseUsers
import com.example.githubuser.api.User
import com.example.githubuser.databinding.ItemRowUserBinding

class UsersAdapter(private val dataSet: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.usernameTextView.text = text
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newDataSet: List<User>){
        dataSet.addAll(newDataSet)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position].login ?: "failed")
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}