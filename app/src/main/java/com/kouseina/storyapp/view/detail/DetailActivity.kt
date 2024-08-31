package com.kouseina.storyapp.view.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kouseina.storyapp.data.remote.response.ListStoryItem
import com.kouseina.storyapp.databinding.ActivityDetailBinding
class DetailActivity : AppCompatActivity() {
    companion object {
        const val STORY_ITEM = "story_item"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val storyItem = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ListStoryItem>(STORY_ITEM, ListStoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListStoryItem>(STORY_ITEM)
        }

        Glide.with(applicationContext)
            .load(storyItem?.photoUrl)
            .into(binding.imageView)
        binding.tvName.text = storyItem?.name
        binding.tvDesc.text = storyItem?.description
    }
}