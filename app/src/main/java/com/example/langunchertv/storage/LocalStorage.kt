package com.example.langunchertv.storage

interface LocalStorage {
    fun clear()
    var favourites: String
    var isLauncherActive: Boolean
}