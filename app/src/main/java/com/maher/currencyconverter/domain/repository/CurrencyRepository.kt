package com.maher.currencyconverter.domain.repository

import com.maher.currencyconverter.data.network.dto.ConvertCurrencyResponseDto
import com.maher.currencyconverter.domain.model.CurrencySymbol

interface CurrencyRepository {

    suspend fun compare(
        from: CurrencySymbol,
        to: CurrencySymbol,
        amount: Double
    ): ConvertCurrencyResponseDto
}