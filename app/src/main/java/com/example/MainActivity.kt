package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.CourierPortalScreen
import com.example.ui.screens.CustomsCalculatorScreen
import com.example.ui.screens.TrackingDashboardScreen
import com.example.ui.theme.TalabnaDarkBg
import com.example.ui.theme.TalabnaXTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.ParcelViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TalabnaXTheme {
                val viewModel: ParcelViewModel = viewModel()
                val currentTab by viewModel.currentTab.collectAsState()
                val language by viewModel.language.collectAsState()
                val activeToast by viewModel.activeToast.collectAsState()

                val bayanParcel by viewModel.bayanDialogParcel.collectAsState()
                val podParcel by viewModel.podReceiptParcel.collectAsState()
                val courierPodParcel by viewModel.courierPodParcel.collectAsState()
                val showNewShipment by viewModel.showNewShipmentDialog.collectAsState()

                // Support RTL dynamically for Arabic (العربية)
                val layoutDirection = if (language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(TalabnaDarkBg),
                        contentWindowInsets = WindowInsets.safeDrawing,
                        topBar = {
                            Column {
                                TalabnaHeader(viewModel = viewModel)
                                NotificationBanner(
                                    message = activeToast,
                                    onDismiss = { viewModel.dismissToast() }
                                )
                            }
                        }
                    ) { innerPadding ->
                        // BackHandler: return to Tracking Dashboard if in secondary tabs
                        if (currentTab != AppTab.TRACKING) {
                            BackHandler {
                                viewModel.setTab(AppTab.TRACKING)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(TalabnaDarkBg)
                        ) {
                            when (currentTab) {
                                AppTab.TRACKING -> {
                                    TrackingDashboardScreen(viewModel = viewModel)
                                }
                                AppTab.CUSTOMS_CALC -> {
                                    CustomsCalculatorScreen(viewModel = viewModel)
                                }
                                AppTab.COURIER_POD -> {
                                    CourierPortalScreen(viewModel = viewModel)
                                }
                            }

                            // Modals
                            bayanParcel?.let { parcel ->
                                BayanDocumentDialog(
                                    parcel = parcel,
                                    onDismiss = { viewModel.closeBayanDialog() }
                                )
                            }

                            podParcel?.let { parcel ->
                                PodReceiptDialog(
                                    parcel = parcel,
                                    onDismiss = { viewModel.closePodReceipt() }
                                )
                            }

                            courierPodParcel?.let { parcel ->
                                CourierPodSheet(
                                    parcel = parcel,
                                    viewModel = viewModel,
                                    onDismiss = { viewModel.closeCourierPod() }
                                )
                            }

                            if (showNewShipment) {
                                NewShipmentDialog(
                                    viewModel = viewModel,
                                    onDismiss = { viewModel.closeNewShipmentDialog() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
