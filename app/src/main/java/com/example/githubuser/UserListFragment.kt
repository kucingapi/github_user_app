package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.UsersRecyclerView.UsersAdapter
import com.example.githubuser.api.User
import com.example.githubuser.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var recyclerView: RecyclerView
    private val dataSet = arrayListOf<User>()
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var listUserAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setUserObserver()
        return binding.root
    }

    private fun setupRecyclerView(){
        recyclerView = binding.listUserRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        listUserAdapter = UsersAdapter(dataSet)
        recyclerView.adapter = listUserAdapter
    }

    fun setUserObserver(): Unit {
        usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        usersViewModel._listUser.observe(viewLifecycleOwner) {
            listUserAdapter.submitList(it)
        }
        usersViewModel.getUsers("")
    }

}