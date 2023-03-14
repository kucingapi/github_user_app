package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.usersRecyclerView.UsersAdapter
import com.example.githubuser.api.User
import com.example.githubuser.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var recyclerView: RecyclerView
    private val dataSet = arrayListOf<User>()
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var listUserAdapter: UsersAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        setToolBar()
        setupRecyclerView()
        setUserObserver()
//        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setToolBar(){
        val searchToolbar = binding.searchToolbar
        searchToolbar.inflateMenu(R.menu.option_menu)
        setupSearchView(searchToolbar.menu)
    }

    private fun setupSearchView(menu: Menu){
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(SearchViewQueryListener())
    }

    inner class SearchViewQueryListener: SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            handleQuerySubmission(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    }

    private fun handleQuerySubmission(query: String){
        usersViewModel.getUsers(query)
    }

    private fun setupRecyclerView(){
        recyclerView = binding.listUserRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        listUserAdapter = UsersAdapter(dataSet)
        recyclerView.adapter = listUserAdapter
    }

    private fun setUserObserver(){
        usersViewModel._listUser.observe(viewLifecycleOwner) {
            listUserAdapter.submitList(it)
        }
        usersViewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        usersViewModel.getUsers("")
    }

}