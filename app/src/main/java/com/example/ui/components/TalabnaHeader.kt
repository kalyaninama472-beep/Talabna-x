package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun TalabnaHeader(
    viewModel: ParcelViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val currentLanguage by viewModel.language.collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TalabnaDarkBg)
    ) {
        // Top App Bar with Logo & Language Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                        .border(1.2.dp, Brush.linearGradient(listOf(TalabnaCyan, Color(0xFF1E293B))), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.talabna_logo),
                        contentDescription = "Talabna X Official Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "T Λ L Λ B N Λ",
                            color = TalabnaSilver,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            letterSpacing = 2.5.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            text = "X",
                            color = TalabnaCyanBright,
                            fontWeight = FontWeight.Black,
                            fontSize = 19.sp,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "طلبنا X • India ➔ Kuwait Express",
                        color = TalabnaCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }

            // Language Selector Button
            Surface(
                onClick = { showLanguageDialog = true },
                shape = RoundedCornerShape(20.dp),
                color = TalabnaSurfaceVariant,
                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder),
                modifier = Modifier.testTag("language_switch_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Language",
                        tint = TalabnaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = currentLanguage.nativeName,
                        color = TalabnaSilverLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Navigation Tabs (Customer Track | Kuwait Customs | Courier POD)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TabPill(
                title = viewModel.getString("customer_mode"),
                icon = Icons.Default.LocalShipping,
                isSelected = currentTab == AppTab.TRACKING,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setTab(AppTab.TRACKING) },
                testTag = "tab_tracking"
            )

            TabPill(
                title = viewModel.getString("customs_calculator_tab"),
                icon = Icons.Default.ReceiptLong,
                isSelected = currentTab == AppTab.CUSTOMS_CALC,
                modifier = Modifier.weight(1.1f),
                onClick = { viewModel.setTab(AppTab.CUSTOMS_CALC) },
                testTag = "tab_customs"
            )

            TabPill(
                title = viewModel.getString("courier_mode"),
                icon = Icons.Default.Security,
                isSelected = currentTab == AppTab.COURIER_POD,
                modifier = Modifier.weight(1.1f),
                onClick = { viewModel.setTab(AppTab.COURIER_POD) },
                testTag = "tab_courier"
            )
        }
    }

    // Language Selection Modal
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(
                    text = "Select Language / भाषा चुनें / اختر اللغة",
                    color = TalabnaSilver,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AppLanguage.values().forEach { lang ->
                        val isSelected = currentLanguage == lang
                        Surface(
                            onClick = {
                                viewModel.setLanguage(lang)
                                showLanguageDialog = false
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) TalabnaCyan.copy(alpha = 0.15f) else TalabnaSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TalabnaCyan else TalabnaBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = lang.nativeName,
                                        color = if (isSelected) TalabnaCyan else TalabnaSilverLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = lang.displayName,
                                        color = TalabnaSilverDark,
                                        fontSize = 12.sp
                                    )
                                }
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(TalabnaCyan)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(viewModel.getString("close"), color = TalabnaCyan)
                }
            },
            containerColor = TalabnaSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun TabPill(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) TalabnaCyan.copy(alpha = 0.18f) else TalabnaSurface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) TalabnaCyan else TalabnaBorder.copy(alpha = 0.6f)
        ),
        modifier = modifier.height(44.dp).testTag(testTag)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) TalabnaCyan else TalabnaSilverDark,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = title,
                color = if (isSelected) TalabnaCyan else TalabnaSilverDark,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}
