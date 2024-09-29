package com.kouseina.storyapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kouseina.storyapp.R
import com.kouseina.storyapp.data.StoryRepository
import com.kouseina.storyapp.data.pref.UserPreference
import com.kouseina.storyapp.data.pref.dataStore
import com.kouseina.storyapp.data.remote.response.ErrorResponse
import com.kouseina.storyapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException


internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    companion object {
        fun provideRepository(context: Context): StoryRepository {
            val pref = UserPreference.getInstance(context.dataStore)
            val user = runBlocking { pref.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            return StoryRepository.getInstance(pref, apiService)
        }
    }

    private val mWidgetItems = ArrayList<String>()

    override fun onDataSetChanged() {
        runBlocking {
            try {
                val response = provideRepository(mContext).getStoryForStackWidget()
                mWidgetItems.addAll(response.listStory?.map { it.photoUrl ?:  "" } ?: ArrayList())
            } catch (e: HttpException) {
                //get error message
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message

                Log.d("StackRemoteViewsFactory", errorMessage ?: "")
            }
        }
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        try {
            val bitmap: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mWidgetItems[position])
                .submit(200, 200)
                .get()

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (e: Exception) {
            Log.d("StackRemoteViewsFactory", e.toString())
        }

        val extras = bundleOf(
            StoryBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}