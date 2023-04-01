package com.example.githubuser.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowViewPagerAdapter
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.databinding.FragmentUserDetailBinding
import com.example.githubuser.viewmodel.UserDetailViewModel
import com.example.githubuser.viewmodel.UserDetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var usernameText: String
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var profileUrl: String
    private lateinit var favoriteButton: FloatingActionButton

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
        const val ARG_USERNAME = "username_argument"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        userDetailViewModel = ViewModelProvider(this, UserDetailViewModelFactory(requireActivity().application))[UserDetailViewModel::class.java]
        getBundleData()
        getUserDetail()
        setUserDetailObserver()
        setTabLayout()
        setFavoriteButton()
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
        if (profileUrl != null) {
            this.profileUrl = profileUrl
        }
        with(binding){
            username.text = usernameText
            followingCount.text = following.toString()
            followersCount.text = followers.toString()
            Glide.with(requireContext())
                .load(profileUrl)
                .into(profilePicture)
        }
    }

    private fun setTabLayout() {
        val sectionsPagerAdapter = FollowViewPagerAdapter(requireActivity(), usernameText)
        val viewPager: ViewPager2 = binding.viewPagerFollow
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabFollow
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setFavoriteButton() {
        favoriteButton = binding.fabFavorite
        userDetailViewModel.isUserFavorite(usernameText)
        userDetailViewModel.isFavorite.observe(viewLifecycleOwner) {
            val red200 = ContextCompat.getColor(requireContext(), R.color.red_200)
            val white = ContextCompat.getColor(requireContext(), R.color.white)
            val red500 = ContextCompat.getColor(requireContext(), R.color.red_500)

            val backgroundColor = if (it) red200 else white
            val iconTint = ColorStateList.valueOf(red500)

            favoriteButton.backgroundTintList = ColorStateList.valueOf(backgroundColor)
            favoriteButton.imageTintList = iconTint
            setFavoriteOnClickListener(it)
        }
    }
    private fun setFavoriteOnClickListener(isFavorite: Boolean){
        if (!isFavorite){
            favoriteButton.setOnClickListener {
                val favoriteUser = FavoriteUser(usernameText, profileUrl)
                userDetailViewModel.insertFavoriteUser(favoriteUser)
            }
        }
        else {
            favoriteButton.setOnClickListener {
                userDetailViewModel.deleteFavoriteUser(usernameText)
            }
        }
    }

}