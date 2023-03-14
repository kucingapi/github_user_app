package com.example.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.FragmentUserDetailBinding
import com.example.githubuser.viewmodel.UserDetailViewModel

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var usernameText: String
    private val userDetailViewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        getBundleData()
        getUserDetail()
        setUserDetailObserver()
        return binding.root
    }

    private fun getBundleData(){
        usernameText = UserDetailFragmentArgs.fromBundle(arguments as Bundle).username
    }

    private fun getUserDetail() {
        userDetailViewModel.getUserDetail(usernameText)
    }

    private fun setUserDetailObserver() {
        userDetailViewModel.userDetail.observe(viewLifecycleOwner) {
            setUserDetail(it.avatarUrl, it.followers, it.following)
        }
        userDetailViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
    }

    private fun setUserDetail(profileUrl: String?, followers: Int?, following: Int?){
        with(binding){
            username.text = usernameText
            followingCount.text = following.toString()
            followersCount.text = followers.toString()
            Glide.with(requireContext())
                .load(profileUrl)
                .into(profilePicture)
        }

    }

}