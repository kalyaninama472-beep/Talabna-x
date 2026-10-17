package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.model.Parcel
import com.example.ui.theme.*

@Composable
fun PodReceiptDialog(
    parcel: Parcel,
    onDismiss: () -> Unit
) {
    val pod = parcel.proofOfDelivery ?: return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .testTag("pod_receipt_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0D121D),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TalabnaGreen.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Header
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
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = TalabnaGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = "PROOF OF DELIVERY (POD)",
                                color = TalabnaSilverLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "إثبات التسليم المعتمد رسميًا",
                                color = TalabnaGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_pod_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TalabnaSilver)
                    }
                }

                HorizontalDivider(color = TalabnaBorder, modifier = Modifier.padding(vertical = 10.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Shipment Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TalabnaSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("SHIPMENT TRACKING", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = parcel.trackingNumber,
                                color = TalabnaCyanBright,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Origin: ${parcel.senderCity} ➔ Destination: ${parcel.recipientCity}",
                                color = TalabnaSilver,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Recipient Verification Details
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TalabnaSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("RECIPIENT CONFIRMATION", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Signed By:", color = TalabnaSilverDark, fontSize = 12.sp)
                                Text(pod.recipientName, color = TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Kuwait Civil ID / ID Ref:", color = TalabnaSilverDark, fontSize = 12.sp)
                                Text(pod.civilIdOrAadhaar, color = TalabnaCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Delivery Timestamp:", color = TalabnaSilverDark, fontSize = 12.sp)
                                Text(pod.deliveryTimestamp, color = TalabnaSilverLight, fontSize = 11.sp)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Dispatch Courier ID:", color = TalabnaSilverDark, fontSize = 12.sp)
                                Text(pod.courierId, color = TalabnaSilverLight, fontSize = 12.sp)
                            }
                        }
                    }

                    // Signature Render Canvas
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF070B14),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaCyan.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("DIGITAL RECIPIENT SIGNATURE", color = TalabnaCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Icon(Icons.Default.Fingerprint, contentDescription = null, tint = TalabnaCyan, modifier = Modifier.size(16.dp))
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF05070D))
                                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    pod.signatureStrokes.forEach { strokeList ->
                                        if (strokeList.size > 1) {
                                            val path = Path().apply {
                                                moveTo(strokeList.first().x, strokeList.first().y)
                                                for (i in 1 until strokeList.size) {
                                                    lineTo(strokeList[i].x, strokeList[i].y)
                                                }
                                            }
                                            drawPath(
                                                path = path,
                                                color = TalabnaCyanBright,
                                                style = Stroke(
                                                    width = 3.5.dp.toPx(),
                                                    cap = StrokeCap.Round,
                                                    join = StrokeJoin.Round
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Photo Proof Card with Real Image if attached
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TalabnaSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = TalabnaGreen, modifier = Modifier.size(16.dp))
                                Text("DOORSTEP DELIVERY PHOTO PROOF", color = TalabnaGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }

                            if (pod.photoUri != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, TalabnaBorder, RoundedCornerShape(8.dp))
                                ) {
                                    AsyncImage(
                                        model = pod.photoUri,
                                        contentDescription = "Delivered Photo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Text(
                                text = pod.photoProofNote,
                                color = TalabnaSilverLight,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close POD Receipt", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
