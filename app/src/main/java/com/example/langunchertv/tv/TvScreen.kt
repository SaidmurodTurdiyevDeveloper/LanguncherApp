package com.example.langunchertv.tv

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.tv.material3.Text
import com.example.langunchertv.MainActivity
import com.example.langunchertv.TvAppInfo


@Composable
fun TvLauncherScreen() {
    val context = LocalContext.current
    var isAutoLaunchEnabled = remember { mutableStateOf(false) }
    val apps = remember { getAllLaunchableInstalledApps(context) }
    var isShowDialog = remember { mutableStateOf(false) }
    var isHiding = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        val componentName = ComponentName(context, MainActivity::class.java)
        context.packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {

                Lifecycle.Event.ON_RESUME -> {
                    isShowDialog.value = false
                    isHiding.value = false
                    isAutoLaunchEnabled.value = isDefaultLauncher(context)
                }

                Lifecycle.Event.ON_PAUSE -> {
                    isHiding.value = true
                }

                else -> Unit
            }
        }

        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .alpha(
                if (isHiding.value) 0.1f else 1f
            )
            .padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TvFocusableButton(
                modifier = Modifier.fillMaxWidth(0.3f),
                onClick = {
                    isShowDialog.value = true
                },
                text = "Homa oynani o`zgartirish",
                focusedContainerColor = Color(0xFF007AFF),
                containerColor = Color(0xFF4B4B4B)
            )
            Text(
                "Home oynaga qoyilganmi?",
                fontSize = 20.sp
            )
            FocusableSwitch(
                checked = isAutoLaunchEnabled.value,
                onCheckedChange = { checked ->
//                    isAutoLaunchEnabled.value = checked
                }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(4)
        ) {
            items(apps) { app ->
                TvAppItem(app) {
                    val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
                    context.startActivity(intent)
                }
            }
        }
    }
    if (isShowDialog.value && isHiding.value.not()) {
        ChangeHomeLauncherDialogTv(
            onDismiss = {
                isShowDialog.value = false
                isHiding.value = false
            },
            changeDialog = {
                val intent = Intent(Settings.ACTION_HOME_SETTINGS)
                context.startActivity(intent)
                isHiding.value = true
            }
        )
    }

}

@SuppressLint("QueryPermissionsNeeded")
fun getAllLaunchableInstalledApps(context: Context): List<TvAppInfo> {
    val pm = context.packageManager
    val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { pm.getLaunchIntentForPackage(it.packageName) != null }

    return apps.map {
        TvAppInfo(
            label = pm.getApplicationLabel(it).toString(),
            packageName = it.packageName,
            icon = pm.getApplicationIcon(it)
        )
    }.sortedBy { it.label.lowercase() }
}


fun isDefaultLauncher(context: Context): Boolean {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
    }

    val resolveInfo = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    val currentLauncherPackage = resolveInfo?.activityInfo?.packageName
    Log.d("TagTagTag", "Current launcher package: $currentLauncherPackage")
    Log.d("TagTagTag", "Current launcher package: ${context.packageName}")
    return context.packageName == currentLauncherPackage
}

