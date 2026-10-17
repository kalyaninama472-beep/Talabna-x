package com.example.data.model

import androidx.compose.ui.geometry.Offset

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String, val isRtl: Boolean) {
    ENGLISH("en", "English", "English", false),
    HINDI("hi", "Hindi", "हिन्दी", false),
    ARABIC("ar", "Arabic", "العربية", true)
}

enum class ShipmentStatus {
    PICKED_UP,
    EXPORT_CUSTOMS,
    AIR_TRANSIT,
    BAYAN_INSPECTION,
    DUTY_PAID,
    OUT_FOR_DELIVERY,
    DELIVERED
}

enum class CustomsStatus {
    CLEARED,
    IN_INSPECTION,
    DUTY_ASSESSED,
    DOCS_REQUIRED
}

data class Milestone(
    val id: String,
    val stage: ShipmentStatus,
    val titleEn: String,
    val titleHi: String,
    val titleAr: String,
    val location: String,
    val hubCode: String,
    val timestamp: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val descriptionAr: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean = false
)

data class ProofOfDelivery(
    val recipientName: String,
    val civilIdOrAadhaar: String,
    val deliveryTimestamp: String,
    val signatureStrokes: List<List<Offset>> = emptyList(),
    val hasPhotoProof: Boolean = false,
    val photoProofNote: String = "",
    val photoUri: String? = null,
    val courierId: String = "KWI-AGENT-408"
)

data class CustomsDeclaration(
    val declarationNo: String,
    val hsCode: String,
    val categoryName: String,
    val itemDescription: String,
    val quantity: Int,
    val declaredValueInr: Double,
    val declaredValueKwd: Double,
    val freightKwd: Double,
    val insuranceKwd: Double,
    val cifValueKwd: Double,
    val dutyRatePercent: Double = 5.0,
    val customsDutyKwd: Double,
    val bayanFeeKwd: Double = 1.000,
    val totalCustomsDueKwd: Double,
    val status: CustomsStatus = CustomsStatus.DUTY_ASSESSED,
    val portOfEntry: String = "Kuwait International Airport (KWI Air Cargo)"
)

data class Parcel(
    val id: String,
    val trackingNumber: String,
    val senderName: String,
    val senderCity: String,
    val senderCountry: String = "India",
    val senderPort: String, // e.g., BOM (Mumbai)
    val recipientName: String,
    val recipientPhone: String,
    val recipientCity: String, // e.g., Salmiya, Kuwait City
    val recipientCountry: String = "Kuwait",
    val destinationPort: String = "KWI (Kuwait Airport)",
    val weightKg: Double,
    val dimensions: String,
    val serviceType: String = "TalabnaX Priority Air Express",
    val currentStatus: ShipmentStatus,
    val customsStatus: CustomsStatus,
    val estimatedDelivery: String,
    val customsDeclaration: CustomsDeclaration,
    val milestones: List<Milestone>,
    val proofOfDelivery: ProofOfDelivery? = null
)
