package com.example.githubuser.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.viewmodel.UsersViewModel
import com.example.githubuser.adapter.UsersAdapter
import com.example.githubuser.api.User
import com.example.githubuser.databinding.FragmentUserListBinding
import com.example.githubuser.repository.DataStoreRepository
import com.example.githubuser.viewmodel.UsersViewModelFactory

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var recyclerView: RecyclerView
    private val dataSet = arrayListOf<User>()
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var listUserAdapter: UsersAdapter
    private lateinit var menu: Menu
    private lateinit var searchView: SearchView
    private var nightMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        val dataStoreRepository = DataStoreRepository.getInstance(requireContext())
        usersViewModel = ViewModelProvider(this, UsersViewModelFactory(requireActivity().application, dataStoreRepository))[UsersViewModel::class.java]
        setToolBar()
        setupRecyclerView()
        setUserObserver()
        setDataStoreObserver()
        setNavigation()
        return binding.root
    }

    private fun setToolBar(){
        val searchToolbar = binding.searchToolbar
        searchToolbar.inflateMenu(R.menu.option_menu)
        menu = searchToolbar.menu
        setupSearchView()
    }

    private fun setupSearchView(){
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

        listUserAdapter = UsersAdapter(dataSet, UserListDestination.DETAIL)
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

    private fun setDataStoreObserver() {
        val themeToggle = menu.findItem(R.id.setting)
        themeToggle.setOnMenuItemClickListener {
            usersViewModel.saveToDataStore(!nightMode)
            true
        }
        usersViewModel.readFromDataStore.observe(viewLifecycleOwner) {
            nightMode = it
            if(nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeToggle.setIcon(R.drawable.ic_baseline_mode_night_24)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeToggle.setIcon(R.drawable.ic_baseline_light_mode_24)
            }
        }
    }

    private fun setNavigation() {
        val favoriteIcon = menu.findItem(R.id.favorite)
        favoriteIcon.setOnMenuItemClickListener {
            val destination = UserListFragmentDirections.actionUserListFragmentToFavoriteUserFragment()
            findNavController().navigate(destination)
            true
        }
    }
}