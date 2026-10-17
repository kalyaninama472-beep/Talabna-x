package com.example.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Parcel
import com.example.ui.theme.*
import com.example.ui.viewmodel.ParcelViewModel

@Composable
fun CourierPodSheet(
    parcel: Parcel,
    viewModel: ParcelViewModel,
    onDismiss: () -> Unit
) {
    var recipientName by remember { mutableStateOf(parcel.recipientName) }
    var civilId by remember { mutableStateOf("") }
    var signatureStrokes by remember { mutableStateOf<List<List<Offset>>>(emptyList()) }
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var photoNote by remember { mutableStateOf("Doorstep delivery verified at ${parcel.recipientCity}") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .testTag("courier_pod_sheet"),
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
                    Column {
                        Text(
                            text = viewModel.getString("pod_dialog_title"),
                            color = TalabnaSilverLight,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "AWB: ${parcel.trackingNumber}",
                            color = TalabnaCyan,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_pod_sheet_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TalabnaSilver)
                    }
                }

                HorizontalDivider(color = TalabnaBorder, modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Recipient Name Field
                    OutlinedTextField(
                        value = recipientName,
                        onValueChange = {
                            recipientName = it
                            errorMsg = null
                        },
                        label = { Text(viewModel.getString("recipient_name"), color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("recipient_name_input")
                    )

                    // Civil ID Field
                    OutlinedTextField(
                        value = civilId,
                        onValueChange = {
                            civilId = it
                            errorMsg = null
                        },
                        placeholder = { Text("12-digit Civil ID or Passport No.", color = TalabnaSilverDark, fontSize = 12.sp) },
                        label = { Text(viewModel.getString("civil_id_number"), color = TalabnaSilverDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TalabnaCyan,
                            unfocusedBorderColor = TalabnaBorder,
                            focusedTextColor = TalabnaSilverLight,
                            unfocusedTextColor = TalabnaSilverLight,
                            focusedContainerColor = TalabnaSurface,
                            unfocusedContainerColor = TalabnaSurface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("civil_id_input")
                    )

                    // Photo Proof Component using Android Photo Picker
                    PhotoProofCapture(
                        photoUri = selectedPhotoUri,
                        onPhotoSelected = { selectedPhotoUri = it },
                        locationTag = "${parcel.recipientCity} (Kuwait Delivery Zone)"
                    )

                    // Signature Pad
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = viewModel.getString("signature_canvas_label"),
                            color = TalabnaSilverLight,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        SignaturePad(
                            strokes = signatureStrokes,
                            onStrokesChange = {
                                signatureStrokes = it
                                errorMsg = null
                            },
                            promptLabel = "Sign recipient signature here",
                            clearLabel = viewModel.getString("clear_signature")
                        )
                    }

                    if (errorMsg != null) {
                        Text(
                            text = errorMsg ?: "",
                            color = TalabnaRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (recipientName.isBlank()) {
                            errorMsg = "Please enter recipient name"
                            return@Button
                        }
                        if (civilId.isBlank()) {
                            errorMsg = "Please enter Kuwait Civil ID or ID reference"
                            return@Button
                        }
                        if (signatureStrokes.isEmpty()) {
                            errorMsg = "Please draw recipient signature on canvas above"
                            return@Button
                        }

                        viewModel.submitCourierPod(
                            parcelId = parcel.id,
                            recipientName = recipientName,
                            civilId = civilId,
                            signatureStrokes = signatureStrokes,
                            photoNote = photoNote,
                            photoUri = selectedPhotoUri?.toString()
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TalabnaCyan),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_pod_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.getString("confirm_delivery_btn"),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
