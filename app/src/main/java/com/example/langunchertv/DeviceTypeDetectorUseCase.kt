package com.example.langunchertv

import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration


class DeviceTypeDetectorUseCase {
    fun invoke(context: Context): DeviceType {
        val pm = context.packageManager

        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
        val isTv = uiModeManager?.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION

        return when {
            isTv ||
                    pm.hasSystemFeature(PackageManager.FEATURE_LEANBACK) ||
                    pm.hasSystemFeature(PackageManager.FEATURE_TELEVISION) -> DeviceType.SMART_TV

            (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
                    Configuration.SCREENLAYOUT_SIZE_LARGE -> DeviceType.TABLET

            else -> DeviceType.PHONE
        }
    }

}
