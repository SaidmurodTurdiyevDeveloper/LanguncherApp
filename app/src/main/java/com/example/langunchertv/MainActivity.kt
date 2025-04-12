package com.example.langunchertv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.example.langunchertv.mobile.MobileLauncherScreen
import com.example.langunchertv.tv.TvLauncherScreen
import com.example.langunchertv.ui.theme.LanguncherTVTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val detector = DeviceTypeDetectorUseCase().invoke(this)
            LanguncherTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    when (detector) {
                        DeviceType.SMART_TV -> {
                            TvLauncherScreen()
                        }

                        DeviceType.TABLET -> {
                            MobileLauncherScreen()
                        }

                        DeviceType.PHONE -> {
                            MobileLauncherScreen()
                        }
                    }
                }
            }
        }
    }
}

