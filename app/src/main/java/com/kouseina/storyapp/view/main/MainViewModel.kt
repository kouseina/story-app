package com.kouseina.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.UserRepository
import com.kouseina.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }


    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}