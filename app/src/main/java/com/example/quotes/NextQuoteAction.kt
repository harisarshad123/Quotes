package com.example.quotes

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.state.PreferencesGlanceStateDefinition

class NextQuoteAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val dataStore = PreferencesGlanceStateDefinition.getDataStore(context, glanceId.toString())

        dataStore.edit { prefs ->
            val current = prefs[PreferencesKeys.CurrentQuoteIndex] ?: 0
            prefs[PreferencesKeys.CurrentQuoteIndex] = current + 1
        }
        QuoteWidget().update(context, glanceId)
    }
}
