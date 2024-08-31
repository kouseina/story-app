package com.kouseina.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.storyapp.R
import com.kouseina.storyapp.data.remote.response.ListStoryItem
import com.kouseina.storyapp.databinding.ActivityMainBinding
import com.kouseina.storyapp.view.ViewModelFactory
import com.kouseina.storyapp.view.add_story.AddStoryActivity
import com.kouseina.storyapp.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchStoryList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.logout()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        setToolBar()
        setRecylerView()
        setFab()
    }

    private fun setToolBar() {
        binding.toolbar.title = "Dicoding Story"

        setSupportActionBar(binding.toolbar)
    }

    private fun setRecylerView() {
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(applicationContext, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        viewModel.getSession().observe(this) { user ->
            Log.d("MainActivity isLogin: ", user.isLogin.toString())
            Log.d("MainActivity TOKEN: ", user.token)

            if (user.isLogin) {
                viewModel.fetchStoryList()
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.storyList.observe(this) {
            setStoryListData(it)

            if (it.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvStory.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvStory.visibility = View.VISIBLE
            }
        }
    }

    private fun setFab() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setStoryListData(storyList: List<ListStoryItem>) {
        val adapter = MainAdapter()
        adapter.submitList(storyList)
        binding.rvStory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.visibility = View.GONE
        }
    }

}