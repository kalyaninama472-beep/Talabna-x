package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.CustomsDeclaration
import com.example.data.model.Parcel
import com.example.ui.theme.*

@Composable
fun BayanDocumentDialog(
    parcel: Parcel,
    onDismiss: () -> Unit
) {
    val declaration = parcel.customsDeclaration

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .testTag("bayan_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0F1522),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TalabnaCyan.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "STATE OF KUWAIT",
                            color = TalabnaSilverLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "الإدارة العامة للجمارك - بيان جمركي",
                            color = TalabnaCyan,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_bayan_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TalabnaSilver)
                    }
                }

                HorizontalDivider(color = TalabnaBorder, modifier = Modifier.padding(vertical = 10.dp))

                // Scrollable Document Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Document Badge & Barcode Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF070B14),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "BAYAN DECLARATION NO.",
                                    color = TalabnaSilverDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = declaration.declarationNo,
                                    color = TalabnaCyanBright,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Air Waybill: ${parcel.trackingNumber}",
                                    color = TalabnaSilver,
                                    fontSize = 11.sp
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.QrCode2,
                                contentDescription = "Bayan QR Verification",
                                tint = TalabnaSilverLight,
                                modifier = Modifier.size(46.dp)
                            )
                        }
                    }

                    // Parties Info: Shipper & Consignee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = TalabnaSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("EXPORTER (INDIA)", color = TalabnaSilverDark, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Text(parcel.senderName, color = TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(parcel.senderCity, color = TalabnaSilverDark, fontSize = 11.sp)
                                Text("Port: ${parcel.senderPort}", color = TalabnaCyan, fontSize = 10.sp)
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = TalabnaSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("CONSIGNEE (KUWAIT)", color = TalabnaSilverDark, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Text(parcel.recipientName, color = TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(parcel.recipientCity, color = TalabnaSilverDark, fontSize = 11.sp)
                                Text(declaration.portOfEntry, color = TalabnaCyan, fontSize = 10.sp, maxLines = 1)
                            }
                        }
                    }

                    // Commodity & HS Code Details
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = TalabnaSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("HS TARIFF CODE", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(declaration.hsCode, color = TalabnaCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(declaration.itemDescription, color = TalabnaSilverLight, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text("Category: ${declaration.categoryName} • Qty: ${declaration.quantity} unit(s)", color = TalabnaSilverDark, fontSize = 11.sp)
                        }
                    }

                    // Automated Duty & Tax Breakdown Table
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF090E17),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaCyan.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "KUWAIT CUSTOMS DUTY ASSESSMENT (٥٪)",
                                color = TalabnaCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )

                            CustomsRow("Declared Invoice Value (INR ₹)", "₹ ${"%,.0f".format(declaration.declaredValueInr)}")
                            CustomsRow("Converted Invoice Value (KWD)", "${"%.3f".format(declaration.declaredValueKwd)} KWD")
                            CustomsRow("Air Freight (KWD)", "${"%.3f".format(declaration.freightKwd)} KWD")
                            CustomsRow("Insurance Assessment (1%)", "${"%.3f".format(declaration.insuranceKwd)} KWD")
                            HorizontalDivider(color = TalabnaBorder.copy(alpha = 0.5f))
                            CustomsRow("Total CIF Valuation", "${"%.3f".format(declaration.cifValueKwd)} KWD", isBold = true)
                            CustomsRow("Kuwait Customs Duty (5%)", "${"%.3f".format(declaration.customsDutyKwd)} KWD", isAccent = true)
                            CustomsRow("Electronic Bayan Filing Fee", "${"%.3f".format(declaration.bayanFeeKwd)} KWD")
                            HorizontalDivider(color = TalabnaCyan.copy(alpha = 0.5f))
                            CustomsRow(
                                label = "TOTAL PAYABLE CUSTOMS CHARGES",
                                value = "${"%.3f".format(declaration.totalCustomsDueKwd)} KWD",
                                isBold = true,
                                isHighlight = true
                            )
                        }
                    }

                    // Official Stamp Box
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = TalabnaGreen.copy(alpha = 0.12f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TalabnaGreen)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = TalabnaGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "KUWAIT CUSTOMS ELECTRONIC CLEARANCE PASS",
                                    color = TalabnaGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Authorized under GCC Common Customs Law for import into the State of Kuwait.",
                                    color = TalabnaSilver,
                                    fontSize = 10.sp
                                )
                            }
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
                    Text("Close Document", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CustomsRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    isAccent: Boolean = false,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = if (isHighlight) TalabnaSilverLight else if (isAccent) TalabnaCyan else TalabnaSilverDark,
            fontSize = if (isHighlight) 12.sp else 11.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            color = if (isHighlight) TalabnaCyan else if (isAccent) TalabnaCyanBright else TalabnaSilverLight,
            fontSize = if (isHighlight) 14.sp else 12.sp,
            fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace
        )
    }
}
