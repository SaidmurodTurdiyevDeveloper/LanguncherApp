package com.example.langunchertv.storage

import android.content.Context
import com.example.langunchertv.storage.helper.BooleanPreference
import com.example.langunchertv.storage.helper.StringPreference

class LocalStorageImpl(
    context: Context
) : LocalStorage {

    private val pref = context.getSharedPreferences("tv_cache", Context.MODE_PRIVATE)
    override fun clear() {
        pref.edit().apply {
            clear()
            apply()
        }
    }

    override var favourites by StringPreference(pref = pref, defValue = "[]")
    override var isLauncherActive: Boolean by BooleanPreference(pref = pref, true)

}