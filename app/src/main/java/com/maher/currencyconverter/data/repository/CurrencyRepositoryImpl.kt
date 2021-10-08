package com.maher.currencyconverter.data.repository

import com.maher.currencyconverter.data.network.CurrencyApi
import com.maher.currencyconverter.data.network.CurrencyApiInitializer.API_KEY
import com.maher.currencyconverter.data.network.dto.ConvertCurrencyResponseDto
import com.maher.currencyconverter.domain.model.CurrencySymbol
import com.maher.currencyconverter.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(private val mCurrencyApi: CurrencyApi) : CurrencyRepository {

    override suspend fun compare(
        from: CurrencySymbol,
        to: CurrencySymbol,
        amount: Double
    ): ConvertCurrencyResponseDto = mCurrencyApi.Compare(from.name, to.name, amount,API_KEY!!)
}