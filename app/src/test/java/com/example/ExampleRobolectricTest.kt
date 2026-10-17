package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.ParcelRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches Talabna X`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Talabna X", appName)
    }

    @Test
    fun `test customs duty calculation 5 percent`() {
        val repo = ParcelRepository()
        val electronics = repo.categories.first { it.hsCode == "8517.13.00" }
        // 100,000 INR declared value
        val declaration = repo.calculateCustoms(electronics, 100000.0, 5.0)
        assertNotNull(declaration)
        assertEquals(5.0, declaration.dutyRatePercent, 0.001)
        assertTrue(declaration.cifValueKwd > 0)
        assertTrue(declaration.customsDutyKwd > 0)
        assertTrue(declaration.totalCustomsDueKwd > declaration.customsDutyKwd)
    }

    @Test
    fun `test initial parcels list not empty`() {
        val repo = ParcelRepository()
        val list = repo.parcels.value
        assertTrue(list.isNotEmpty())
        assertTrue(list.any { it.senderCountry == "India" && it.recipientCountry == "Kuwait" })
    }
}
