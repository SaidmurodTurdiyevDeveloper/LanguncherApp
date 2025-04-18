package com.example.langunchertv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModelProvider
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.example.langunchertv.mobile.MobileLauncherScreen
import com.example.langunchertv.storage.LocalStorageImpl
import com.example.langunchertv.tv.TvLauncherScreen
import com.example.langunchertv.tv.viewmodel.TvScreenViewModel
import com.example.langunchertv.tv.viewmodel.ViewModelFactory
import com.example.langunchertv.ui.theme.LanguncherTVTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: TvScreenViewModel

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detector = DeviceTypeDetectorUseCase().invoke(this)
        val localStorage = LocalStorageImpl(this)
        val factory = ViewModelFactory(localStorage)
        viewModel = ViewModelProvider(this, factory)[TvScreenViewModel::class.java]
        setContent {
            LanguncherTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    when (detector) {
                        DeviceType.SMART_TV -> {
                            TvLauncherScreen(viewModel)
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

