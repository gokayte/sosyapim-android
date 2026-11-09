package com.example.sosyapim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.sosyapim.data.AppScreen
import com.example.sosyapim.data.MusicCatalog
import com.example.sosyapim.data.MusicClip
import com.example.sosyapim.ui.components.CrtTvModeView
import com.example.sosyapim.ui.components.SosBottomNav
import com.example.sosyapim.ui.components.SosHeader
import com.example.sosyapim.ui.screens.ArtistsScreen
import com.example.sosyapim.ui.screens.ClipsScreen
import com.example.sosyapim.ui.screens.ServicesDemoScreen
import com.example.sosyapim.ui.screens.YeraltiScreen
import com.example.sosyapim.ui.theme.SOSYapimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SOSYapimTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.CLIPS) }
    var currentClipIndex by remember { mutableIntStateOf(0) }
    var isTvMode by remember { mutableStateOf(false) }

    val clips = MusicCatalog.allClips
    val currentClip = clips.getOrElse(currentClipIndex) { clips[0] }

    val onSelectClip: (MusicClip) -> Unit = { clip ->
        val idx = clips.indexOfFirst { it.id == clip.id }
        if (idx >= 0) {
            currentClipIndex = idx
        }
        currentScreen = AppScreen.CLIPS
    }

    val onPrevClip: () -> Unit = {
        currentClipIndex = if (currentClipIndex > 0) currentClipIndex - 1 else clips.size - 1
    }

    val onNextClip: () -> Unit = {
        currentClipIndex = (currentClipIndex + 1) % clips.size
    }

    // Handle Android system back gesture properly
    BackHandler(enabled = isTvMode || currentScreen != AppScreen.CLIPS) {
        if (isTvMode) {
            isTvMode = false
        } else if (currentScreen != AppScreen.CLIPS) {
            currentScreen = AppScreen.CLIPS
        }
    }

    if (isTvMode) {
        // Retro Tube TV (CRT) Mode Full Experience
        CrtTvModeView(
            currentClip = currentClip,
            playlist = clips,
            onSelectClip = onSelectClip,
            onPrevClip = onPrevClip,
            onNextClip = onNextClip,
            onExitTvMode = { isTvMode = false },
            modifier = Modifier
                .fillMaxSize()
                .testTag("crt_tv_mode_screen")
        )
    } else {
        // Standard Modern Studio Experience
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("sos_main_scaffold"),
            topBar = {
                SosHeader(
                    onToggleTvMode = { isTvMode = true }
                )
            },
            bottomBar = {
                SosBottomNav(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it }
                )
            },
            containerColor = Color(0xFF0F1115)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFF0F1115))
            ) {
                when (currentScreen) {
                    AppScreen.CLIPS -> {
                        ClipsScreen(
                            currentClip = currentClip,
                            onClipSelected = onSelectClip,
                            onToggleTvMode = { isTvMode = true }
                        )
                    }
                    AppScreen.ARTISTS -> {
                        ArtistsScreen(
                            onArtistSelected = { clip ->
                                onSelectClip(clip)
                            }
                        )
                    }
                    AppScreen.YERALTI -> {
                        YeraltiScreen()
                    }
                    AppScreen.SERVICES -> {
                        ServicesDemoScreen()
                    }
                }
            }
        }
    }
}
