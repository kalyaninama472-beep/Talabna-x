package com.example.ui.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.CustomsDeclaration
import com.example.data.model.Parcel
import com.example.data.repository.CommodityCategory
import com.example.data.repository.ParcelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppTab {
    TRACKING,
    CUSTOMS_CALC,
    COURIER_POD
}

class ParcelViewModel(
    val repository: ParcelRepository = ParcelRepository()
) : ViewModel() {

    val parcels: StateFlow<List<Parcel>> = repository.parcels
    val activeToast: StateFlow<String?> = repository.activeToast

    private val _currentTab = MutableStateFlow(AppTab.TRACKING)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _selectedParcelId = MutableStateFlow("p-1")
    val selectedParcelId: StateFlow<String> = _selectedParcelId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Customs Calculator State
    val categories = repository.categories
    private val _selectedCategory = MutableStateFlow(categories[0])
    val selectedCategory: StateFlow<CommodityCategory> = _selectedCategory.asStateFlow()

    private val _calcValueInr = MutableStateFlow("85000")
    val calcValueInr: StateFlow<String> = _calcValueInr.asStateFlow()

    private val _calcWeightKg = MutableStateFlow("3.5")
    val calcWeightKg: StateFlow<String> = _calcWeightKg.asStateFlow()

    private val _computedCustoms = MutableStateFlow<CustomsDeclaration?>(null)
    val computedCustoms: StateFlow<CustomsDeclaration?> = _computedCustoms.asStateFlow()

    // Modal state for viewing Bayan Document
    private val _bayanDialogParcel = MutableStateFlow<Parcel?>(null)
    val bayanDialogParcel: StateFlow<Parcel?> = _bayanDialogParcel.asStateFlow()

    // Modal state for viewing finalized POD
    private val _podReceiptParcel = MutableStateFlow<Parcel?>(null)
    val podReceiptParcel: StateFlow<Parcel?> = _podReceiptParcel.asStateFlow()

    // Courier POD capture sheet state
    private val _courierPodParcel = MutableStateFlow<Parcel?>(null)
    val courierPodParcel: StateFlow<Parcel?> = _courierPodParcel.asStateFlow()

    // New Shipment Dialog state
    private val _showNewShipmentDialog = MutableStateFlow(false)
    val showNewShipmentDialog: StateFlow<Boolean> = _showNewShipmentDialog.asStateFlow()

    init {
        recomputeCustoms()
    }

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    fun selectParcel(id: String) {
        _selectedParcelId.value = id
        repository.selectParcel(id)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCalcCategory(cat: CommodityCategory) {
        _selectedCategory.value = cat
        recomputeCustoms()
    }

    fun setCalcValueInr(inr: String) {
        _calcValueInr.value = inr
        recomputeCustoms()
    }

    fun setCalcWeightKg(kg: String) {
        _calcWeightKg.value = kg
        recomputeCustoms()
    }

    fun recomputeCustoms() {
        val valInr = _calcValueInr.value.toDoubleOrNull() ?: 10000.0
        val weight = _calcWeightKg.value.toDoubleOrNull() ?: 1.0
        _computedCustoms.value = repository.calculateCustoms(_selectedCategory.value, valInr, weight)
    }

    fun openNewShipmentDialog() {
        _showNewShipmentDialog.value = true
    }

    fun closeNewShipmentDialog() {
        _showNewShipmentDialog.value = false
    }

    fun registerNewShipment(
        senderName: String,
        senderCity: String,
        recipientName: String,
        recipientPhone: String,
        recipientCity: String,
        category: CommodityCategory,
        declaredValueInr: Double,
        weightKg: Double
    ) {
        val newParcel = repository.registerNewShipment(
            senderName = senderName,
            senderCity = senderCity,
            recipientName = recipientName,
            recipientPhone = recipientPhone,
            recipientCity = recipientCity,
            category = category,
            declaredValueInr = declaredValueInr,
            weightKg = weightKg
        )
        _selectedParcelId.value = newParcel.id
        _currentTab.value = AppTab.TRACKING
        _showNewShipmentDialog.value = false
    }

    fun advanceMilestone() {
        val currentId = _selectedParcelId.value
        repository.advanceMilestone(currentId)
    }

    fun openBayanDialog(parcel: Parcel) {
        _bayanDialogParcel.value = parcel
    }

    fun closeBayanDialog() {
        _bayanDialogParcel.value = null
    }

    fun openPodReceipt(parcel: Parcel) {
        _podReceiptParcel.value = parcel
    }

    fun closePodReceipt() {
        _podReceiptParcel.value = null
    }

    fun openCourierPod(parcel: Parcel) {
        _courierPodParcel.value = parcel
    }

    fun closeCourierPod() {
        _courierPodParcel.value = null
    }

    fun submitCourierPod(
        parcelId: String,
        recipientName: String,
        civilId: String,
        signatureStrokes: List<List<Offset>>,
        photoNote: String,
        photoUri: String? = null
    ) {
        repository.submitProofOfDelivery(
            parcelId = parcelId,
            recipientName = recipientName,
            civilId = civilId,
            signatureStrokes = signatureStrokes,
            photoNote = photoNote,
            photoUri = photoUri
        )
        _courierPodParcel.value = null
    }

    fun dismissToast() {
        repository.dismissToast()
    }

    fun getString(key: String): String {
        return AppStrings.get(key, _language.value)
    }
}
