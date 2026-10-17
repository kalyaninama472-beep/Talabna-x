package com.example.data.localization

import com.example.data.model.AppLanguage

object AppStrings {
    fun get(key: String, language: AppLanguage): String {
        return strings[key]?.get(language) ?: strings[key]?.get(AppLanguage.ENGLISH) ?: key
    }

    private val strings: Map<String, Map<AppLanguage, String>> = mapOf(
        "app_title" to mapOf(
            AppLanguage.ENGLISH to "TALABNA X",
            AppLanguage.HINDI to "तालाबना X",
            AppLanguage.ARABIC to "طلبنا X"
        ),
        "app_tagline" to mapOf(
            AppLanguage.ENGLISH to "India ➔ Kuwait Cross-Border Express Logistics",
            AppLanguage.HINDI to "भारत ➔ कुवैत सीमा-पार एक्सप्रेस लॉजिस्टिक्स",
            AppLanguage.ARABIC to "الخدمات اللوجستية السريعة عبر الحدود من الهند إلى الكويت"
        ),
        "customer_mode" to mapOf(
            AppLanguage.ENGLISH to "Customer Track",
            AppLanguage.HINDI to "ग्राहक ट्रैकिंग",
            AppLanguage.ARABIC to "تتبع الشحنة"
        ),
        "courier_mode" to mapOf(
            AppLanguage.ENGLISH to "Courier Dispatch (POD)",
            AppLanguage.HINDI to "कूरियर एजेंट (POD)",
            AppLanguage.ARABIC to "بوابة المندوب (إثبات التسليم)"
        ),
        "customs_calculator_tab" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Customs & Tax",
            AppLanguage.HINDI to "कुवैत सीमा शुल्क और कर",
            AppLanguage.ARABIC to "جمارك وضرائب الكويت"
        ),
        "search_hint" to mapOf(
            AppLanguage.ENGLISH to "Enter AWB / Tracking (e.g. TLX-8842-KWI)",
            AppLanguage.HINDI to "ट्रैकिंग नंबर दर्ज करें (उदा. TLX-8842-KWI)",
            AppLanguage.ARABIC to "أدخل رقم الشحنة أو البوليصة (مثل TLX-8842-KWI)"
        ),
        "quick_parcels" to mapOf(
            AppLanguage.ENGLISH to "Active Shipments",
            AppLanguage.HINDI to "सक्रिय पार्सल",
            AppLanguage.ARABIC to "الشحنات المسجلة"
        ),
        "origin_hub" to mapOf(
            AppLanguage.ENGLISH to "Origin Port",
            AppLanguage.HINDI to "उत्पत्ति बंदरगाह",
            AppLanguage.ARABIC to "ميناء المغادرة"
        ),
        "dest_hub" to mapOf(
            AppLanguage.ENGLISH to "Destination Port",
            AppLanguage.HINDI to "गंतव्य बंदरगाह",
            AppLanguage.ARABIC to "ميناء الوصول"
        ),
        "status_picked_up" to mapOf(
            AppLanguage.ENGLISH to "Picked Up in India",
            AppLanguage.HINDI to "भारत में पिकअप किया गया",
            AppLanguage.ARABIC to "تم الاستلام في الهند"
        ),
        "status_export_customs" to mapOf(
            AppLanguage.ENGLISH to "Export Customs Cleared",
            AppLanguage.HINDI to "निर्यात सीमा शुल्क पास",
            AppLanguage.ARABIC to "تم فسح جمارك التصدير"
        ),
        "status_air_transit" to mapOf(
            AppLanguage.ENGLISH to "Air Cargo Transit",
            AppLanguage.HINDI to "हवाई पारगमन में",
            AppLanguage.ARABIC to "الشحن الجوي في الطريق"
        ),
        "status_bayan_inspection" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Bayan Customs Inspection",
            AppLanguage.HINDI to "कुवैत बयान सीमा शुल्क निरीक्षण",
            AppLanguage.ARABIC to "تفتيش بيان الجمركي بالكويت"
        ),
        "status_duty_paid" to mapOf(
            AppLanguage.ENGLISH to "5% Bayan Duty Assessed & Cleared",
            AppLanguage.HINDI to "5% सीमा शुल्क मूल्यांकन और भुगतान",
            AppLanguage.ARABIC to "تم دفع رسوم البيان ٥٪ والتخليص"
        ),
        "status_out_for_delivery" to mapOf(
            AppLanguage.ENGLISH to "Out for Delivery (Kuwait)",
            AppLanguage.HINDI to "वितरण के लिए निकला (कुवैत)",
            AppLanguage.ARABIC to "خرج للتسليم في الكويت"
        ),
        "status_delivered" to mapOf(
            AppLanguage.ENGLISH to "Delivered with POD",
            AppLanguage.HINDI to "सफलतापूर्वक वितरित (POD सहित)",
            AppLanguage.ARABIC to "تم التسليم بنجاح مع إثبات الاستلام"
        ),
        "scan_checkpoint" to mapOf(
            AppLanguage.ENGLISH to "Scan & Advance Checkpoint",
            AppLanguage.HINDI to "चेकपॉइंट स्कैन करें और आगे बढ़ाएं",
            AppLanguage.ARABIC to "مسح وتحديث نقطة التفتيش التالية"
        ),
        "book_shipment" to mapOf(
            AppLanguage.ENGLISH to "Register New Shipment",
            AppLanguage.HINDI to "नया शिपमेंट पंजीकृत करें",
            AppLanguage.ARABIC to "تسجيل شحنة جديدة"
        ),
        "view_bayan_doc" to mapOf(
            AppLanguage.ENGLISH to "Inspect Bayan Customs Doc",
            AppLanguage.HINDI to "बयान कस्टम्स दस्तावेज़ देखें",
            AppLanguage.ARABIC to "معاينة بيان الجمارك الرسمي"
        ),
        "view_pod_receipt" to mapOf(
            AppLanguage.ENGLISH to "View Proof of Delivery (POD)",
            AppLanguage.HINDI to "डिलीवरी का प्रमाण (POD) देखें",
            AppLanguage.ARABIC to "عرض إثبات التسليم المعتمد"
        ),
        "customs_breakdown_title" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Customs Authority (Bayan) Duty",
            AppLanguage.HINDI to "कुवैत सीमा शुल्क प्राधिकरण (बयान) शुल्क",
            AppLanguage.ARABIC to "رسوم الإدارة العامة للجمارك (بيان جمركي)"
        ),
        "declared_value" to mapOf(
            AppLanguage.ENGLISH to "Declared Value",
            AppLanguage.HINDI to "घोषित मूल्य",
            AppLanguage.ARABIC to "القيمة المصرح بها"
        ),
        "cif_value" to mapOf(
            AppLanguage.ENGLISH to "CIF Total (Cost, Ins, Freight)",
            AppLanguage.HINDI to "सीआईएफ कुल (लागत, बीमा, माल भाड़ा)",
            AppLanguage.ARABIC to "إجمالي سيف (القيمة + التأمين + الشحن)"
        ),
        "duty_rate" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Standard Duty (5%)",
            AppLanguage.HINDI to "कुवैत मानक शुल्क (5%)",
            AppLanguage.ARABIC to "التعريفة الجمركية الكويتية الموحدة (٥٪)"
        ),
        "bayan_fee" to mapOf(
            AppLanguage.ENGLISH to "Bayan Electronic Fee",
            AppLanguage.HINDI to "बयान इलेक्ट्रॉनिक शुल्क",
            AppLanguage.ARABIC to "رسم البيان الإلكتروني"
        ),
        "total_payable" to mapOf(
            AppLanguage.ENGLISH to "Total Customs Due",
            AppLanguage.HINDI to "कुल देय सीमा शुल्क",
            AppLanguage.ARABIC to "إجمالي الرسوم المستحقة"
        ),
        "hs_code_label" to mapOf(
            AppLanguage.ENGLISH to "Harmonized System (HS) Code",
            AppLanguage.HINDI to "एचएस टैरिफ कोड",
            AppLanguage.ARABIC to "رمز النظام المنسق (HS Code)"
        ),
        // Courier strings
        "courier_header" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Dispatch Manifest",
            AppLanguage.HINDI to "कुवैत प्रेषण सूची",
            AppLanguage.ARABIC to "بيان التوزيع الميداني بالكويت"
        ),
        "ready_for_pod" to mapOf(
            AppLanguage.ENGLISH to "Collect POD (Photo & Signature)",
            AppLanguage.HINDI to "पीओडी एकत्र करें (फोटो और हस्ताक्षर)",
            AppLanguage.ARABIC to "تسليم وأخذ إثبات التوقيع والصورة"
        ),
        "pod_dialog_title" to mapOf(
            AppLanguage.ENGLISH to "Capture Proof of Delivery",
            AppLanguage.HINDI to "डिलीवरी का प्रमाण दर्ज करें",
            AppLanguage.ARABIC to "تسجيل إثبات التسليم الرقمي"
        ),
        "recipient_name" to mapOf(
            AppLanguage.ENGLISH to "Recipient Name",
            AppLanguage.HINDI to "प्राप्तकर्ता का नाम",
            AppLanguage.ARABIC to "اسم المستلم"
        ),
        "civil_id_number" to mapOf(
            AppLanguage.ENGLISH to "Kuwait Civil ID / ID Ref",
            AppLanguage.HINDI to "कुवैत सिविल आईडी / आईडी संदर्भ",
            AppLanguage.ARABIC to "الرقم المدني الكويتي / إثبات الهوية"
        ),
        "signature_canvas_label" to mapOf(
            AppLanguage.ENGLISH to "Touch Signature Confirmation",
            AppLanguage.HINDI to "डिजिटल हस्ताक्षर पुष्टि",
            AppLanguage.ARABIC to "توقيع المستلم على الشاشة"
        ),
        "clear_signature" to mapOf(
            AppLanguage.ENGLISH to "Clear",
            AppLanguage.HINDI to "साफ़ करें",
            AppLanguage.ARABIC to "مسح"
        ),
        "take_photo_proof" to mapOf(
            AppLanguage.ENGLISH to "Attach / Take Delivery Photo",
            AppLanguage.HINDI to "डिलीवरी फोटो संलग्न करें",
            AppLanguage.ARABIC to "التقاط أو إرفاق صورة التسليم"
        ),
        "photo_captured_ok" to mapOf(
            AppLanguage.ENGLISH to "Photo Proof Attached",
            AppLanguage.HINDI to "फोटो प्रमाण संलग्न",
            AppLanguage.ARABIC to "تم إرفاق صورة التسليم"
        ),
        "confirm_delivery_btn" to mapOf(
            AppLanguage.ENGLISH to "Complete Delivery & Sign POD",
            AppLanguage.HINDI to "डिलीवरी पूर्ण करें और POD सहेजें",
            AppLanguage.ARABIC to "تأكيد التسليم واعتماد التوقيع"
        ),
        "delivery_success_toast" to mapOf(
            AppLanguage.ENGLISH to "Delivery completed! Proof of delivery archived.",
            AppLanguage.HINDI to "डिलीवरी पूरी हो गई! डिलीवरी का प्रमाण संग्रहीत।",
            AppLanguage.ARABIC to "تم التسليم بنجاح! تم أرشفة إثبات التسليم والتوقيع."
        ),
        // Calculator strings
        "calc_title" to mapOf(
            AppLanguage.ENGLISH to "India ➔ Kuwait Duty Calculator",
            AppLanguage.HINDI to "भारत ➔ कुवैत सीमा शुल्क कैलकुलेटर",
            AppLanguage.ARABIC to "حاسبة الرسوم الجمركية: الهند ➔ الكويت"
        ),
        "select_category" to mapOf(
            AppLanguage.ENGLISH to "Commodity Category",
            AppLanguage.HINDI to "वस्तु श्रेणी",
            AppLanguage.ARABIC to "تصنيف البضاعة"
        ),
        "invoice_value" to mapOf(
            AppLanguage.ENGLISH to "Commercial Invoice Value (INR ₹)",
            AppLanguage.HINDI to "वाणिज्यिक चालान मूल्य (INR ₹)",
            AppLanguage.ARABIC to "قيمة الفاتورة التجارية (روبية هندية ₹)"
        ),
        "package_weight" to mapOf(
            AppLanguage.ENGLISH to "Weight (kg)",
            AppLanguage.HINDI to "वजन (किलोग्राम)",
            AppLanguage.ARABIC to "الوزن الإجمالي (كجم)"
        ),
        "generate_customs_bayan" to mapOf(
            AppLanguage.ENGLISH to "Register Shipment & Generate Bayan",
            AppLanguage.HINDI to "शिपमेंट पंजीकृत करें और बयान बनाएं",
            AppLanguage.ARABIC to "تسجيل الشحنة وإصدار البيان الجمركي"
        ),
        "close" to mapOf(
            AppLanguage.ENGLISH to "Close",
            AppLanguage.HINDI to "बंद करें",
            AppLanguage.ARABIC to "إغلاق"
        )
    )
}
