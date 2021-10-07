package com.maher.currencyconverter.initialization

import android.content.Context
import android.content.pm.PackageManager
import androidx.startup.Initializer
import com.maher.currencyconverter.data.network.CurrencyApiInitializer
import com.maher.currencyconverter.presentation.getMetaData

@Suppress("unused")
internal class ApiInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val metadata = with(context) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            ).metaData
        }

        val apiKey = getMetaData(metadata, META_API_KEY)
        CurrencyApiInitializer.initialize(apiKey)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }

    companion object{
        private const val META_API_KEY = "currencyApiKey"
    }
}
