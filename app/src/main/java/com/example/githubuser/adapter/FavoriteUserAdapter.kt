package com.example.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.fragment.FavoriteUserFragmentDirections

class FavoriteUserAdapter(private val dataSet: ArrayList<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(username: String, url: String) {
            with(binding) {
                usernameTextView.text = username
                Glide.with(itemView.context)
                    .load(url)
                    .into(profilePictureImageView)
                userCard.setOnClickListener {
                    navigateDetailUser(username, it)
                }
            }
        }
        private fun navigateDetailUser(username: String, view: View){
            lateinit var destination: NavDirections
            val toDetailUserFragment =
                FavoriteUserFragmentDirections.actionFavoriteUserFragmentToUserDetailFragment()
            toDetailUserFragment.username = username
            destination = toDetailUserFragment

            view.findNavController().navigate(destination)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newDataSet: List<FavoriteUser>){
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
        viewHolder.bind(currentData.username, currentData.profilePicture)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
