package com.example.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapter.FavoriteUserAdapter
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.databinding.FragmentFavoriteUserBinding
import com.example.githubuser.viewmodel.FavoriteUserViewModel
import com.example.githubuser.viewmodel.FavoriteUserViewModelFactory


class FavoriteUserFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteUserBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listFavoriteUserAdapter: FavoriteUserAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private val dataSet = arrayListOf<FavoriteUser>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        favoriteUserViewModel = ViewModelProvider(this, FavoriteUserViewModelFactory(requireActivity().application))[FavoriteUserViewModel::class.java]
        setRecyclerView()
        setFavoriteObserver()
        return binding.root
    }

    private fun setRecyclerView() {
        recyclerView = binding.favoriteRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        listFavoriteUserAdapter = FavoriteUserAdapter(dataSet)
        recyclerView.adapter = listFavoriteUserAdapter
    }

    private fun setFavoriteObserver() {
        favoriteUserViewModel.favoriteUsers.observe(viewLifecycleOwner) {
            listFavoriteUserAdapter.submitList(it)
        }

        favoriteUserViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        favoriteUserViewModel.getAllFavoriteUser()
    }
}