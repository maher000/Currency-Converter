package com.maher.currencyconverter.data.network.dto

data class ConvertCurrencyResponseDto(
    val meta: CurrencyMetaDeto,
    val response: CurrencyComparatorDto
)