package com.example.langunchertv

import android.graphics.drawable.Drawable

data class TvAppInfo(
    val label: String,
    val packageName: String,
    val icon: Drawable?,
    val isFavourite: Boolean
)