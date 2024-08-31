package com.kouseina.storyapp.view.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.remote.response.ErrorResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    private val _addStorySuccess = MutableLiveData<Boolean>()
    val addStorySuccess: LiveData<Boolean> = _addStorySuccess

    fun addStory(
        multipartBody: MultipartBody.Part,
        requestBody: RequestBody
    ) {
        _message.value = null
        _isLoading.value = true
        _addStorySuccess.value = false

        viewModelScope.launch {
            try {
                val successResponse = repository.addStory(multipartBody, requestBody)
                _message.value = successResponse.message
                _isLoading.value = false
                _addStorySuccess.value = true
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                _message.value = errorResponse.message
                _isLoading.value = false
                _addStorySuccess.value = false
            }
        }
    }
}