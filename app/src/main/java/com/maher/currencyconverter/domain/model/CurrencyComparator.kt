package com.maher.currencyconverter.domain.model

import java.util.Date

data class CurrencyComparator(
    val fromCurrency: CurrencySymbol,
    val toCurrency: CurrencySymbol,
    val amount: Double,
    val value: Double,
    val date: Date,
)
