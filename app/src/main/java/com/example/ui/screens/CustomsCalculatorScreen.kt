package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.CustomsDeclaration
import com.example.data.repository.CommodityCategory
import com.example.ui.theme.*
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun CustomsCalculatorScreen(
    viewModel: ParcelViewModel,
    modifier: Modifier = Modifier
) {
    val currentLanguage by viewModel.language.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val calcValueInr by viewModel.calcValueInr.collectAsState()
    val calcWeightKg by viewModel.calcWeightKg.collectAsState()
    val computedCustoms by viewModel.computedCustoms.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TalabnaDarkBg)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        // Screen Header Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TalabnaSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(TalabnaCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = TalabnaCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = viewModel.getString("calc_title"),
                                color = TalabnaSilverLight,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "General Administration of Customs (Kuwait) 5% Bayan Engine",
                                color = TalabnaSilverDark,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Category Selector
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = viewModel.getString("select_category"),
                    color = TalabnaSilverDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    viewModel.categories.forEach { cat ->
                        val isSelected = cat.hsCode == selectedCategory.hsCode
                        val catName = when (currentLanguage) {
                            AppLanguage.HINDI -> cat.nameHi
                            AppLanguage.ARABIC -> cat.nameAr
                            AppLanguage.ENGLISH -> cat.nameEn
                        }

                        Surface(
                            onClick = { viewModel.setCalcCategory(cat) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) TalabnaCyan.copy(alpha = 0.16f) else TalabnaSurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TalabnaCyan else TalabnaBorder
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("category_${cat.hsCode}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = catName,
                                        color = if (isSelected) TalabnaCyanBright else TalabnaSilverLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "HS: ${cat.hsCode} • Duty Rate: ${cat.dutyRatePercent}%",
                                        color = TalabnaSilverDark,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = TalabnaCyan,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Input Fields: Invoice Value (INR) & Package Weight (kg)
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TalabnaSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "COMMERCIAL INVOICE INPUTS",
                        color = TalabnaSilverDark,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    OutlinedTextField(
                        value = calcValueInr,
                        onValueChange = { viewModel.setCalcValueInr(it) },
                        label = { Text(viewModel.getString("invoice_value"), color = TalabnaSilverDark) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Text("₹", color = TalabnaCyan, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(start = 12.dp))
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = Color(0xFF090E17),
                            unfocusedContainerColor = Color(0xFF090E17)
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("customs_inr_input")
                    )

                    // Quick value presets
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("25000", "50000", "100000", "200000").forEach { preset ->
                            Surface(
                                onClick = { viewModel.setCalcValueInr(preset) },
                                shape = RoundedCornerShape(8.dp),
                                color = TalabnaSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "₹${preset.take(3)}k",
                                    color = TalabnaSilver,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = calcWeightKg,
                        onValueChange = { viewModel.setCalcWeightKg(it) },
                        label = { Text(viewModel.getString("package_weight"), color = TalabnaSilverDark) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        leadingIcon = {
                            Icon(Icons.Default.Scale, contentDescription = null, tint = TalabnaCyan, modifier = Modifier.size(18.dp))
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = Color(0xFF090E17),
                            unfocusedContainerColor = Color(0xFF090E17)
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("customs_weight_input")
                    )
                }
            }
        }

        // Computed Tax & Bayan Duty Breakdown
        if (computedCustoms != null) {
            val customs = computedCustoms!!
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0A0F19),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, TalabnaCyan.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "AUTOMATED DUTY & TAX ASSESSMENT",
                                color = TalabnaCyanBright,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = TalabnaGreen.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaGreen)
                            ) {
                                Text(
                                    text = "5% GCC COMMON LAW",
                                    color = TalabnaGreen,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Currency Equivalents Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            CurrencyCard("INR EQUIV", "₹ ${"%,.0f".format(customs.declaredValueInr)}", Modifier.weight(1f))
                            CurrencyCard("KWD CIF", "${"%.3f".format(customs.cifValueKwd)} KWD", Modifier.weight(1f))
                            val usdEquiv = customs.cifValueKwd * 3.26
                            CurrencyCard("USD CIF", "$ ${"%.1f".format(usdEquiv)}", Modifier.weight(1f))
                        }

                        HorizontalDivider(color = TalabnaBorder, modifier = Modifier.padding(vertical = 4.dp))

                        TaxLine("Commercial Invoice Value", "${"%.3f".format(customs.declaredValueKwd)} KWD")
                        TaxLine("Air Cargo Freight Charge", "${"%.3f".format(customs.freightKwd)} KWD")
                        TaxLine("Customs Insurance (1%)", "${"%.3f".format(customs.insuranceKwd)} KWD")
                        TaxLine("Total CIF Valuation", "${"%.3f".format(customs.cifValueKwd)} KWD", isBold = true)
                        TaxLine("Kuwait Customs Duty (5%)", "${"%.3f".format(customs.customsDutyKwd)} KWD", isCyan = true)
                        TaxLine("Bayan Electronic Filing Fee", "${"%.3f".format(customs.bayanFeeKwd)} KWD")

                        HorizontalDivider(color = TalabnaCyan.copy(alpha = 0.6f), modifier = Modifier.padding(vertical = 4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("TOTAL CUSTOMS DUE (KWD)", color = TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("≈ ₹ ${"%,.0f".format(customs.totalCustomsDueKwd * 272.5)} Indian Rupees", color = TalabnaSilverDark, fontSize = 11.sp)
                            }
                            Text(
                                text = "${"%.3f".format(customs.totalCustomsDueKwd)} KWD",
                                color = TalabnaCyanBright,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Button to register actual shipment and view Bayan
                        Button(
                            onClick = {
                                val inrVal = calcValueInr.toDoubleOrNull() ?: 50000.0
                                val wt = calcWeightKg.toDoubleOrNull() ?: 2.0
                                viewModel.registerNewShipment(
                                    senderName = "Commercial Exporter (India)",
                                    senderCity = "Mumbai BOM Port",
                                    recipientName = "Consignee Importer (Kuwait)",
                                    recipientPhone = "+965 9900 1122",
                                    recipientCity = "Salmiya, Hawally",
                                    category = selectedCategory,
                                    declaredValueInr = inrVal,
                                    weightKg = wt
                                )
                                val registered = viewModel.parcels.value.firstOrNull()
                                if (registered != null) {
                                    viewModel.openBayanDialog(registered)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("generate_bayan_button")
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(viewModel.getString("generate_customs_bayan"), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = TalabnaSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = TalabnaSilverDark, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = TalabnaSilverLight, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}

@Composable
private fun TaxLine(
    label: String,
    value: String,
    isBold: Boolean = false,
    isCyan: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = if (isCyan) TalabnaCyan else TalabnaSilverDark,
            fontSize = 11.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            color = if (isCyan) TalabnaCyanBright else TalabnaSilverLight,
            fontSize = 12.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )
    }
}
