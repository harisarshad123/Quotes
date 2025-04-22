package com.example.quotes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

class QuoteWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            QuoteContent()
        }
    }

    @Composable
    private fun QuoteContent() {
        val quotes = listOf(
            "Believe you can and you're halfway there.",
            "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            "Do one thing every day that scares you.",
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
                .clickable(actionRunCallback<NextQuoteAction>()).background(color = Color.White),
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
