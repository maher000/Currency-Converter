package com.maher.currencyconverter.presentation.compareTwoCurrencies

internal sealed class CompareTwoCurrenciesState {
    class Success(val result: String) : CompareTwoCurrenciesState()
    class Error(val error: String) : CompareTwoCurrenciesState()
    object Loading : CompareTwoCurrenciesState()
    object Default: CompareTwoCurrenciesState()
}