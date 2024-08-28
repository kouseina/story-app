package com.kouseina.storyapp.di

import android.content.Context
import com.kouseina.storyapp.data.UserRepository
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}