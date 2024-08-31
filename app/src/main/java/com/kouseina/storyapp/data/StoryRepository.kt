package com.kouseina.storyapp.data

import com.kouseina.storyapp.data.pref.UserModel
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.remote.response.AddStoryResponse
import com.kouseina.storyapp.data.remote.response.StoryResponse
import com.kouseina.storyapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getStory(): StoryResponse {
        return apiService.getStories()
    }

    suspend fun addStory(
        multipartBody: MultipartBody.Part,
        requestBody: RequestBody
    ): AddStoryResponse {
        return apiService.addStory(multipartBody, requestBody)
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): StoryRepository = StoryRepository(userPreference, apiService)
    }
}