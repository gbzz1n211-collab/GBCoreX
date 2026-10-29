package com.gb.corex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Color.Black,
                    surface = Color(0xFF0A0A0A),
                    primary = Color(0xFF00FF66), // Verde Hacker/Neon
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoreXMainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreXMainScreen() {
    var urlText by remember { mutableStateOf("") }
    var detectedPlatform by remember { mutableStateOf("Aguardando link...") }
    var downloadStatus by remember { mutableStateOf("") }

    fun identifyPlatform(url: String) {
        urlText = url
        detectedPlatform = when {
            url.contains("tiktok.com") -> "TikTok (Detectado)"
            url.contains("instagram.com") -> "Instagram (Detectado)"
            url.contains("youtube.com") || url.contains("youtu.be") -> "YouTube (Detectado)"
            url.contains("pinterest.com") -> "Pinterest (Detectado)"
            url.contains("facebook.com") || url.contains("fb.watch") -> "Facebook (Detectado)"
            url.contains("reddit.com") -> "Reddit (Detectado)"
            url.contains("x.com") || url.contains("twitter.com") -> "X / Twitter (Detectado)"
            url.contains("kwai.com") -> "Kwai (Detectado)"
            url.isBlank() -> "Aguardando link..."
            else -> "Plataforma Genérica / Web"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "GB COREX DOWNLOADER", 
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FF66),
                        fontSize = 18.sp
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF050505)
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Motor Ativo:", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(detectedPlatform, color = Color(0xFF00FF66), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }

            OutlinedTextField(
                value = urlText,
                onValueChange = { identifyPlatform(it) },
                label = { Text("Cole o link aqui (8 Plataformas)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00FF66),
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedLabelColor = Color(0xFF00FF66)
                )
            )

            Button(
                onClick = {
                    if (urlText.isBlank()) {
                        downloadStatus = "Insira um link válido!"
                    } else {
                        downloadStatus = "Processando requisição..."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF66))
            ) {
                Text("Baixar Mídia", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            if (downloadStatus.isNotBlank()) {
                Text(
                    text = downloadStatus,
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
