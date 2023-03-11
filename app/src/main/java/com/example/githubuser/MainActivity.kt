package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.UsersRecyclerView.UsersAdapter
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseUsers
import com.example.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private val dataSet = arrayListOf<String>("Tesssstt", "Testt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
//        getUsers("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupRecyclerView(){
        recyclerView = binding.listUserRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        val listHeroAdapter = UsersAdapter(dataSet)
        recyclerView.adapter = listHeroAdapter
    }

    private fun setupSearchView(menu: Menu){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
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
        getUsers(query)
        searchView.clearFocus()
    }

    private fun getUsers(username: String){
        val query = if(username.isEmpty()) getString(R.string.empty_string) else username
        val client = apiService.getListUsers(query)

        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful) {
                    val listUser = response.body()?.users as List<*>
                    Log.d("response", "onResponse: $listUser")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.d("response", "failed")
            }
        })
    }
}