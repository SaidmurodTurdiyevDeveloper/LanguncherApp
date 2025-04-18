package com.example.langunchertv.tv

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.langunchertv.TvAppInfo
import com.example.langunchertv.tv.viewmodel.TvScreenViewModel


@Composable
fun TvLauncherScreen(
    viewModel: TvScreenViewModel
) {
    val context = LocalContext.current
    val apps = viewModel.apps.value
    val isActive = viewModel.isActive.value
    val isFavourites = viewModel.isFavourites.value
    LaunchedEffect(Unit) {
        viewModel.loadApps(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Launcher App", fontSize = 32.sp, color = Color.White)
            TvTimerTextView()
            Spacer(Modifier.weight(1f))
            TvFocusableButton(
                onClick = {
                    viewModel.changeIsActive(!isActive)
                },
                text = if (isActive) "O`chirish" else "Yoqish",
                focusedContainerColor = Color(0xFF007AFF),
                containerColor = Color(0xFF4B4B4B)
            )
            TvFocusableButton(
                onClick = {
                    if (isFavourites) {
                        viewModel.loadApps(context)
                    } else {
                        viewModel.favourites(context)
                    }
                },
                text = if (isFavourites) "Barchasi" else "Sevimlilar",
                focusedContainerColor = Color(0xFF007AFF),
                containerColor = Color(0xFF4B4B4B)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (isActive) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                columns = GridCells.Fixed(4)
            ) {
                itemsIndexed(apps) { index, app ->
                    TvAppItem(app) {
                        if (app.isFavourite) {
                            viewModel.removeFavourite(index = index)
                        } else {
                            viewModel.addFavourite(index = index)
                        }
                    }
                }
            }
        } else {
            Text("Launcher o`chirilgan", fontSize = 32.sp, color = Color.White)
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun getAllLaunchableInstalledApps(context: Context, localList: List<String>): List<TvAppInfo> {
    val pm = context.packageManager
    val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { pm.getLaunchIntentForPackage(it.packageName) != null }

    return apps.map {
        TvAppInfo(
            label = pm.getApplicationLabel(it).toString(),
            packageName = it.packageName,
            icon = pm.getApplicationIcon(it),
            isFavourite = localList.contains(it.packageName)
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

