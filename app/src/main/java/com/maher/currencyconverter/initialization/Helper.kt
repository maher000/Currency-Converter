package com.maher.currencyconverter.presentation

import android.os.Bundle

internal fun getMetaData(metadata: Bundle, key: String): String {
    val value = metadata.getString(key)
    require(!value.isNullOrBlank()) { computeMissingManifestEntryMessage(key) }
    return value
}

internal fun computeMissingManifestEntryMessage(entity: String) = "The manifest doesn't " +
        "contain any $entity metadata entity!"
