package com.example.quotes

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object PreferencesKeys {
    val CurrentQuoteIndex = intPreferencesKey("quote_index")
    val LastUpdatedTimestamp = longPreferencesKey("last_updated")
}
