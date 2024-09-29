package com.kouseina.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.storyapp.R
import com.kouseina.storyapp.data.remote.response.ListStoryItem
import com.kouseina.storyapp.databinding.ActivityMainBinding
import com.kouseina.storyapp.view.LoadingStateAdapter
import com.kouseina.storyapp.view.ViewModelFactory
import com.kouseina.storyapp.view.add_story.AddStoryActivity
import com.kouseina.storyapp.view.map.MapsActivity
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

            R.id.menu_map -> {
                startActivity(Intent(this, MapsActivity::class.java))
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

        }

        viewModel.storyList.observe(this) {
            setStoryListData(it)
        }
    }

    private fun setFab() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setStoryListData(data: PagingData<ListStoryItem>) {
        val adapter = MainAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        adapter.submitData(lifecycle, data)
    }
}