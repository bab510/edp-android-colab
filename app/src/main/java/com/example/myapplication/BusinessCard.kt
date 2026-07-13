package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun BusinessCard() {
    val darkBackground = Color(0xFF0F0F0F)
    val goldSideBar = Color(0xFF433912) // Rich Dark Gold/Olive
    val highlightGold = Color(0xFFFFC107) // Industrial Gold

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Left Decorative Bar (matches reference)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.28f)
                .background(goldSideBar)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Picture (Circular Frame)
                Surface(
                    modifier = Modifier
                        .size(130.dp)
                        .offset(x = (-10).dp), // Slight overlap with sidebar
                    shape = CircleShape,
                    color = Color.DarkGray,
                    border = BorderStroke(4.dp, highlightGold)
                ) {
                    // INSTRUCTIONS: 
                    // 1. Download the image you sent.
                    // 2. Rename it to 'profile_pic.png'.
                    // 3. Right-click 'res/drawable' in Android Studio -> 'Open in Explorer'.
                    // 4. Paste your image there.
                    // 5. Change 'R.drawable.ic_launcher_background' to 'R.drawable.profile_pic' below.
                    
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background), 
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Name and Title Section
                Column {
                    Text(
                        text = "BOB BENEDICT\nAGUSTERO",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 28.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "SENIOR SOFTWARE ENGINEER",
                        color = highlightGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Contact Details
            Column(
                modifier = Modifier.padding(start = 135.dp)
            ) {
                ContactItem(Icons.Default.Phone, "0955 22 33 444")
                Spacer(modifier = Modifier.height(20.dp))
                ContactItem(Icons.Default.Email, "bob.agustero@email.com")
                Spacer(modifier = Modifier.height(20.dp))
                ContactItem(Icons.Default.LocationOn, "Cagayan de Oro, PH")
            }
        }
    }
}

@Composable
fun ContactItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.5.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    MyApplicationTheme {
        BusinessCard()
    }
}
