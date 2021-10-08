package com.maher.currencyconverter.data.network

import com.maher.currencyconverter.data.network.dto.ConvertCurrencyResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("v1/convert")
    suspend fun Compare(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
        @Query("api_key") apiKey: String
    ) : ConvertCurrencyResponseDto

}