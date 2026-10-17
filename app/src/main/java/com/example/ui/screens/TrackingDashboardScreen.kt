package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.Milestone
import com.example.data.model.Parcel
import com.example.data.model.ShipmentStatus
import com.example.ui.theme.*
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun TrackingDashboardScreen(
    viewModel: ParcelViewModel,
    modifier: Modifier = Modifier
) {
    val parcels by viewModel.parcels.collectAsState()
    val selectedId by viewModel.selectedParcelId.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentLanguage by viewModel.language.collectAsState()

    val filteredParcels = remember(parcels, searchQuery) {
        if (searchQuery.isBlank()) parcels
        else parcels.filter {
            it.trackingNumber.contains(searchQuery, ignoreCase = true) ||
            it.recipientName.contains(searchQuery, ignoreCase = true) ||
            it.senderCity.contains(searchQuery, ignoreCase = true)
        }
    }

    val activeParcel = parcels.find { it.id == selectedId } ?: parcels.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TalabnaDarkBg)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        // Official Talabna X Hero Brand Emblem Banner
        item {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = TalabnaSurface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Brush.horizontalGradient(listOf(TalabnaCyan.copy(alpha = 0.5f), TalabnaBorder))
                ),
                modifier = Modifier.fillMaxWidth().testTag("hero_brand_banner")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.Black)
                            .border(1.5.dp, Brush.linearGradient(listOf(TalabnaCyan, TalabnaBorder)), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.talabna_logo),
                            contentDescription = "Talabna X Official Logo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "T Λ L Λ B N Λ",
                                color = TalabnaSilverLight,
                                fontWeight = FontWeight.Black,
                                fontSize = 17.sp,
                                letterSpacing = 2.5.sp
                            )
                            Text(
                                text = "X",
                                color = TalabnaCyanBright,
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp
                            )
                        }

                        Text(
                            text = "طلبنا إكس • الهند ➔ الكويت",
                            color = TalabnaCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Cross-Border Cargo Express • Customs Bayan (5%)",
                            color = TalabnaSilverDark,
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(TalabnaGreen)
                            )
                            Text(
                                text = "Kuwait Customs Bayan EDI Online",
                                color = TalabnaGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Search & Register Shipment Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = { Text(viewModel.getString("search_hint"), color = TalabnaSilverDark, fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = TalabnaCyan)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = TalabnaSilverDark)
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TalabnaCyan,
                        unfocusedBorderColor = TalabnaBorder,
                        focusedTextColor = TalabnaSilverLight,
                        unfocusedTextColor = TalabnaSilverLight,
                        focusedContainerColor = TalabnaSurface,
                        unfocusedContainerColor = TalabnaSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("tracking_search_bar")
                )

                Surface(
                    onClick = { viewModel.openNewShipmentDialog() },
                    shape = RoundedCornerShape(14.dp),
                    color = TalabnaCyan,
                    modifier = Modifier.size(54.dp).testTag("open_register_shipment_btn")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, contentDescription = "New Shipment", tint = Color.Black)
                    }
                }
            }
        }

        // Active Parcels Horizontal Selector
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.getString("quick_parcels"),
                        color = TalabnaSilverDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${filteredParcels.size} Parcels",
                        color = TalabnaCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredParcels) { p ->
                        val isSelected = p.id == activeParcel?.id
                        Surface(
                            onClick = { viewModel.selectParcel(p.id) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) TalabnaCyan.copy(alpha = 0.2f) else TalabnaSurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TalabnaCyan else TalabnaBorder
                            ),
                            modifier = Modifier.testTag("quick_parcel_${p.trackingNumber}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (p.currentStatus) {
                                                ShipmentStatus.DELIVERED -> TalabnaGreen
                                                ShipmentStatus.BAYAN_INSPECTION -> TalabnaAmber
                                                else -> TalabnaCyan
                                            }
                                        )
                                )
                                Column {
                                    Text(
                                        text = p.trackingNumber,
                                        color = if (isSelected) TalabnaCyanBright else TalabnaSilverLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = "${p.senderCity.take(6)} ➔ ${p.recipientCity.take(7)}",
                                        color = TalabnaSilverDark,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (activeParcel != null) {
            // Hero Route Card
            item {
                ActiveParcelHeroCard(
                    parcel = activeParcel,
                    viewModel = viewModel,
                    onInspectBayan = { viewModel.openBayanDialog(activeParcel) },
                    onViewPod = { viewModel.openPodReceipt(activeParcel) }
                )
            }

            // Real Checkpoint Scan / Advance Action
            item {
                Button(
                    onClick = { viewModel.advanceMilestone() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TalabnaSurfaceVariant
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, TalabnaCyan),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("advance_checkpoint_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = TalabnaCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.getString("scan_checkpoint"),
                        color = TalabnaCyanBright,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            // Timeline Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CROSS-BORDER CHECKPOINT MILESTONES",
                        color = TalabnaSilverDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = TalabnaCyan.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaCyan.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "${activeParcel.weightKg} kg • ${activeParcel.serviceType.take(16)}",
                            color = TalabnaCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Checkpoints Timeline Items
            items(activeParcel.milestones) { milestone ->
                MilestoneTimelineNode(
                    milestone = milestone,
                    language = currentLanguage,
                    isLast = milestone == activeParcel.milestones.last()
                )
            }
        }
    }
}

@Composable
private fun ActiveParcelHeroCard(
    parcel: Parcel,
    viewModel: ParcelViewModel,
    onInspectBayan: () -> Unit,
    onViewPod: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = TalabnaSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder),
        modifier = Modifier.fillMaxWidth().testTag("active_parcel_hero_card")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tracking AWB & Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AIR WAYBILL (AWB)",
                        color = TalabnaSilverDark,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = parcel.trackingNumber,
                        color = TalabnaSilverLight,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                val (badgeBg, badgeText, badgeColor) = when (parcel.currentStatus) {
                    ShipmentStatus.DELIVERED -> Triple(TalabnaGreen.copy(alpha = 0.2f), viewModel.getString("status_delivered"), TalabnaGreen)
                    ShipmentStatus.OUT_FOR_DELIVERY -> Triple(TalabnaCyan.copy(alpha = 0.2f), viewModel.getString("status_out_for_delivery"), TalabnaCyan)
                    ShipmentStatus.BAYAN_INSPECTION -> Triple(TalabnaAmber.copy(alpha = 0.2f), viewModel.getString("status_bayan_inspection"), TalabnaAmber)
                    ShipmentStatus.DUTY_PAID -> Triple(TalabnaGreen.copy(alpha = 0.2f), viewModel.getString("status_duty_paid"), TalabnaGreen)
                    ShipmentStatus.AIR_TRANSIT -> Triple(TalabnaCyan.copy(alpha = 0.2f), viewModel.getString("status_air_transit"), TalabnaCyan)
                    ShipmentStatus.EXPORT_CUSTOMS -> Triple(Color(0xFF6366F1).copy(alpha = 0.2f), viewModel.getString("status_export_customs"), Color(0xFF818CF8))
                    ShipmentStatus.PICKED_UP -> Triple(TalabnaSilverDark.copy(alpha = 0.2f), viewModel.getString("status_picked_up"), TalabnaSilverLight)
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = badgeBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor)
                ) {
                    Text(
                        text = badgeText,
                        color = badgeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        maxLines = 1
                    )
                }
            }

            // Route Visualization (India BOM ➔ Kuwait KWI)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF0A0F19),
                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Origin (India)
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("🇮🇳 INDIA", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(parcel.senderPort.take(3), color = TalabnaCyanBright, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                        Text(parcel.senderCity, color = TalabnaSilver, fontSize = 11.sp)
                    }

                    // Flight / Corridor Indicator
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlightTakeoff,
                            contentDescription = null,
                            tint = TalabnaCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Brush.horizontalGradient(listOf(TalabnaCyan, TalabnaSilver)))
                        )
                        Text(
                            text = "Air Cargo Corridor",
                            color = TalabnaSilverDark,
                            fontSize = 9.sp
                        )
                    }

                    // Destination (Kuwait)
                    Column(horizontalAlignment = Alignment.End) {
                        Text("🇰🇼 KUWAIT", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("KWI", color = TalabnaCyanBright, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                        Text(parcel.recipientCity.take(14), color = TalabnaSilver, fontSize = 11.sp)
                    }
                }
            }

            // Key Info Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoTile(
                    label = "RECIPIENT",
                    value = parcel.recipientName,
                    subtext = parcel.recipientPhone,
                    modifier = Modifier.weight(1f)
                )
                InfoTile(
                    label = "ESTIMATED DELIVERY",
                    value = parcel.estimatedDelivery,
                    subtext = "Kuwait Local Time",
                    modifier = Modifier.weight(1f)
                )
            }

            // Action Buttons: Inspect Bayan / View POD
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onInspectBayan,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = TalabnaSurfaceVariant,
                        contentColor = TalabnaCyan
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaCyan.copy(alpha = 0.5f)),
                    modifier = Modifier.weight(1f).testTag("open_bayan_doc_button")
                ) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(viewModel.getString("view_bayan_doc"), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                }

                if (parcel.proofOfDelivery != null) {
                    Button(
                        onClick = onViewPod,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TalabnaGreen),
                        modifier = Modifier.weight(1f).testTag("open_pod_receipt_button")
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(viewModel.getString("view_pod_receipt"), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoTile(
    label: String,
    value: String,
    subtext: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF090E17),
        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(label, color = TalabnaSilverDark, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(subtext, color = TalabnaSilverDark, fontSize = 10.sp, maxLines = 1)
        }
    }
}

@Composable
private fun MilestoneTimelineNode(
    milestone: Milestone,
    language: AppLanguage,
    isLast: Boolean
) {
    val title = when (language) {
        AppLanguage.HINDI -> milestone.titleHi
        AppLanguage.ARABIC -> milestone.titleAr
        AppLanguage.ENGLISH -> milestone.titleEn
    }

    val description = when (language) {
        AppLanguage.HINDI -> milestone.descriptionHi
        AppLanguage.ARABIC -> milestone.descriptionAr
        AppLanguage.ENGLISH -> milestone.descriptionEn
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            milestone.isCompleted -> TalabnaGreen
                            milestone.isCurrent -> TalabnaCyan
                            else -> TalabnaSurfaceVariant
                        }
                    )
                    .border(
                        2.dp,
                        if (milestone.isCurrent) TalabnaCyanBright else TalabnaBorder,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (milestone.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )
                } else if (milestone.isCurrent) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(
                            if (milestone.isCompleted) TalabnaGreen.copy(alpha = 0.6f)
                            else TalabnaBorder.copy(alpha = 0.5f)
                        )
                )
            }
        }

        Surface(
            modifier = Modifier.weight(1f).padding(bottom = if (isLast) 0.dp else 12.dp),
            shape = RoundedCornerShape(12.dp),
            color = if (milestone.isCurrent) TalabnaCyan.copy(alpha = 0.08f) else TalabnaSurface,
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (milestone.isCurrent) TalabnaCyan.copy(alpha = 0.6f) else TalabnaBorder.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = if (milestone.isCurrent) TalabnaCyanBright else TalabnaSilverLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = milestone.timestamp,
                        color = TalabnaSilverDark,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${milestone.location} (${milestone.hubCode})",
                    color = TalabnaCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    color = TalabnaSilverDark,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
