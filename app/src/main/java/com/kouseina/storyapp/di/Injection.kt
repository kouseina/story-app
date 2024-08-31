package com.kouseina.storyapp.di

import android.content.Context
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.pref.dataStore
import com.kouseina.storyapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(pref, apiService)
    }
}