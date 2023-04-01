package com.example.githubuser.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragment.FollowersFragment
import com.example.githubuser.fragment.FollowingFragment
import com.example.githubuser.fragment.UserDetailFragment

class FollowViewPagerAdapter(activity: FragmentActivity, username: String) : FragmentStateAdapter(activity) {
    private var username: String
    override fun getItemCount(): Int {
        return 2
    }
    init {
        this.username = username
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val followersFragment = FollowersFragment()
        val followingFragment = FollowingFragment()
        followersFragment.arguments = Bundle().apply {
            putString(UserDetailFragment.ARG_USERNAME, username)
        }
        followingFragment.arguments = Bundle().apply {
            putString(UserDetailFragment.ARG_USERNAME, username)
        }
        when (position) {
            0 -> fragment = followersFragment
            1 -> fragment = followingFragment
        }
        return fragment as Fragment
    }
}