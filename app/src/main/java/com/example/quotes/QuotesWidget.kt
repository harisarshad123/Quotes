package com.example.quotes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import java.util.Calendar

class QuoteWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Check if we need to update the quote (new day)
        checkAndUpdateQuote(context, id)

        provideContent {
            QuoteContent()
        }
    }

    private suspend fun checkAndUpdateQuote(context: Context, glanceId: GlanceId) {
        val dataStore = PreferencesGlanceStateDefinition.getDataStore(context, glanceId.toString())

        dataStore.edit { prefs ->
            val lastUpdated = prefs[PreferencesKeys.LastUpdatedTimestamp] ?: 0L
            val currentTime = System.currentTimeMillis()

            if (isDifferentDay(lastUpdated, currentTime)) {
                val current = prefs[PreferencesKeys.CurrentQuoteIndex] ?: 0
                prefs[PreferencesKeys.CurrentQuoteIndex] = current + 1
                prefs[PreferencesKeys.LastUpdatedTimestamp] = currentTime
            } else if (lastUpdated == 0L) {
                prefs[PreferencesKeys.LastUpdatedTimestamp] = currentTime
            }
        }
    }

    private fun isDifferentDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }

        return cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) ||
                cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)
    }

    @Composable
    private fun QuoteContent() {
        val quotes = listOf(
            "Do one thing every day that scares you.",
            "Believe you can and you're halfway there.",
            "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            "Education is the most powerful weapon you can use to change the world.",
            "The only limit to our realization of tomorrow is our doubts of today."
        )

        val prefs = currentState<Preferences>()
        val index = prefs[PreferencesKeys.CurrentQuoteIndex] ?: 0
        val quote = quotes[index % quotes.size]

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.White).cornerRadius(10.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Quote of the day",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                modifier = GlanceModifier.padding(bottom = 5.dp)
            )
            Text(
                text = quote,
                style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center)
            )
        }
    }
}
