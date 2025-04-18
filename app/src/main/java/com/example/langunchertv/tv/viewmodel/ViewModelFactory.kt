package com.example.langunchertv.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.langunchertv.storage.LocalStorage

class ViewModelFactory(private val localStorage: LocalStorage) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvScreenViewModel::class.java)) {
            return TvScreenViewModel(localStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}