package com.example.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapter.UsersAdapter
import com.example.githubuser.api.User
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listUserAdapter: UsersAdapter
    private val followingViewModel: FollowingViewModel by viewModels()
    private val dataSet = arrayListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setFollowersData()
        return binding.root
    }

    private fun setupRecyclerView(){
        recyclerView = binding.recyclerViewFollowing

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        listUserAdapter = UsersAdapter(dataSet)
        recyclerView.adapter = listUserAdapter
    }

    private fun setFollowersData() {
        val username = arguments?.getString(UserDetailFragment.ARG_USERNAME, "test")
        followingViewModel.getFollowing(username ?: "kucingapi")
        followingViewModel.userFollowing.observe(viewLifecycleOwner) {
            listUserAdapter.submitList(it)
        }
        followingViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
    }

}