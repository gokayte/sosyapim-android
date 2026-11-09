package com.example.sosyapim.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sosyapim.data.MusicCatalog
import com.example.sosyapim.data.MusicClip
import com.example.sosyapim.ui.components.YouTubePlayerView
import com.example.sosyapim.ui.theme.DarkBorder
import com.example.sosyapim.ui.theme.DarkCard
import com.example.sosyapim.ui.theme.SosRed
import com.example.sosyapim.ui.theme.SosRedDark

@Composable
fun ClipsScreen(
    currentClip: MusicClip,
    onClipSelected: (MusicClip) -> Unit,
    onToggleTvMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var likedClipIds by remember { mutableStateOf(setOf<String>()) }

    val filteredClips = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            MusicCatalog.allClips
        } else {
            val q = searchQuery.trim().lowercase()
            MusicCatalog.allClips.filter {
                it.title.lowercase().contains(q) || it.artist.lowercase().contains(q)
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F1115))
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Brand Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2026)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF343842))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF22161A), Color(0xFF181A20), Color(0xFF38151B))
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "RESMÎ SOS YAPIM SİTESİ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFF8E96),
                                letterSpacing = 1.2.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SosRed)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "65M+ İZLENME",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Bağımsız Müziğin Yeni Sesi",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Text(
                            text = "SOS Yapım video klipleri, yayınları ve sanatçı kataloğu.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFBEC2CB)
                        )
                    }
                }
            }
        }

        // Active Now Playing Player Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("now_playing_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF353942))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Player Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(SosRed, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ŞİMDİ OYNATILIYOR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE4E5E8)
                            )
                        }

                        Button(
                            onClick = onToggleTvMode,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF321A1E)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("switch_to_crt_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tv,
                                contentDescription = null,
                                tint = SosRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Tüplü TV Modu",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9BA1)
                            )
                        }
                    }

                    // YouTube Player
                    YouTubePlayerView(
                        videoId = currentClip.id,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Track Title and Artist
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currentClip.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = currentClip.artist,
                                style = MaterialTheme.typography.bodyMedium,
                                color = SosRed,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Favorite toggle
                            IconButton(
                                onClick = {
                                    likedClipIds = if (likedClipIds.contains(currentClip.id)) {
                                        likedClipIds - currentClip.id
                                    } else {
                                        likedClipIds + currentClip.id
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (likedClipIds.contains(currentClip.id)) {
                                        Icons.Default.Favorite
                                    } else {
                                        Icons.Default.FavoriteBorder
                                    },
                                    contentDescription = "Beğen",
                                    tint = if (likedClipIds.contains(currentClip.id)) SosRed else Color.Gray
                                )
                            }

                            // YouTube button
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentClip.youtubeUrl))
                                        context.startActivity(intent)
                                    } catch (_: Exception) {}
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SosRed),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "YouTube",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Vitrindeki Rekor Klipler (Horizontal Showcase)
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔥 VİTRİNDEKİ REKOR KLİPLER",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Black,
                        color = SosRed,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "DOKUN & İZLE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA5A8B0)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(MusicCatalog.showcaseClips) { clip ->
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { onClipSelected(clip) }
                                .testTag("showcase_clip_${clip.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF382025))
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(115.dp)
                                        .background(Color.Black)
                                ) {
                                    AsyncImage(
                                        model = clip.thumbnailUrl,
                                        contentDescription = clip.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(6.dp)
                                            .background(SosRed, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = clip.highlight ?: "REKOR",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(36.dp)
                                            .background(Color(0xAA000000), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = clip.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = clip.artist,
                                        fontSize = 11.sp,
                                        color = Color(0xFFA5A8B0)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("clip_search_input"),
                placeholder = {
                    Text("Şarkı veya sanatçı ara (56 klip)...", color = Color(0xFF757A84))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ara",
                        tint = Color(0xFFA5A8B0)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Temizle",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF14161A),
                    unfocusedContainerColor = Color(0xFF14161A),
                    focusedBorderColor = SosRed,
                    unfocusedBorderColor = DarkBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }

        // Section Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VİDEO KLİP LİSTESİ",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFE4E6EA),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${filteredClips.size} Video",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SosRed
                )
            }
        }

        // Clips List
        itemsIndexed(filteredClips) { _, clip ->
            val isCurrent = clip.id == currentClip.id
            val isLiked = likedClipIds.contains(clip.id)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClipSelected(clip) }
                    .testTag("clip_item_${clip.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) Color(0xFF2C191E) else DarkCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isCurrent) SosRed.copy(alpha = 0.8f) else Color(0xFF26282F)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 86.dp, height = 54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black)
                    ) {
                        AsyncImage(
                            model = clip.thumbnailUrl,
                            contentDescription = clip.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x44000000)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = if (isCurrent) SosRed else Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = clip.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) Color.White else Color(0xFFE8E9EC),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = clip.artist,
                            fontSize = 11.sp,
                            color = if (isCurrent) Color(0xFFFF9BA1) else Color(0xFF90949E),
                            maxLines = 1
                        )
                        if (clip.highlight != null) {
                            Text(
                                text = clip.highlight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SosRed
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            likedClipIds = if (isLiked) likedClipIds - clip.id else likedClipIds + clip.id
                        }
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Beğen",
                            tint = if (isLiked) SosRed else Color(0xFF555963),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
