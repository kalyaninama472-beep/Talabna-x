package com.example.data.repository

import androidx.compose.ui.geometry.Offset
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

data class CommodityCategory(
    val nameEn: String,
    val nameHi: String,
    val nameAr: String,
    val hsCode: String,
    val dutyRatePercent: Double = 5.0,
    val defaultFreightPerKgInr: Double = 450.0
)

class ParcelRepository {

    val categories = listOf(
        CommodityCategory("Consumer Electronics & Gadgets", "इलेक्ट्रॉनिक्स और गैजेट्स", "إلكترونيات وأجهزة ذكية", "8517.13.00", 5.0, 650.0),
        CommodityCategory("Textiles, Silk Sarees & Garments", "कपड़ा और रेशम वस्त्र", "أقمشة ومنسوجات وملابس حريرية", "6204.42.00", 5.0, 400.0),
        CommodityCategory("Indian Spices & Foodstuffs", "भारतीय मसाले और खाद्य पदार्थ", "بهارات وتوابل ومواد غذائية هندية", "0910.99.10", 5.0, 350.0),
        CommodityCategory("Commercial Documents & Contracts", "वाणिज्यिक और कानूनी दस्तावेज", "مستندات وعقود تجارية رسمية", "4901.99.00", 0.0, 500.0),
        CommodityCategory("Ayurvedic & Herbal Cosmetics", "आयुर्वेदिक और हर्बल सौंदर्य उत्पाद", "مستحضرات عشبية وتجميل أيورفيدا", "3304.99.00", 5.0, 480.0),
        CommodityCategory("Jewelry & Silver Artifacts", "आभूषण और चांदी की कलाकृतियाँ", "مجوهرات ومصنوعات فضية", "7113.11.00", 5.0, 950.0)
    )

    private val initialParcels = listOf(
        Parcel(
            id = "p-1",
            trackingNumber = "TLX-8842-KWI",
            senderName = "TechVenture Exports Pvt Ltd",
            senderCity = "Mumbai, Maharashtra",
            senderCountry = "India",
            senderPort = "BOM (Chhatrapati Shivaji Maharaj Intl)",
            recipientName = "Fahad Al-Mutawa",
            recipientPhone = "+965 9988 7766",
            recipientCity = "Salmiya, Block 4, Hawally",
            recipientCountry = "Kuwait",
            destinationPort = "KWI (Kuwait International Airport Air Cargo)",
            weightKg = 4.2,
            dimensions = "32 x 24 x 18 cm",
            serviceType = "TalabnaX Priority Air Express",
            currentStatus = ShipmentStatus.BAYAN_INSPECTION,
            customsStatus = CustomsStatus.IN_INSPECTION,
            estimatedDelivery = "Tomorrow, 2:00 PM",
            customsDeclaration = CustomsDeclaration(
                declarationNo = "KAC-BAYAN-2026-90412",
                hsCode = "8517.13.00",
                categoryName = "Consumer Electronics & Gadgets",
                itemDescription = "Industrial Sensor Controllers & Mobile Terminals",
                quantity = 6,
                declaredValueInr = 125000.0,
                declaredValueKwd = 458.700,
                freightKwd = 22.500,
                insuranceKwd = 4.800,
                cifValueKwd = 486.000,
                dutyRatePercent = 5.0,
                customsDutyKwd = 24.300,
                bayanFeeKwd = 1.000,
                totalCustomsDueKwd = 25.300,
                status = CustomsStatus.IN_INSPECTION
            ),
            milestones = listOf(
                Milestone(
                    id = "m-1",
                    stage = ShipmentStatus.PICKED_UP,
                    titleEn = "Shipment Picked Up & Scanned",
                    titleHi = "पार्सल उठाया गया और स्कैन किया गया",
                    titleAr = "تم استلام الشحنة وتوثيقها",
                    location = "Andheri Cargo Hub, Mumbai",
                    hubCode = "BOM-HUB-1",
                    timestamp = "Oct 14, 09:30 AM",
                    descriptionEn = "Consignment received from sender with export commercial invoice.",
                    descriptionHi = "वाणिज्यिक चालान के साथ प्रेषक से खेप प्राप्त हुई।",
                    descriptionAr = "تم استلام الشحنة من المرسل مرفقة بالفاتورة التجارية.",
                    isCompleted = true
                ),
                Milestone(
                    id = "m-2",
                    stage = ShipmentStatus.EXPORT_CUSTOMS,
                    titleEn = "Indian Customs Clearance Passed",
                    titleHi = "भारतीय निर्यात सीमा शुल्क निकासी उत्तीर्ण",
                    titleAr = "تم فسح الجمارك الهندية للتصدير",
                    location = "Air Cargo Complex, Sahar, Mumbai",
                    hubCode = "BOM-ACC",
                    timestamp = "Oct 14, 04:15 PM",
                    descriptionEn = "Export declaration approved. EDI shipping bill generated.",
                    descriptionHi = "निर्यात घोषणा स्वीकृत। ईडीआई शिपिंग बिल तैयार।",
                    descriptionAr = "تم اعتماد إقرار التصدير وإصدار بوليصة الشحن الإلكترونية.",
                    isCompleted = true
                ),
                Milestone(
                    id = "m-3",
                    stage = ShipmentStatus.AIR_TRANSIT,
                    titleEn = "Departed on Kuwait Airways Cargo (KU384)",
                    titleHi = "कुवैत एयरवेज कार्गो (KU384) पर रवाना",
                    titleAr = "غادرت على متن الخطوط الجوية الكويتية للشحن",
                    location = "En Route (Arabian Sea Corridor)",
                    hubCode = "FLIGHT-KU384",
                    timestamp = "Oct 15, 01:20 AM",
                    descriptionEn = "Direct international air transit from BOM to KWI.",
                    descriptionHi = "मुंबई से कुवैत के लिए सीधी अंतरराष्ट्रीय हवाई यात्रा।",
                    descriptionAr = "رحلة شحن جوي دولية مباشرة من مطار مومباي إلى الكويت.",
                    isCompleted = true
                ),
                Milestone(
                    id = "m-4",
                    stage = ShipmentStatus.BAYAN_INSPECTION,
                    titleEn = "Kuwait Customs Bayan Inspection & 5% Duty Assessment",
                    titleHi = "कुवैत सीमा शुल्क बयान निरीक्षण और 5% शुल्क",
                    titleAr = "فحص الجمارك الكويتية (نظام بيان) واحتساب رسم ٥٪",
                    location = "KWI Air Cargo Customs Terminal, Kuwait",
                    hubCode = "KWI-BAYAN-TERM",
                    timestamp = "Oct 15, 08:45 AM",
                    descriptionEn = "General Administration of Customs processing Bayan declaration #KAC-BAYAN-2026-90412.",
                    descriptionHi = "सामान्य सीमा शुल्क प्रशासन बयान घोषणा पर कार्रवाई कर रहा है।",
                    descriptionAr = "الإدارة العامة للجمارك تقوم بمراجعة البيان الجمركي الإلكتروني واحتساب الرسم.",
                    isCompleted = false,
                    isCurrent = true
                ),
                Milestone(
                    id = "m-5",
                    stage = ShipmentStatus.DUTY_PAID,
                    titleEn = "Customs Duty Cleared & Released for Dispatch",
                    titleHi = "कस्टम ड्यूटी भुगतान पूर्ण एवं प्रेषण हेतु स्वीकृत",
                    titleAr = "تم دفع الرسوم الجمركية والإفراج عن الطرد للتوزيع",
                    location = "Shuwaikh Central Sorting Facility",
                    hubCode = "KWI-DC-SHUW",
                    timestamp = "Pending",
                    descriptionEn = "Bayan duty clearance voucher stamped for final transport.",
                    descriptionHi = "अंतिम परिवहन के लिए बयान शुल्क निकासी वाउचर मुहरबंद।",
                    descriptionAr = "جاهز للإفراج فور اعتماد إيصال سداد الرسوم الجمركية.",
                    isCompleted = false
                ),
                Milestone(
                    id = "m-6",
                    stage = ShipmentStatus.OUT_FOR_DELIVERY,
                    titleEn = "Out for Delivery to Consignee",
                    titleHi = "वितरण के लिए निकला",
                    titleAr = "خرج للتسليم للمستلم النهائي",
                    location = "Salmiya Delivery Zone",
                    hubCode = "KWI-LASTMILE-04",
                    timestamp = "Pending",
                    descriptionEn = "Assigned to Talabna courier agent for doorstep verification.",
                    descriptionHi = "घर-घर सत्यापन के लिए कूरियर एजेंट को सौंपा गया।",
                    descriptionAr = "بعهدة مندوب طلبنا إكس للتسليم المباشر والتحقق من الهوية.",
                    isCompleted = false
                ),
                Milestone(
                    id = "m-7",
                    stage = ShipmentStatus.DELIVERED,
                    titleEn = "Delivered & Proof-of-Delivery Archived",
                    titleHi = "वितरित और डिलीवरी का प्रमाण संग्रहीत",
                    titleAr = "تم التسليم وتوثيق التوقيع والصورة بنجاح",
                    location = "Salmiya, Block 4, Hawally",
                    hubCode = "DEST",
                    timestamp = "Pending",
                    descriptionEn = "Signed by recipient with photo proof and Civil ID confirmation.",
                    descriptionHi = "फोटो प्रमाण और सिविल आईडी पुष्टि के साथ प्राप्तकर्ता द्वारा हस्ताक्षरित।",
                    descriptionAr = "استلمها العميل مع حفظ التوقيع الحي وصورة الإثبات ورقم البطاقة المدنية.",
                    isCompleted = false
                )
            )
        ),
        Parcel(
            id = "p-2",
            trackingNumber = "TLX-4109-KWI",
            senderName = "Surat Heritage Silks & Textiles",
            senderCity = "Surat / Delhi Hub",
            senderCountry = "India",
            senderPort = "DEL (Indira Gandhi Intl Cargo)",
            recipientName = "Maha Al-Kandari",
            recipientPhone = "+965 6677 8899",
            recipientCity = "Kuwait City, Sharq District",
            recipientCountry = "Kuwait",
            destinationPort = "KWI (Kuwait International Airport)",
            weightKg = 6.8,
            dimensions = "45 x 35 x 20 cm",
            serviceType = "TalabnaX Express Saver",
            currentStatus = ShipmentStatus.OUT_FOR_DELIVERY,
            customsStatus = CustomsStatus.CLEARED,
            estimatedDelivery = "Today by 6:00 PM",
            customsDeclaration = CustomsDeclaration(
                declarationNo = "KAC-BAYAN-2026-78150",
                hsCode = "6204.42.00",
                categoryName = "Textiles, Silk Sarees & Garments",
                itemDescription = "Handcrafted Silk Garments and Festive Attire",
                quantity = 12,
                declaredValueInr = 84000.0,
                declaredValueKwd = 308.200,
                freightKwd = 18.000,
                insuranceKwd = 3.000,
                cifValueKwd = 329.200,
                dutyRatePercent = 5.0,
                customsDutyKwd = 16.460,
                bayanFeeKwd = 1.000,
                totalCustomsDueKwd = 17.460,
                status = CustomsStatus.CLEARED
            ),
            milestones = listOf(
                Milestone("m-21", ShipmentStatus.PICKED_UP, "Shipment Picked Up", "पिकअप संपन्न", "تم استلام الشحنة", "DEL Hub", "DEL-1", "Oct 13, 11:00 AM", "Export parcels consolidated.", "निर्यात पार्सल समेकित।", "تم تجميع الطرود للتصدير.", true),
                Milestone("m-22", ShipmentStatus.EXPORT_CUSTOMS, "Indian Customs Export Cleared", "सीमा शुल्क पास", "فسح جمارك الهند", "DEL Air Cargo", "DEL-ACC", "Oct 13, 08:30 PM", "Air Waybill issued.", "एयरवे बिल जारी।", "إصدار بوليصة الشحن.", true),
                Milestone("m-23", ShipmentStatus.AIR_TRANSIT, "Air Transit Kuwait Cargo", "हवाई पारगमन", "في طريق الشحن الجوي", "Transit", "KU-402", "Oct 14, 04:00 AM", "Arrived at KWI Cargo.", "कुवैत आगमन।", "وصل مطار الكويت.", true),
                Milestone("m-24", ShipmentStatus.BAYAN_INSPECTION, "Kuwait Bayan Clearance Completed", "बयान सीमा शुल्क पूर्ण", "إنهاء فحص بيان الجمركي", "KWI Terminal", "KWI-CUST", "Oct 14, 02:00 PM", "Physical inspection completed.", "भौतिक निरीक्षण पूरा।", "اكتملت المطابقة الجمركية.", true),
                Milestone("m-25", ShipmentStatus.DUTY_PAID, "Customs 5% Duty Settled", "शुल्क का भुगतान हुआ", "سداد الرسوم الجمركية", "Kuwait Customs", "KAC-PAY", "Oct 15, 09:00 AM", "Duty payment receipt verified.", "शुल्क रसीद सत्यापित।", "تم التحقق من سداد الرسوم ٥٪.", true),
                Milestone("m-26", ShipmentStatus.OUT_FOR_DELIVERY, "Out with Courier Agent (Ready for POD)", "कूरियर के पास (POD हेतु)", "خرج مع مندوب التوصيل للتسليم", "Sharq Delivery Route", "KWI-LASTMILE-01", "Today, 10:30 AM", "Courier is arriving at Sharq District.", "कूरियर शर्क जिले में पहुंच रहा है।", "المندوب في طريقه إلى شارع الخليج العربي - شرق.", false, isCurrent = true),
                Milestone("m-27", ShipmentStatus.DELIVERED, "Delivered to Recipient", "वितरित", "تم التسليم", "Sharq, Kuwait City", "DEST", "Pending", "Pending photo and recipient signature.", "फोटो और हस्ताक्षर प्रतीक्षित।", "بانتظار توقيع المستلم وصورة التسليم.", false)
            )
        )
    )

    private val _parcels = MutableStateFlow<List<Parcel>>(initialParcels)
    val parcels: StateFlow<List<Parcel>> = _parcels.asStateFlow()

    private val _selectedParcelId = MutableStateFlow<String>("p-1")
    val selectedParcelId: StateFlow<String> = _selectedParcelId.asStateFlow()

    private val _activeToast = MutableStateFlow<String?>(null)
    val activeToast: StateFlow<String?> = _activeToast.asStateFlow()

    fun selectParcel(id: String) {
        _selectedParcelId.value = id
    }

    fun dismissToast() {
        _activeToast.value = null
    }

    fun triggerToast(msg: String) {
        _activeToast.value = msg
    }

    fun calculateCustoms(
        category: CommodityCategory,
        declaredValueInr: Double,
        weightKg: Double
    ): CustomsDeclaration {
        val inrToKwdRate = 0.00367
        val declaredKwd = declaredValueInr * inrToKwdRate
        val freightInr = weightKg * category.defaultFreightPerKgInr
        val freightKwd = freightInr * inrToKwdRate
        val insuranceKwd = declaredKwd * 0.01
        val cifKwd = declaredKwd + freightKwd + insuranceKwd
        val dutyKwd = (cifKwd * (category.dutyRatePercent / 100.0))
        val bayanFeeKwd = 1.000
        val totalDueKwd = dutyKwd + bayanFeeKwd

        val randomBayanSuffix = (10000..99999).random()

        return CustomsDeclaration(
            declarationNo = "KAC-BAYAN-2026-$randomBayanSuffix",
            hsCode = category.hsCode,
            categoryName = category.nameEn,
            itemDescription = "Consignment cargo (${category.nameEn})",
            quantity = 1,
            declaredValueInr = declaredValueInr,
            declaredValueKwd = Math.round(declaredKwd * 1000.0) / 1000.0,
            freightKwd = Math.round(freightKwd * 1000.0) / 1000.0,
            insuranceKwd = Math.round(insuranceKwd * 1000.0) / 1000.0,
            cifValueKwd = Math.round(cifKwd * 1000.0) / 1000.0,
            dutyRatePercent = category.dutyRatePercent,
            customsDutyKwd = Math.round(dutyKwd * 1000.0) / 1000.0,
            bayanFeeKwd = bayanFeeKwd,
            totalCustomsDueKwd = Math.round(totalDueKwd * 1000.0) / 1000.0,
            status = CustomsStatus.DUTY_ASSESSED
        )
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
    ): Parcel {
        val awbNumber = "TLX-${(1000..9999).random()}-KWI"
        val id = "p-${UUID.randomUUID().toString().take(8)}"
        val now = SimpleDateFormat("MMM dd, hh:mm a", Locale.US).format(Date())

        val customs = calculateCustoms(category, declaredValueInr, weightKg)

        val milestones = listOf(
            Milestone(
                id = "m-${id}-1",
                stage = ShipmentStatus.PICKED_UP,
                titleEn = "Shipment Picked Up & Registered",
                titleHi = "पार्सल उठाया और पंजीकृत किया गया",
                titleAr = "تم استلام الشحنة وقيدها بالنظام",
                location = "$senderCity Hub",
                hubCode = "IND-EXP",
                timestamp = now,
                descriptionEn = "Consignment registered with commercial invoice for export to Kuwait.",
                descriptionHi = "कुवैत निर्यात के लिए वाणिज्यिक चालान के साथ खेप पंजीकृत।",
                descriptionAr = "تم قيد الشحنة مع الفاتورة التجارية للتصدير إلى الكويت.",
                isCompleted = true,
                isCurrent = true
            ),
            Milestone(
                id = "m-${id}-2",
                stage = ShipmentStatus.EXPORT_CUSTOMS,
                titleEn = "Indian Customs Clearance",
                titleHi = "भारतीय सीमा शुल्क निकासी",
                titleAr = "فسح الجمارك الهندية للتصدير",
                location = "Air Cargo Terminal",
                hubCode = "IND-ACC",
                timestamp = "Scheduled",
                descriptionEn = "Export clearance and security screening pending flight boarding.",
                descriptionHi = "उड़ान बोर्डिंग से पहले निर्यात निकासी और सुरक्षा जांच।",
                descriptionAr = "إجراءات التخليص الجمركي والتفتيش الأمني قبل الإقلاع.",
                isCompleted = false
            ),
            Milestone(
                id = "m-${id}-3",
                stage = ShipmentStatus.AIR_TRANSIT,
                titleEn = "Direct Air Transit to Kuwait",
                titleHi = "कुवैत के लिए सीधी हवाई यात्रा",
                titleAr = "الشحن الجوي المباشر إلى الكويت",
                location = "International Air Cargo",
                hubCode = "AIR-KWI",
                timestamp = "Pending",
                descriptionEn = "Scheduled for flight departure to Kuwait International Airport (KWI).",
                descriptionHi = "कुवैत अंतरराष्ट्रीय हवाई अड्डे के लिए प्रस्थान।",
                descriptionAr = "مجدولة للمغادرة إلى مطار الكويت الدولي (KWI).",
                isCompleted = false
            ),
            Milestone(
                id = "m-${id}-4",
                stage = ShipmentStatus.BAYAN_INSPECTION,
                titleEn = "Kuwait Bayan Customs 5% Assessment",
                titleHi = "कुवैत बयान 5% सीमा शुल्क मूल्यांकन",
                titleAr = "فحص الجمارك الكويتية (بيان) واحتساب رسم ٥٪",
                location = "KWI Air Cargo Customs Terminal",
                hubCode = "KWI-CUST",
                timestamp = "Pending",
                descriptionEn = "Kuwait Customs Authority electronic Bayan processing #${customs.declarationNo}.",
                descriptionHi = "कुवैत सीमा शुल्क इलेक्ट्रॉनिक बयान प्रसंस्करण।",
                descriptionAr = "مراجعة البيان الجمركي الإلكتروني واحتساب رسم ٥٪.",
                isCompleted = false
            ),
            Milestone(
                id = "m-${id}-5",
                stage = ShipmentStatus.DUTY_PAID,
                titleEn = "Customs Duty Cleared",
                titleHi = "सीमा शुल्क का भुगतान और निकासी",
                titleAr = "سداد الرسوم الجمركية والإفراج",
                location = "Kuwait Sorting Center",
                hubCode = "KWI-DC",
                timestamp = "Pending",
                descriptionEn = "Customs duties settled; cleared for local distribution.",
                descriptionHi = "कस्टम ड्यूटी का निपटान हुआ; स्थानीय वितरण के लिए स्वीकृत।",
                descriptionAr = "تم إنهاء سداد الرسوم الجمركية وجاهزة للتوزيع المحلي.",
                isCompleted = false
            ),
            Milestone(
                id = "m-${id}-6",
                stage = ShipmentStatus.OUT_FOR_DELIVERY,
                titleEn = "Out for Delivery (Kuwait)",
                titleHi = "वितरण के लिए निकला",
                titleAr = "خرج للتسليم في الكويت",
                location = recipientCity,
                hubCode = "KWI-LASTMILE",
                timestamp = "Pending",
                descriptionEn = "Dispatched with Talabna courier agent for doorstep verification.",
                descriptionHi = "घर-घर सत्यापन के लिए कूरियर एजेंट को सौंपा गया।",
                descriptionAr = "في طريقها مع مندوب التوصيل للاستلام النهائي والتوقيع.",
                isCompleted = false
            ),
            Milestone(
                id = "m-${id}-7",
                stage = ShipmentStatus.DELIVERED,
                titleEn = "Delivered & Signed with POD",
                titleHi = "वितरित एवं हस्ताक्षरित",
                titleAr = "تم التسليم وتوثيق التوقيع والصورة",
                location = recipientCity,
                hubCode = "DEST",
                timestamp = "Pending",
                descriptionEn = "Signed by recipient with photo proof and Civil ID confirmation.",
                descriptionHi = "फोटो प्रमाण और सिविल आईडी पुष्टि के साथ प्राप्तकर्ता द्वारा हस्ताक्षरित।",
                descriptionAr = "استلمها العميل مع حفظ التوقيع وصورة الإثبات.",
                isCompleted = false
            )
        )

        val newParcel = Parcel(
            id = id,
            trackingNumber = awbNumber,
            senderName = senderName,
            senderCity = senderCity,
            senderCountry = "India",
            senderPort = if (senderCity.contains("Mumbai", true)) "BOM (Mumbai)" else "DEL (Delhi)",
            recipientName = recipientName,
            recipientPhone = recipientPhone,
            recipientCity = recipientCity,
            recipientCountry = "Kuwait",
            destinationPort = "KWI (Kuwait International Airport)",
            weightKg = weightKg,
            dimensions = "35 x 25 x 20 cm",
            serviceType = "TalabnaX Priority Air Express",
            currentStatus = ShipmentStatus.PICKED_UP,
            customsStatus = CustomsStatus.IN_INSPECTION,
            estimatedDelivery = "In 3 Business Days",
            customsDeclaration = customs,
            milestones = milestones,
            proofOfDelivery = null
        )

        val updatedList = listOf(newParcel) + _parcels.value
        _parcels.value = updatedList
        _selectedParcelId.value = newParcel.id
        _activeToast.value = "📦 Registered new shipment $awbNumber ($senderCity ➔ $recipientCity)"
        return newParcel
    }

    fun advanceMilestone(parcelId: String): String {
        val currentList = _parcels.value
        val parcelIndex = currentList.indexOfFirst { it.id == parcelId }
        if (parcelIndex == -1) return "Parcel not found"

        val parcel = currentList[parcelIndex]
        val milestones = parcel.milestones.toMutableList()
        val currentActiveIndex = milestones.indexOfFirst { it.isCurrent }

        if (currentActiveIndex == -1 || currentActiveIndex >= milestones.size - 1) {
            val msg = "${parcel.trackingNumber}: Shipment is at final stage (${parcel.currentStatus.name})"
            _activeToast.value = msg
            return msg
        }

        milestones[currentActiveIndex] = milestones[currentActiveIndex].copy(
            isCompleted = true,
            isCurrent = false
        )

        val nextIndex = currentActiveIndex + 1
        val now = SimpleDateFormat("MMM dd, hh:mm a", Locale.US).format(Date())
        milestones[nextIndex] = milestones[nextIndex].copy(
            isCompleted = nextIndex == milestones.size - 1,
            isCurrent = true,
            timestamp = now
        )

        val newStatus = milestones[nextIndex].stage
        val newCustomsStatus = when (newStatus) {
            ShipmentStatus.BAYAN_INSPECTION -> CustomsStatus.IN_INSPECTION
            ShipmentStatus.DUTY_PAID, ShipmentStatus.OUT_FOR_DELIVERY, ShipmentStatus.DELIVERED -> CustomsStatus.CLEARED
            else -> parcel.customsStatus
        }

        val updatedParcel = parcel.copy(
            currentStatus = newStatus,
            customsStatus = newCustomsStatus,
            milestones = milestones
        )

        val updatedList = currentList.toMutableList()
        updatedList[parcelIndex] = updatedParcel
        _parcels.value = updatedList

        val toastMsg = "📍 ${parcel.trackingNumber}: Checkpoint scanned ➔ ${milestones[nextIndex].titleEn}"
        _activeToast.value = toastMsg
        return toastMsg
    }

    fun submitProofOfDelivery(
        parcelId: String,
        recipientName: String,
        civilId: String,
        signatureStrokes: List<List<Offset>>,
        photoNote: String,
        photoUri: String? = null
    ): Boolean {
        val currentList = _parcels.value
        val parcelIndex = currentList.indexOfFirst { it.id == parcelId }
        if (parcelIndex == -1) return false

        val parcel = currentList[parcelIndex]
        val now = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US).format(Date())

        val pod = ProofOfDelivery(
            recipientName = recipientName,
            civilIdOrAadhaar = civilId,
            deliveryTimestamp = "$now (Kuwait Local Time)",
            signatureStrokes = signatureStrokes,
            hasPhotoProof = true,
            photoProofNote = photoNote.ifBlank { "Doorstep photo verified at Kuwait delivery address." },
            photoUri = photoUri,
            courierId = "KWI-AGENT-408"
        )

        val updatedMilestones = parcel.milestones.map { m ->
            if (m.stage == ShipmentStatus.DELIVERED) {
                m.copy(
                    isCompleted = true,
                    isCurrent = true,
                    timestamp = "Just Now",
                    descriptionEn = "Successfully signed by $recipientName (Civil ID: $civilId). Proof photo verified.",
                    descriptionHi = "$recipientName द्वारा हस्ताक्षरित। प्रमाण फोटो सत्यापित।",
                    descriptionAr = "تم الاستلام بنجاح وتوقيع المستلم $recipientName (البطاقة المدنية: $civilId)."
                )
            } else {
                m.copy(isCompleted = true, isCurrent = false)
            }
        }

        val updatedParcel = parcel.copy(
            currentStatus = ShipmentStatus.DELIVERED,
            customsStatus = CustomsStatus.CLEARED,
            proofOfDelivery = pod,
            milestones = updatedMilestones
        )

        val updatedList = currentList.toMutableList()
        updatedList[parcelIndex] = updatedParcel
        _parcels.value = updatedList

        _activeToast.value = "✅ POD Confirmed for ${parcel.trackingNumber}! Delivered to $recipientName"
        return true
    }
}
