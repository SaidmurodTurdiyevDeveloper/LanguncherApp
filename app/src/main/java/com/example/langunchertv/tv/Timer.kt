package com.example.langunchertv.tv

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@Composable
internal fun TvTimerTextView() {
    var currentTime by remember { mutableStateOf(getFormattedTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = getFormattedTime()
        }
    }

    Text(
        text = currentTime,
        style = MaterialTheme.typography.headlineLarge,
        color = Color.White,
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

fun getFormattedTime(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}