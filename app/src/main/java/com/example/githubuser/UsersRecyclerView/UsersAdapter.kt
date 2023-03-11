package com.example.githubuser.UsersRecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ItemRowUserBinding

class UsersAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.usernameTextView.text = text
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}