package com.example.sosyapim.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sosyapim.data.AnalyticsData
import com.example.sosyapim.ui.components.YouTubePlayerView
import com.example.sosyapim.ui.theme.DarkCard
import com.example.sosyapim.ui.theme.SosRed

@Composable
fun YeraltiScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val yeraltiVideoId = "Kjd_2wdFXWU"
    val yeraltiUrl = "https://www.youtube.com/watch?v=$yeraltiVideoId"

    var analytics by remember {
        mutableStateOf(AnalyticsData(visits = 1420, uniqueVisitors = 975, totalClicks = 582, youtubeClicks = 310, whatsappClicks = 142, twitterClicks = 68, copyClicks = 62))
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
            .verticalScroll(scrollState)
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(99.dp))
                .background(Color(0x33E11D48))
                .border(1.dp, Color(0x66E11D48), RoundedCornerShape(99.dp))
                .padding(horizontal = 14.dp, vertical = 5.dp)
        ) {
            Text(
                text = "YENİ TEKLİ YAYINDA",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFB7185),
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "SOS ECHOES — YERALTI",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Söz: Gökay Te • Yapım: SOS Müzik Yapım",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9CA3AF),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Video Player Box with Red Ambient Glow Border
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, Color(0x44E11D48), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            YouTubePlayerView(
                videoId = yeraltiVideoId,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // CTA Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    analytics = analytics.copy(
                        totalClicks = analytics.totalClicks + 1,
                        youtubeClicks = analytics.youtubeClicks + 1
                    )
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(yeraltiUrl))
                        context.startActivity(intent)
                    } catch (_: Exception) {}
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("yeralti_yt_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(6.dp))
                Text("▶ YouTube'da İzle & Abone Ol", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // WhatsApp Share
                OutlinedButton(
                    onClick = {
                        analytics = analytics.copy(
                            totalClicks = analytics.totalClicks + 1,
                            whatsappClicks = analytics.whatsappClicks + 1
                        )
                        try {
                            val msg = "Sos Echoes - YERALTI şimdi yayında! Dinlemek için: $yeraltiUrl"
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, msg)
                                `package` = "com.whatsapp"
                            }
                            context.startActivity(intent)
                        } catch (_: Exception) {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Sos Echoes - YERALTI: $yeraltiUrl")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Paylaş"))
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("yeralti_whatsapp_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262636))
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                // X (Twitter) Share
                OutlinedButton(
                    onClick = {
                        analytics = analytics.copy(
                            totalClicks = analytics.totalClicks + 1,
                            twitterClicks = analytics.twitterClicks + 1
                        )
                        try {
                            val tweetUrl = "https://twitter.com/intent/tweet?text=Sos%20Echoes%20-%20YERALTI%20yay%C4%B1nda!&url=$yeraltiUrl"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))
                            context.startActivity(intent)
                        } catch (_: Exception) {}
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("yeralti_twitter_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262636))
                ) {
                    Text("X (Twitter)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                // Copy Link
                OutlinedButton(
                    onClick = {
                        analytics = analytics.copy(
                            totalClicks = analytics.totalClicks + 1,
                            copyClicks = analytics.copyClicks + 1
                        )
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("YERALTI", yeraltiUrl))
                        Toast.makeText(context, "Bağlantı panoya kopyalandı!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("yeralti_copy_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262636))
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Kopyala", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Analytics Dashboard Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("yeralti_analytics_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF12121A)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262636))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📊 Canlı Tıklama & Ziyaret Analitiği",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFB7185)
                    )
                    Text(
                        text = "Aktif",
                        fontSize = 11.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Stats 2x2 Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatBox(
                        title = "Toplam Giriş",
                        value = "${analytics.visits}",
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        title = "Tekil Ziyaretçi",
                        value = "${analytics.uniqueVisitors}",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatBox(
                        title = "Toplam Tıklama",
                        value = "${analytics.totalClicks}",
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        title = "Etkileşim (CTR)",
                        value = "${analytics.ctrPercent}%",
                        valueColor = Color(0xFF33FF77),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Breakdown list
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    ChannelRow(label = "▶ YouTube Tıklama", count = analytics.youtubeClicks)
                    ChannelRow(label = "📲 WhatsApp Paylaşım", count = analytics.whatsappClicks)
                    ChannelRow(label = "🐦 X (Twitter)", count = analytics.twitterClicks)
                    ChannelRow(label = "🔗 Link Kopyalama", count = analytics.copyClicks)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StatBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF0B0B10))
            .border(1.dp, Color(0xFF262636), RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = valueColor
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = Color(0xFF9CA3AF)
            )
        }
    }
}

@Composable
private fun ChannelRow(
    label: String,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0B0B10))
            .border(1.dp, Color(0xFF22222E), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = Color(0xFFD4D6DC))
        Text(
            text = "$count",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
