package com.example.langunchertv.tv

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.Switch
import androidx.tv.material3.SwitchDefaults

@Composable
 fun FocusableSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val thumbColor = when {
        isFocused && checked -> Color(0xFF22C55E)
        isFocused && !checked -> Color(0xFFCBD5E1)
        checked -> Color(0xFF17B26A)
        else -> Color.Companion.White
    }

    val trackColor = when {
        isFocused -> Color(0xFF3E64FF)
        checked -> Color(0xFF344054)
        else -> Color(0xFF344054)
    }

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        interactionSource = interactionSource,
        modifier = Modifier.Companion.focusable(interactionSource = interactionSource),
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,
            uncheckedThumbColor = thumbColor,
            checkedTrackColor = trackColor,
            uncheckedTrackColor = trackColor,
            uncheckedBorderColor = trackColor
        )
    )
}