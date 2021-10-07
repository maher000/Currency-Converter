package com.maher.currencyconverter.domain.repository

import com.maher.currencyconverter.common.Resource
import com.maher.currencyconverter.data.network.dto.ConvertResponse
import com.maher.currencyconverter.data.network.dto.CurrencyComparatorDto
import com.maher.currencyconverter.domain.model.CurrencySymbol
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun compare(
        from: CurrencySymbol,
        to: CurrencySymbol,
        amount: Double
    ): ConvertResponse
}