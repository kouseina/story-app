package com.kouseina.storyapp.di

import android.content.Context
import androidx.lifecycle.asLiveData
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.UserRepository
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.pref.dataStore
import com.kouseina.storyapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun userProvideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun storyProvideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().asLiveData() }
        val apiService = ApiConfig.getApiService(user.value?.token)
        return StoryRepository.getInstance(pref, apiService)
    }
}