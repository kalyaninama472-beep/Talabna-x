package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AppLanguage
import com.example.ui.theme.*
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun NewShipmentDialog(
    viewModel: ParcelViewModel,
    onDismiss: () -> Unit
) {
    val currentLanguage by viewModel.language.collectAsState()
    var senderName by remember { mutableStateOf("") }
    var senderCity by remember { mutableStateOf("Mumbai, Maharashtra") }
    var recipientName by remember { mutableStateOf("") }
    var recipientPhone by remember { mutableStateOf("+965 ") }
    var recipientCity by remember { mutableStateOf("Salmiya, Kuwait") }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var declaredValueInr by remember { mutableStateOf("50000") }
    var weightKg by remember { mutableStateOf("2.5") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val categories = viewModel.categories

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .testTag("new_shipment_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0C111C),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, TalabnaCyan)
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, tint = TalabnaCyan, modifier = Modifier.size(22.dp))
                        Text(
                            text = viewModel.getString("book_shipment"),
                            color = TalabnaSilverLight,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TalabnaSilver)
                    }
                }

                HorizontalDivider(color = TalabnaBorder, modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("ORIGIN IN INDIA", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

                    OutlinedTextField(
                        value = senderName,
                        onValueChange = { senderName = it; errorMsg = null },
                        label = { Text("Shipper / Exporter Name", color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("new_sender_name_input")
                    )

                    OutlinedTextField(
                        value = senderCity,
                        onValueChange = { senderCity = it },
                        label = { Text("Origin City (India)", color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("new_sender_city_input")
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("DESTINATION IN KUWAIT", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

                    OutlinedTextField(
                        value = recipientName,
                        onValueChange = { recipientName = it; errorMsg = null },
                        label = { Text("Consignee / Recipient Name", color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("new_recipient_name_input")
                    )

                    OutlinedTextField(
                        value = recipientPhone,
                        onValueChange = { recipientPhone = it },
                        label = { Text("Recipient Phone Number", color = TalabnaSilverDark) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = recipientCity,
                        onValueChange = { recipientCity = it },
                        label = { Text("Kuwait Area / Governorate", color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("CARGO SPECIFICATION & VALUE", color = TalabnaSilverDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Category (HS Classification)", color = TalabnaSilverDark, fontSize = 11.sp)
                        categories.forEachIndexed { index, cat ->
                            val isSel = index == selectedCategoryIndex
                            val name = when (currentLanguage) {
                                AppLanguage.HINDI -> cat.nameHi
                                AppLanguage.ARABIC -> cat.nameAr
                                AppLanguage.ENGLISH -> cat.nameEn
                            }
                            Surface(
                                onClick = { selectedCategoryIndex = index },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) TalabnaCyan.copy(alpha = 0.2f) else TalabnaSurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSel) TalabnaCyan else TalabnaBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(name, color = if (isSel) TalabnaCyanBright else TalabnaSilverLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    Text("HS ${cat.hsCode}", color = TalabnaSilverDark, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = declaredValueInr,
                            onValueChange = { declaredValueInr = it },
                            label = { Text("Value (INR ₹)", color = TalabnaSilverDark) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = TalabnaCyan,
                                unfocusedBorderColor = TalabnaBorder,
                                focusedTextColor = TalabnaSilverLight,
                                unfocusedTextColor = TalabnaSilverLight,
                                focusedContainerColor = TalabnaSurface,
                                unfocusedContainerColor = TalabnaSurface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = weightKg,
                            onValueChange = { weightKg = it },
                            label = { Text("Weight (kg)", color = TalabnaSilverDark) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = TalabnaCyan,
                                unfocusedBorderColor = TalabnaBorder,
                                focusedTextColor = TalabnaSilverLight,
                                unfocusedTextColor = TalabnaSilverLight,
                                focusedContainerColor = TalabnaSurface,
                                unfocusedContainerColor = TalabnaSurface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (errorMsg != null) {
                        Text(errorMsg ?: "", color = TalabnaRed, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (senderName.isBlank()) {
                            errorMsg = "Please enter shipper name"
                            return@Button
                        }
                        if (recipientName.isBlank()) {
                            errorMsg = "Please enter recipient name"
                            return@Button
                        }
                        val valInr = declaredValueInr.toDoubleOrNull() ?: 25000.0
                        val wt = weightKg.toDoubleOrNull() ?: 1.0

                        viewModel.registerNewShipment(
                            senderName = senderName,
                            senderCity = senderCity,
                            recipientName = recipientName,
                            recipientPhone = recipientPhone,
                            recipientCity = recipientCity,
                            category = categories[selectedCategoryIndex],
                            declaredValueInr = valInr,
                            weightKg = wt
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("confirm_register_shipment_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Register Shipment & Generate AWB", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
