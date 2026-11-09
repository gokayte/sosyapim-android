package com.example.sosyapim.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sosyapim.data.MusicClip
import com.example.sosyapim.ui.theme.SosRed
import com.example.sosyapim.ui.theme.SosRedDark

@Composable
fun CrtTvModeView(
    currentClip: MusicClip,
    playlist: List<MusicClip>,
    onSelectClip: (MusicClip) -> Unit,
    onPrevClip: () -> Unit,
    onNextClip: () -> Unit,
    onExitTvMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var volumeLevel by remember { mutableIntStateOf(70) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_alpha"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B0C0E))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        // Top Retro TV Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .alpha(dotAlpha)
                        .background(SosRed, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CANLI YAYIN · TÜPLÜ TV MODU",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFF3F3F4),
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            Button(
                onClick = onExitTvMode,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292B30)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("exit_tv_mode_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Moddan Çık",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Standart Mod",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Retro CRT TV Bezel Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(22.dp)),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF24262B)),
            border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF43464D))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Outer bezel gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF1E2024), Color(0xFF121316))
                            )
                        )
                        .border(2.dp, Color(0xFF383B41), RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    // Curved TV screen frame
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF08090C))
                            .border(1.dp, Color(0xFF555962), RoundedCornerShape(12.dp))
                    ) {
                        YouTubePlayerView(
                            videoId = currentClip.id,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // CRT Control Panel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF17181C))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "SOS TUBE · CRT 01",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(0xFF33FF77),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "SES $volumeLevel%",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp,
                            color = Color(0xFFD4D6DC)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Prev Clip Button
                        IconButton(
                            onClick = onPrevClip,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF303238))
                                .testTag("crt_prev_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Önceki Klip",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Vol Down
                        IconButton(
                            onClick = { if (volumeLevel > 0) volumeLevel -= 10 },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF303238))
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeDown,
                                contentDescription = "Sesi Kıs",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Vol Up
                        IconButton(
                            onClick = { if (volumeLevel < 100) volumeLevel += 10 },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF303238))
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Sesi Aç",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Next Clip Button
                        IconButton(
                            onClick = onNextClip,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF303238))
                                .testTag("crt_next_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Sonraki Klip",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Now Playing details in CRT Mode
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${currentClip.artist} - ${currentClip.title}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentClip.highlight ?: "SOS Yapım video klibi",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFA0A5AE)
                )
            }

            IconButton(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentClip.youtubeUrl))
                        context.startActivity(intent)
                    } catch (_: Exception) {}
                }
            ) {
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = "YouTube'da Aç",
                    tint = SosRed
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // CRT Channel / Playlist heading
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "KANAL LİSTESİ · VİDEO SEÇKİSİ",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFA9ADB5),
                letterSpacing = 1.sp
            )
            Text(
                text = "${playlist.size} KLİP",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SosRed
            )
        }

        // Lazy Playlist inside CRT Mode
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(playlist) { _, clip ->
                val isSelected = clip.id == currentClip.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) Color(0xFF352328) else Color(0xFF1D1F23))
                        .border(
                            1.dp,
                            if (isSelected) Color(0xFF9E3A43) else Color(0xFF2C2F36),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectClip(clip) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 68.dp, height = 44.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black)
                    ) {
                        AsyncImage(
                            model = clip.thumbnailUrl,
                            contentDescription = clip.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0x66D72632)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${clip.artist} - ${clip.title}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFFE4E6EA),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = clip.highlight ?: "Video klibi",
                            fontSize = 11.sp,
                            color = if (isSelected) Color(0xFFFF9BA1) else Color(0xFF8E929B)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Oynat",
                        tint = if (isSelected) SosRed else Color(0xFF666A73),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
