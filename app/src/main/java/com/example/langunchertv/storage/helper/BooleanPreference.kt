package com.example.langunchertv.storage.helper

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class BooleanPreference(
    private val pref: SharedPreferences,
    private val defValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getBoolean(property.name, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        pref.edit { putBoolean(property.name, value).apply() }
}