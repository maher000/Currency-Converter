package com.maher.currencyconverter.presentation.compareTwoCurrencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maher.currencyconverter.common.DispatcherProvider
import com.maher.currencyconverter.common.Resource
import com.maher.currencyconverter.domain.model.CurrencySymbol
import com.maher.currencyconverter.domain.useCases.CompareTwoCurrencies
import com.maher.currencyconverter.presentation.model.ValidInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CompareTwoCurrenciesViewModel @Inject constructor(
    private val mCompareTwoCurrencies: CompareTwoCurrencies,
    private val mDispatchers: DispatcherProvider
) : ViewModel() {

    private val mCurrencyComparison =
        MutableStateFlow<CompareTwoCurrenciesState>(CompareTwoCurrenciesState.Default)
    val currencyComparison: StateFlow<CompareTwoCurrenciesState> = mCurrencyComparison

    fun compare(
        fromCurrency: String,
        toCurrency: String,
        amount: String
    ) {
        val isValidInput = validateInput(fromCurrency, toCurrency, amount)
        if (!isValidInput.isValid) {
            mCurrencyComparison.value = CompareTwoCurrenciesState.Error(isValidInput.reason)
            return
        }

        viewModelScope.launch(mDispatchers.io) {
            with(requireNotNull(isValidInput.data)) {
                mCompareTwoCurrencies(first, second, third).collect { result ->
                    when (result) {
                        is Resource.Loading -> mCurrencyComparison.value =
                            CompareTwoCurrenciesState.Loading
                        is Resource.Success -> result.data?.let {
                            mCurrencyComparison.value =
                                CompareTwoCurrenciesState.Success(it.value.toString())
                        } ?: run {
                            mCurrencyComparison.value =
                                CompareTwoCurrenciesState.Error("An unexpected Error occurred")
                        }
                        is Resource.Error -> CompareTwoCurrenciesState.Error(
                            result.message ?: "An unexpected Error occurred"
                        )
                    }
                }
            }

        }
    }

    private fun validateInput(
        fromCurrency: String,
        toCurrency: String,
        amount: String
    ): ValidInput<Triple<CurrencySymbol, CurrencySymbol, Double>> {
        val isValidInput = ValidInput<Triple<CurrencySymbol, CurrencySymbol, Double>>()

        val validAmount = amount.toDoubleOrNull() ?: return isValidInput.apply {
            reason = "Amount is not a valid number"
            isValid = false
        }
        val validFromCurrency =
            CurrencySymbol.fromString(fromCurrency) ?: return isValidInput.apply {
                reason = "From Currency name is not valid"
                isValid = false
            }
        val validToCurrency = CurrencySymbol.fromString(toCurrency) ?: return isValidInput.apply {
            reason = "To Currency name is not valid"
            isValid = false
        }

        return isValidInput.apply {
            data = Triple(validFromCurrency, validToCurrency, validAmount)
        }
    }
}