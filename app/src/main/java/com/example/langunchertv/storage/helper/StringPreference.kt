package com.example.langunchertv.storage.helper

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class StringPreference(
    private val pref: SharedPreferences,
    private val defValue: String = ""
) : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String =
        pref.getString(property.name, defValue) ?: ""

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
        pref.edit { putString(property.name, value).apply() }
}