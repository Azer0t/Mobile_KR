package com.example.kr.model

data class MediaItem(
    val id: Int = 0,
    val title: String,
    val description: String,
    val genre: String,
    val releaseYear: Int,
    val rating: Float,
    val authorOrDirector: String,
    val imageUrl: String
)
