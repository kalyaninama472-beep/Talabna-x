package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Parcel
import com.example.data.model.ShipmentStatus
import com.example.ui.theme.*
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun CourierPortalScreen(
    viewModel: ParcelViewModel,
    modifier: Modifier = Modifier
) {
    val parcels by viewModel.parcels.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TalabnaDarkBg)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        // Courier Agent Header Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TalabnaSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TalabnaCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = null,
                                    tint = TalabnaCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "COURIER DISPATCH AGENT",
                                    color = TalabnaSilverDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Agent Mubarak Al-Dousari",
                                    color = TalabnaSilverLight,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Badge: KWI-AGENT-408 • Kuwait Metro Fleet",
                                    color = TalabnaCyan,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TalabnaGreen.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaGreen)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(TalabnaGreen)
                                )
                                Text("ACTIVE", color = TalabnaGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.getString("courier_header").uppercase(),
                    color = TalabnaSilverDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${parcels.size} Shipments",
                    color = TalabnaCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // List of Shipments to Deliver
        items(parcels) { parcel ->
            CourierManifestCard(
                parcel = parcel,
                viewModel = viewModel,
                onCollectPod = { viewModel.openCourierPod(parcel) },
                onViewPod = { viewModel.openPodReceipt(parcel) }
            )
        }
    }
}

@Composable
private fun CourierManifestCard(
    parcel: Parcel,
    viewModel: ParcelViewModel,
    onCollectPod: () -> Unit,
    onViewPod: () -> Unit
) {
    val isDelivered = parcel.currentStatus == ShipmentStatus.DELIVERED
    val isOutForDelivery = parcel.currentStatus == ShipmentStatus.OUT_FOR_DELIVERY

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = TalabnaSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isOutForDelivery) TalabnaCyan else TalabnaBorder
        ),
        modifier = Modifier.fillMaxWidth().testTag("manifest_card_${parcel.trackingNumber}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header: AWB & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = if (isDelivered) Icons.Default.CheckCircle else Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = if (isDelivered) TalabnaGreen else if (isOutForDelivery) TalabnaCyan else TalabnaSilverDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = parcel.trackingNumber,
                        color = TalabnaSilverLight,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isDelivered) TalabnaGreen.copy(alpha = 0.2f) else TalabnaCyan.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isDelivered) TalabnaGreen else TalabnaCyan
                    )
                ) {
                    Text(
                        text = if (isDelivered) "POD VERIFIED" else if (isOutForDelivery) "READY FOR DROP-OFF" else "IN TRANSIT",
                        color = if (isDelivered) TalabnaGreen else TalabnaCyanBright,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Consignee Destination
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF090E17))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(parcel.recipientName, color = TalabnaSilverLight, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(parcel.recipientPhone, color = TalabnaCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = TalabnaSilverDark, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(parcel.recipientCity, color = TalabnaSilver, fontSize = 11.sp)
                }
                Text(
                    text = "Package: ${parcel.customsDeclaration.categoryName} (${parcel.weightKg} kg)",
                    color = TalabnaSilverDark,
                    fontSize = 10.sp
                )
            }

            // Action: Collect POD or View POD or Advance Checkpoint
            if (isDelivered) {
                Button(
                    onClick = onViewPod,
                    colors = ButtonDefaults.buttonColors(containerColor = TalabnaSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp).testTag("view_pod_${parcel.trackingNumber}")
                ) {
                    Icon(Icons.Default.Verified, contentDescription = null, tint = TalabnaGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(viewModel.getString("view_pod_receipt"), color = TalabnaGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.selectParcel(parcel.id)
                            viewModel.advanceMilestone()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = TalabnaSurfaceVariant,
                            contentColor = TalabnaCyan
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaCyan.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(44.dp).testTag("advance_checkpoint_${parcel.trackingNumber}")
                    ) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Scan Hub", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onCollectPod,
                        colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1.3f).height(44.dp).testTag("collect_pod_${parcel.trackingNumber}")
                    ) {
                        Icon(Icons.Default.Draw, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(viewModel.getString("ready_for_pod"), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
