package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PhotoProofCapture(
    photoUri: Uri?,
    onPhotoSelected: (Uri?) -> Unit,
    locationTag: String = "Kuwait City Delivery Site (GPS: 29.3759° N, 47.9774° E)",
    modifier: Modifier = Modifier
) {
    var captureTimestamp by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            captureTimestamp = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US).format(Date())
            onPhotoSelected(uri)
        }
    }

    val hasPhoto = photoUri != null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(TalabnaSurface)
            .border(1.dp, if (hasPhoto) TalabnaGreen else TalabnaBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = if (hasPhoto) Icons.Default.CheckCircle else Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = if (hasPhoto) TalabnaGreen else TalabnaCyan,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = if (hasPhoto) "Proof Photo Attached" else "Doorstep Delivery Photo",
                    color = TalabnaSilverLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            if (hasPhoto) {
                TextButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("Change Photo", color = TalabnaCyan, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF070B14))
                .border(1.dp, if (hasPhoto) TalabnaGreen.copy(alpha = 0.5f) else Color(0xFF243247), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                // Real Image loaded via Coil AsyncImage
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Doorstep Proof Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Watermark overlay on real photo
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.Black.copy(alpha = 0.75f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaGreen)
                    ) {
                        Text(
                            text = "TALABNA X POD VERIFIED",
                            color = TalabnaGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(6.dp))
                            .padding(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = TalabnaCyan,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = locationTag,
                                color = TalabnaSilverLight,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }
                        Text(
                            text = "Timestamp: ${captureTimestamp.ifBlank { "Verified on delivery" }}",
                            color = TalabnaSilverDark,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            } else {
                // Photo Picker prompt
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = null,
                        tint = TalabnaCyan,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Attach photo of parcel handed over to consignee",
                        color = TalabnaSilverDark,
                        fontSize = 11.sp
                    )

                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("select_photo_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Select / Take Photo", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
