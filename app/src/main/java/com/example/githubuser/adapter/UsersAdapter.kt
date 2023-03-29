package com.example.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.fragment.UserListDestination
import com.example.githubuser.fragment.UserListFragmentDirections
import com.example.githubuser.api.User
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.fragment.UserDetailFragmentDirections

class UsersAdapter(private val dataSet: ArrayList<User>, private val userListDestination: UserListDestination) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRowUserBinding, private val userListDestination: UserListDestination) :
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
            lateinit var destination:NavDirections
            if (userListDestination == UserListDestination.DETAIL) {
                val toDetailUserFragment =
                    UserListFragmentDirections.actionUserListFragmentToUserDetailFragment()
                toDetailUserFragment.username = username
                destination = toDetailUserFragment
            }
            else {
                val toSelf = UserDetailFragmentDirections.actionUserDetailFragmentSelf()
                toSelf.username = username
                destination = toSelf
            }

            view.findNavController().navigate(destination)
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
        return ViewHolder(binding, userListDestination)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentData = dataSet[position]
        viewHolder.bind(currentData.login ?: "failed", currentData.avatarUrl ?: "failed")
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}