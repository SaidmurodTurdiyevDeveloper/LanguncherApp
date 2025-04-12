package com.example.langunchertv.tv

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.AssistChipDefaults
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
 fun TvFocusableButton(
    modifier: Modifier = Modifier.Companion,
    onClick: () -> Unit,
    text: String,
    focusedContainerColor: Color = Color(0xFF007AFF),
    focusedContentColor: Color = Color.Companion.White,
    containerColor: Color = Color(0xFF344054),
    contentColor: Color = Color.Companion.White
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.colors(
            focusedContainerColor = focusedContainerColor,
            focusedContentColor = focusedContentColor,
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = ButtonDefaults.shape(shape = AssistChipDefaults.ContainerShape.copy(all = CornerSize(8.dp)))
    ) {
        Text(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = text,
            textAlign = TextAlign.Companion.Center,
            maxLines = 1,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Companion.W500
            )
        )
    }
}