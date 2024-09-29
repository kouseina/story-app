package com.kouseina.storyapp

import com.kouseina.storyapp.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                id = i.toString(),
                name = "name $i",
                photoUrl = "photoUrl $i",
                createdAt = "",
                description = "desc $i",
            )
            items.add(quote)
        }
        return items
    }
}