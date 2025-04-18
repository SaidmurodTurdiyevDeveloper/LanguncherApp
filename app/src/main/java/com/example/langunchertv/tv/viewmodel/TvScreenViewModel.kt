package com.example.langunchertv.tv.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.langunchertv.TvAppInfo
import com.example.langunchertv.storage.LocalStorage
import com.example.langunchertv.tv.getAllLaunchableInstalledApps
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TvScreenViewModel(private val localStorage: LocalStorage) : ViewModel() {
    val isActive = mutableStateOf(false)
    val isFavourites = mutableStateOf(false)
    val apps = mutableStateOf(listOf<TvAppInfo>())
    private val local = getLocalApps()

    fun changeIsActive(isActive: Boolean) {
        this.isActive.value = isActive
        localStorage.isLauncherActive = isActive
    }

    fun loadApps(context: Context) {
        isFavourites.value = false
        apps.value = getAllLaunchableInstalledApps(context, local)
    }

    fun favourites(context: Context) {
        isFavourites.value = true
        apps.value = getAllLaunchableInstalledApps(context, local).filter { it.isFavourite }
    }

    fun addFavourite(index: Int) {
        val newLs = apps.value.toMutableList()
        newLs[index] = apps.value[index].copy(isFavourite = true)
        apps.value = newLs
        local.add(apps.value[index].packageName)
        setLocalApps()
    }

    fun removeFavourite(index: Int) {
        val newLs = apps.value.toMutableList()
        newLs[index] = apps.value[index].copy(isFavourite = false)
        apps.value=newLs
        local.remove(apps.value[index].packageName)
        setLocalApps()
    }

    private fun getLocalApps(): MutableList<String> {
        try {
            val gson = Gson()
            val jsonString = localStorage.favourites
            if (jsonString.isBlank())
                return mutableListOf()
            val typeToken = object : TypeToken<List<String>>() {}
            val ls = gson.fromJson<List<String>>(jsonString, typeToken.type)
            return ls.toMutableList()
        } catch (_: Exception) {
            localStorage.favourites = ""
            return mutableListOf()
        }
    }

    private fun setLocalApps() {
        val gson = Gson()
        val gsonString = gson.toJson(local)
        localStorage.favourites = gsonString
    }
}