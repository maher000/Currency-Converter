package com.maher.currencyconverter.data.network

import com.maher.currencyconverter.data.CurrencyException.*

object CurrencyApiInitializer {
    private var isInitialized = false
    internal var API_KEY: String? = null
        get() = requireNotNull(field, { "Missing API KEY for Currency APi" })
        private set

    fun initialize(apiKey: String) {
        if (isInitialized) {
            throw ApiInitializedException(
                "Currency Api is already initialized.\nDid you try to initialize it manually " +
                        "without disabling ApiInitializer from manifest?"
            )
        }
        API_KEY = apiKey
        isInitialized = true
    }
}