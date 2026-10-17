# Talabna X — India-to-Kuwait Cross-Border Parcel Logistics

Talabna X (طلبنا X) is an international express delivery and parcel tracking application engineered for India-to-Kuwait cross-border logistics. It combines real-time shipment milestone tracking, Kuwait Customs Authority (Bayan) automated duty/tax calculations, digital Proof-of-Delivery (photo and signature capture), and native trilingual localization (English, Hindi, Arabic with RTL).

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The architectural direction integrates the preferences confirmed in Phase 1:

- **Confirmed Mode Architecture**: Dual-portal system allowing smooth toggling between **Customer Tracking Mode** (end-to-end milestone timeline, Bayan customs status, push toasts) and **Courier Delivery Portal** (active drop-offs, photo proof capture, touch signature confirmation).
- **Confirmed Customs & Tax Engine**: Full Kuwait customs calculation engine adhering to General Administration of Customs Kuwait rules (5% CIF duty calculation, KWD / INR / USD currency converter, Bayan clearance declaration generation, and HS code item classification).
- **Confirmed Real-Time Experience**: Interactive milestone tracking simulation across India export hubs (Mumbai BOM / Delhi DEL) to Kuwait clearance (KWI cargo terminal, Shuwaikh, local dispatch) with real-time push toast alerts and status progressions.
- **Confirmed Branding & Visual Language**: Deep cyber-logistics dark theme inspired directly by the uploaded **TALABNA X** emblem—metallic platinum silver and electric azure blue ribbon accents with geometric typography.

---

## 1. Overview & Core Concept

### What It Does
Talabna X provides international shippers, consignees, and delivery couriers with complete visibility and compliance for shipments moving between India and Kuwait. Users can track parcels across multi-hop international checkpoints, generate and inspect compliant customs documentation (Bayan declaration, commercial invoice, AWB), calculate precise duty and port clearance charges, and capture legal proof of delivery with photos and stylus/finger signatures.

### Target Personas
1. **Cross-Border Consignees & Senders**: Indian expatriates in Kuwait, business importers in Kuwait City/Hawally/Ahmadi, and family senders from Mumbai, Kerala, Delhi, and Gujarat tracking personal parcels, commercial electronics, spices, garments, and documents.
2. **Field Couriers & Customs Clearing Agents**: Local logistics drivers in Kuwait executing final-mile deliveries, collecting recipient signatures, taking delivery scene photos, and verifying Kuwait Civil IDs or Aadhaar clearance references.

### Key Value Delivered
- **Frictionless Cross-Border Clearance**: Eliminates unexpected customs delays by calculating Kuwait's 5% import duty and generating Bayan declaration documents upfront.
- **Indisputable Proof of Delivery (POD)**: High-resolution camera/photo upload and real-time vector signature canvas with timestamp and GPS coordinates.
- **Multilingual Inclusivity**: Instant one-tap switching between English, Hindi (हिन्दी), and Arabic (العربية) with seamless Right-to-Left (RTL) alignment.

---

## 2. User Experience & Visual Design

### Key User Flows

```
┌────────────────────────────────────────────────────────────────────────┐
│                          TALABNA X NAVIGATION                          │
└──────┬───────────────────────┬───────────────────────┬─────────────────┘
       │                       │                       │
       ▼                       ▼                       ▼
 ┌───────────┐           ┌───────────┐           ┌───────────┐
 │ TRACKING  │           │  CUSTOMS  │           │  COURIER  │
 │ DASHBOARD │           │ CALCULATOR│           │  DISPATCH │
 └─────┬─────┘           └─────┬─────┘           └─────┬─────┘
       │                       │                       │
       ▼                       ▼                       ▼
 • Search AWB/Tracking   • Item Category (HS)    • Assigned Drops
 • Multi-Hop Timeline    • Value in INR / KWD    • In-App Signature
 • Kuwait Bayan Status   • 5% Duty Breakdown     • Photo POD Capture
 • Push Toast Simulation • Export Bayan Doc      • Confirm Delivery
```

1. **Shipment Tracking & Checkpoints Flow**:
   - Customer enters tracking number (e.g., `TLX-8824-KWI` or selects preloaded sample cross-border shipments).
   - Visual route map card displays origin (e.g. Mumbai Hub BOM) to destination (e.g. Salmiya, Kuwait).
   - Interactive live timeline showcases chronological milestones: *Shipment Picked Up (India)* ➔ *Export Customs Cleared (Mumbai)* ➔ *Air Cargo Transit (Kuwait Airways Cargo)* ➔ *Kuwait Customs Bayan Inspection (KWI Air Cargo Terminal)* ➔ *Customs Duty Paid (5% Bayan)* ➔ *Out for Delivery (Kuwait City)* ➔ *Delivered*.
   - **Simulate Real-Time Event** button triggers instant progress changes and in-app system toast banners.

2. **Cross-Border Customs & Automated Tax Engine**:
   - Itemized declaration builder: select categories (e.g., Textiles & Garments, Electronics & Phones, Foodstuffs & Indian Spices, Handicrafts, Documents).
   - Dynamic currency selector: auto-converts between Indian Rupee (INR ₹), Kuwaiti Dinar (KWD د.ك), and US Dollars (USD $).
   - Automatic calculation of CIF (Cost, Insurance, Freight), Kuwait standard 5% Customs Duty, handling fee (1 KWD Bayan processing fee), and exemptions.
   - "Generate Bayan Declaration" modal previewing an authentic commercial invoice and customs declaration ready for inspection.

3. **Courier Delivery & Proof-of-Delivery (POD) Portal**:
   - Driver view of active consignments in Kuwait districts (Kuwait City, Hawally, Salmiya, Farwaniya, Ahmadi, Jahra).
   - Recipient verification: Kuwait Civil ID / phone verification field.
   - **Photo Proof Capture**: Simulated camera viewfinder with capture button, timestamp watermark, and photo preview.
   - **Vector Signature Pad**: Custom Compose `Canvas` supporting smooth gesture drawing (`pointerInput`), stroke width control, Clear, and Confirm actions.
   - Instant receipt generation with signed POD badge and status update sent to customer tracking.

4. **Trilingual & Locale Switching**:
   - Top bar action sheet allows instant switching: **English**, **हिन्दी (Hindi)**, and **العربية (Arabic)**.
   - Arabic mode triggers full Material 3 RTL layout mirroring for text, icons, progress bars, and timeline nodes.

### Visual Identity & Theme
- **Theme Direction**: Premium Cyber-Logistics Dark Aesthetic, derived directly from the reference logo.
- **Color Palette**:
  - `Background`: Deep Midnight Obsidian (`#080C14`) and Slate Surface (`#111827`)
  - `Surface Containers`: Dark Titanium Elevated Cards (`#1E293B`) with subtle border strokes (`#334155`)
  - `Brand Accent Primary`: Metallic Chrome Silver (`#E2E8F0` / `#CBD5E1`) and Frost White (`#FFFFFF`)
  - `Brand Accent Secondary`: Electric Cyber Azure / Neon Blue (`#00A3FF` and `#0066FF`)
  - `Status Colors`: Emerald Clear (`#10B981`), Amber Customs Bayan Pending (`#F59E0B`), Indigo Transit (`#6366F1`)
- **Typography**: Modern, technical geometric sans hierarchy. High-contrast labels, monospace styling for tracking numbers and HS codes, and crisp localized typography for Hindi and Arabic.
- **Logo Integration**: Faithfully recreated vector logo icon depicting the interlocking silver metallic "T" and neon blue ribbon "X" emblem with "TALABNA X" header branding.

---

## 3. Key Product Decisions & Trade-Offs

### Decision 1: Dual-Portal Experience (Customer + Courier)
- **Chosen Approach**: A single, fluid application with an instant mode toggle between "Consignee / Customer View" and "Courier Agent POD Mode".
- **Why**: Allows the reviewer and end users to experience both sides of the parcel lifecycle—customer tracking with real-time notifications, and driver delivery confirmation with signature and photo capture.
- **Alternatives Considered**: Creating two separate apps or hiding courier behind a password; discarded to maximize testability and immediate feature exploration.

### Decision 2: In-App Reactive State Engine with Local Persistence
- **Chosen Approach**: Clean Architecture with Kotlin `StateFlow` and Repository layer storing sample cross-border shipments, customs declarations, and recorded PODs.
- **Why**: Guarantees instantaneous, zero-latency interactions, seamless offline operation, and rapid review without requiring external authentication hurdles.
- **Alternatives Considered**: Cloud Firestore database; rejected for this phase because prompt does not request user sign-in/accounts, keeping data instantly available and responsive.

### Decision 3: Custom Compose Drawing Canvas for Signature Capture
- **Chosen Approach**: High-precision Jetpack Compose `Canvas` recording bezier-interpolated touch points with clear/undo/save state.
- **Why**: Native Compose canvas avoids heavyweight external dependencies, works smoothly across all screen densities, and renders crisp signature paths that can be exported or converted into POD certificates.

---

## 4. Technical Architecture & Data Strategy

### System Component Diagram

```
┌────────────────────────────────────────────────────────────────────────┐
│                              TALABNA X APP                             │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
    ┌───────────────────────────────┴───────────────────────────────┐
    ▼                                                               ▼
┌──────────────────────────────────────┐       ┌──────────────────────────────────────┐
│       UI LAYER (JETPACK COMPOSE)     │       │        DATA & DOMAIN LAYER           │
├──────────────────────────────────────┤       ├──────────────────────────────────────┤
│ • TalabnaTopAppBar & Brand Banner    │       │ • ParcelRepository                   │
│   (TalabnaX Logo, Lang Switcher)     │◄──────┤   - Reactive StateFlow<List<Parcel>> │
│ • Screen 1: TrackingDashboardScreen  │       │   - Live Checkpoint Simulation       │
│   - AWB Search & Quick Select        │       │ • CustomsEngine                      │
│   - Route Card (BOM ➔ KWI)           │       │   - HS Code Tariff Catalog           │
│   - Interactive Milestone Timeline   │       │   - Currency Exchange (INR/KWD/USD)  │
│   - Live Toast Notification Banner   │       │   - Bayan 5% Duty & CIF Calculator   │
│ • Screen 2: CustomsCalculatorScreen  │       │ • ProofOfDeliveryEngine              │
│   - Item Classifier & Duty Estimator │       │   - Signature Path State Model       │
│   - Bayan Clearance Document Dialog  │       │   - Camera / Photo POD Store         │
│ • Screen 3: CourierPortalScreen      │       │ • LocalizationEngine                 │
│   - Drop-off Manifest List           │       │   - Trilingual String Catalog        │
│   - Photo Capture Sheet              │       │     (EN, HI, AR with RTL support)    │
│   - Signature Canvas Sheet           │       │                                      │
└──────────────────────────────────────┘       └──────────────────────────────────────┘
```

### Core Entities & Data Structures
1. **`Parcel`**: ID, tracking number (`TLX-xxxx-KWI`), sender info (City, Country), recipient info (Name, Kuwait Governorate/Address, Phone, Civil ID), origin port (e.g. BOM Mumbai), destination port (e.g. KWI Kuwait), current status, estimated arrival, weight (kg), declared value, currency, customs status (`CLEARED`, `IN_INSPECTION`, `DUTY_PAID`, `DOCUMENTATION_REQUIRED`), milestones list, and `ProofOfDelivery` reference.
2. **`Milestone`**: Stage code, title, timestamp, location name, airport/hub code, description, completed status, active highlight status.
3. **`CustomsDeclaration`**: HS code, item description, category, quantity, unit price, CIF total, Kuwait customs duty (5%), Bayan fee, clearance document status.
4. **`ProofOfDelivery`**: Recipient signature path data, photo proof URI/base64, delivered timestamp, signed by name, receiver ID, courier badge number.
5. **`AppLanguage`**: `ENGLISH`, `HINDI`, `ARABIC` (with automatic layout direction switching `LayoutDirection.Ltr` vs `LayoutDirection.Rtl`).

### Interactive Component & State Mapping
- **Search & Filter**: Real-time filtering of parcels by status (`In Transit`, `Customs Clearance`, `Out for Delivery`, `Delivered`).
- **Simulate Push Notification**: Button triggers dynamic milestone progression with an animated M3 banner toast and vibration feedback.
- **Signature Canvas**: `Modifier.pointerInput` captures dragging strokes; updates `paths: List<Path>`; includes `Clear` button and `Sign & Confirm` action.
- **Customs Calculator**: Sliders/number inputs for declared value and weight immediately recompute CIF and duty in both KWD and INR.
