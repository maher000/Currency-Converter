package com.maher.currencyconverter.presentation.model

data class ValidInput<T>(
    var data: T? = null,
    var isValid: Boolean = true,
    var reason: String = ""
)
