package com.maher.currencyconverter.domain

abstract class CurrencyException(message: String) : Exception(message) {
    class ApiInitializedException(message: String) : CurrencyException(message)
}