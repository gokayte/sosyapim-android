package com.example.sosyapim.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sosyapim.data.Artist
import com.example.sosyapim.data.MusicCatalog
import com.example.sosyapim.data.MusicClip
import com.example.sosyapim.ui.theme.DarkBorder
import com.example.sosyapim.ui.theme.DarkCard
import com.example.sosyapim.ui.theme.SosRed

@Composable
fun ArtistsScreen(
    onArtistSelected: (MusicClip) -> Unit,
    modifier: Modifier = Modifier
) {
    var artistSearch by remember { mutableStateOf("") }

    val filteredArtists = remember(artistSearch) {
        if (artistSearch.isBlank()) {
            MusicCatalog.artists
        } else {
            val q = artistSearch.trim().lowercase()
            MusicCatalog.artists.filter { it.name.lowercase().contains(q) }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F1115))
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        // Heading Item
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "YETENEKLERİMİZ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = SosRed,
                    letterSpacing = 1.1.sp
                )
                Text(
                    text = "Tüm Sanatçılarımız",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Text(
                    text = "Klip listemizdeki ${MusicCatalog.artists.size} değerli sanatçı.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFA0A5AE)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = artistSearch,
                    onValueChange = { artistSearch = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("artist_search_input"),
                    placeholder = {
                        Text("Sanatçı ara...", color = Color(0xFF757A84))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ara",
                            tint = Color(0xFFA5A8B0)
                        )
                    },
                    trailingIcon = {
                        if (artistSearch.isNotEmpty()) {
                            IconButton(onClick = { artistSearch = "" }) {
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
        }

        // Artist Cards
        items(filteredArtists) { artist ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("artist_card_${artist.name.replace(" ", "_")}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF282B33))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Artist Avatar with Red Border
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .border(2.5.dp, SosRed, CircleShape)
                            .background(Color.Black)
                    ) {
                        AsyncImage(
                            model = artist.avatarUrl,
                            contentDescription = artist.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = artist.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "${artist.clipCount} klip",
                        fontSize = 11.sp,
                        color = Color(0xFF9EA3AD),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val clip = MusicCatalog.allClips.getOrNull(artist.sampleClipIndex)
                                ?: MusicCatalog.allClips[0]
                            onArtistSelected(clip)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SosRed),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("listen_artist_${artist.name.replace(" ", "_")}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Klipleri Dinle",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
