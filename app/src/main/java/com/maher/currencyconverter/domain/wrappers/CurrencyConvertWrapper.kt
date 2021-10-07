package com.maher.currencyconverter.domain.wrappers

import com.maher.currencyconverter.data.network.dto.CurrencyComparatorDto
import com.maher.currencyconverter.domain.model.CurrencyComparator
import com.maher.currencyconverter.domain.model.CurrencySymbol
import java.util.Date

fun CurrencyComparatorDto.toCurrencyComparator() : CurrencyComparator = CurrencyComparator(
    fromCurrency = CurrencySymbol.valueOf(from),
    toCurrency = CurrencySymbol.valueOf(to),
    amount = amount,
    value = value,
    date = Date(timestamp)
)

