package com.example.kr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kr.data.Repository
import com.example.kr.model.MediaItem

class MediaItemViewModel(private val repository: Repository) : ViewModel() {
    private val _mediaItems = MutableLiveData<List<MediaItem>>()
    val mediaItems: LiveData<List<MediaItem>> get() = _mediaItems

    fun fetchMediaItems() {
        _mediaItems.value = repository.getAllItems()
    }

    fun addMediaItem(item: MediaItem) {
        repository.addItem(item)
        fetchMediaItems()
    }

    fun updateMediaItem(item: MediaItem) {
        repository.updateItem(item)
        fetchMediaItems()
    }

    fun deleteMediaItem(itemId: Int) {
        repository.deleteItem(itemId)
        fetchMediaItems()
    }
}
