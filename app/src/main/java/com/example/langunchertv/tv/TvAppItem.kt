package com.example.langunchertv.tv

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import coil.compose.rememberAsyncImagePainter
import com.example.langunchertv.TvAppInfo

@Composable
fun TvAppItem(app: TvAppInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth(),
        colors = CardDefaults.colors(
            focusedContainerColor = Color(0xFF3E64FF),
            containerColor = Color.Companion.White,
            contentColor = Color.Companion.Black,
            focusedContentColor = Color.Companion.White
        ),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 0.dp,
                    color = Color.Transparent
                )
            )
        ),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                app.icon?.let {
                    Image(painter = rememberAsyncImagePainter(it), contentDescription = null, modifier = Modifier.Companion.size(48.dp))
                }
                Spacer(modifier = Modifier.Companion.height(12.dp))
                Text(app.label, fontSize = 20.sp, textAlign = TextAlign.Companion.Center, maxLines = 1)
            }
            Icon(
                imageVector = if (app.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favourite",
                tint = if (app.isFavourite) Color.Red else Color.Black,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

    }
}

