package com.example.quotes

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class QuoteWidgetReceiver :
    GlanceAppWidgetReceiver() {
    override val glanceAppWidget = QuoteWidget()
}