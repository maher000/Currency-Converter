package com.maher.currencyconverter.data

abstract class CurrencyException(message: String) : Exception(message) {
    class ApiInitializedException(message: String) : CurrencyException(message)
}