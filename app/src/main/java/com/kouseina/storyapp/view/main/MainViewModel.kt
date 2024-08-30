package com.kouseina.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.pref.UserModel
import com.kouseina.storyapp.data.remote.response.ErrorResponse
import com.kouseina.storyapp.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val repository: StoryRepository
) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    private val _storyList = MutableLiveData<List<ListStoryItem>>()
    val storyList: LiveData<List<ListStoryItem>> = _storyList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchStoryList() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getStory()
                _isLoading.value = false

                if (response.listStory != null) {
                    _storyList.value = response.listStory ?: ArrayList()
                }


            } catch (e: HttpException) {
                _isLoading.value = false
                //get error message
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
            }
        }
    }
}