package com.maher.currencyconverter.data.network.dto

data class ConvertResponse(
    val meta: Meta,
    val response: CurrencyComparatorDto
)