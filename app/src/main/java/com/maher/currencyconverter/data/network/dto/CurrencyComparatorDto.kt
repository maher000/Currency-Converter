package com.maher.currencyconverter.data.network.dto

data class CurrencyComparatorDto(
    val from: String,
    val to: String,
    val amount: Double,
    val value: Double,
    val timestamp: Long
)
