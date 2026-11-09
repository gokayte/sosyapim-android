package com.example.sosyapim.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sosyapim.data.MusicCatalog
import com.example.sosyapim.ui.theme.DarkBorder
import com.example.sosyapim.ui.theme.DarkCard
import com.example.sosyapim.ui.theme.SosRed

@Composable
fun ServicesDemoScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var demoUrl by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F1115))
            .verticalScroll(scrollState)
            .padding(14.dp)
    ) {
        // Services Header
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            Text(
                text = "SOS YAPIM HİZMETLERİ",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = SosRed,
                letterSpacing = 1.1.sp
            )
            Text(
                text = "Projen İçin Uçtan Uca Destek",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(
                text = "Müzik üretiminden yayına ve hak yönetimine kadar.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFA0A5AE)
            )
        }

        // 4 Services Cards
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            MusicCatalog.services.forEach { service ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2B2E36))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(50.dp)
                                .background(SosRed, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = service.icon, fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = service.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = service.description,
                                fontSize = 13.sp,
                                color = Color(0xFFBEC2CB),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Demo Application Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("demo_application_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF191B22)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF452227))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🔴 SESİNİ BİZE DUYUR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = SosRed,
                    letterSpacing = 1.1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Yeni Bir Yıldız Sen Olabilirsin",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Kendi şarkını yazdın veya sesine güveniyor musun? Demo kaydını veya portföy linkini bize gönder, SOS Yapım prodüksiyon ekibimiz projenizi değerlendirsin.",
                    fontSize = 13.sp,
                    color = Color(0xFFB0B4BF),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Direct email link chip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF121318), RoundedCornerShape(10.dp))
                        .clickable {
                            try {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${MusicCatalog.CONTACT_EMAIL}")
                                }
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = SosRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = MusicCatalog.CONTACT_EMAIL,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Input fields
                DemoTextField(
                    label = "ADINIZ / SANATÇI ADI",
                    value = name,
                    placeholder = "Örn: Melisa Demir",
                    onValueChange = { name = it },
                    tag = "demo_name_input"
                )

                Spacer(modifier = Modifier.height(10.dp))

                DemoTextField(
                    label = "E-POSTA ADRESİ",
                    value = email,
                    placeholder = "ornek@mail.com",
                    onValueChange = { email = it },
                    tag = "demo_email_input"
                )

                Spacer(modifier = Modifier.height(10.dp))

                DemoTextField(
                    label = "TELEFON NUMARASI",
                    value = phone,
                    placeholder = "+90 5XX XXX XX XX",
                    onValueChange = { phone = it },
                    tag = "demo_phone_input"
                )

                Spacer(modifier = Modifier.height(10.dp))

                DemoTextField(
                    label = "DEMO / VİDEO LİNKİ (DRIVE, YOUTUBE, SOUNDCLOUD)",
                    value = demoUrl,
                    placeholder = "https://...",
                    onValueChange = { demoUrl = it },
                    tag = "demo_url_input"
                )

                Spacer(modifier = Modifier.height(10.dp))

                DemoTextField(
                    label = "KENDİNİZ VE PARÇANIZ HAKKINDA",
                    value = note,
                    placeholder = "Kısaca projenizden bahsedin...",
                    onValueChange = { note = it },
                    singleLine = false,
                    minLines = 3,
                    tag = "demo_note_input"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank()) {
                            statusMessage = "Lütfen adınızı ve e-posta adresinizi doldurun."
                            Toast.makeText(context, statusMessage, Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val subject = "SOS Yapım Demo Başvurusu - $name"
                        val body = """
                            Sanatçı / İsim: $name
                            E-posta: $email
                            Telefon: $phone
                            Demo Linki: $demoUrl
                            
                            Proje Notu:
                            $note
                        """.trimIndent()

                        try {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:${MusicCatalog.CONTACT_EMAIL}")
                                putExtra(Intent.EXTRA_SUBJECT, subject)
                                putExtra(Intent.EXTRA_TEXT, body)
                            }
                            context.startActivity(intent)
                            statusMessage = "E-posta uygulamanız açılıyor... Başvurunuz için teşekkürler!"
                        } catch (e: Exception) {
                            statusMessage = "Başvuru kaydedildi. E-posta: ${MusicCatalog.CONTACT_EMAIL}"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_demo_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = SosRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DEMO KAYDINI GÖNDER",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                if (statusMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = statusMessage,
                        fontSize = 12.sp,
                        color = Color(0xFF33FF77),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Site Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF14161B), RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "SOS YAPIM",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Bağımsız müziğin güçlü sesi. Sanatçılarımızın sesini dünyaya duyuruyoruz.\nİstanbul, Türkiye · Dijital Dağıtım & Telif Yönetimi",
                fontSize = 12.sp,
                color = Color(0xFF8E929B),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "© 2026 SOS Yapım. Tüm hakları saklıdır.",
                fontSize = 11.sp,
                color = Color(0xFF6B707A)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun DemoTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    tag: String,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4D6DC),
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag),
            placeholder = { Text(placeholder, color = Color(0xFF60646D), fontSize = 13.sp) },
            singleLine = singleLine,
            minLines = minLines,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF101115),
                unfocusedContainerColor = Color(0xFF101115),
                focusedBorderColor = SosRed,
                unfocusedBorderColor = DarkBorder,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
    }
}
