package com.kouseina.storyapp.di

import android.content.Context
import androidx.lifecycle.asLiveData
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.pref.dataStore
import com.kouseina.storyapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken() }
        val apiService = ApiConfig.getApiService(token)
        return StoryRepository.getInstance(pref, apiService)
    }
}