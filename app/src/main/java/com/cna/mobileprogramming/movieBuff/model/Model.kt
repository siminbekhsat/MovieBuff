package com.cna.mobileprogramming.movieBuff.model

data class Movie(
    val name: String,
    val imageUrl: String,
    val category: String,
    val phoneNumber: String = "709-123-4567"
)