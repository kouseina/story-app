package com.kouseina.storyapp.data

import com.kouseina.storyapp.data.pref.UserModel
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.remote.response.StoryResponse
import com.kouseina.storyapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    suspend fun getStory(): StoryResponse {
        return apiService.getStories()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(userPreference, apiService)
            }.also { instance = it }
    }
}