package com.walids.mdns

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent

class MyAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val editTextIntent = Intent(context, MyAppWidgetProvider::class.java).apply {
                action = "com.example.EDITTEXT_CLICK"
            }

            // Create PendingIntent to trigger on button click
            val editTextPendingIntent = PendingIntent.getBroadcast(
                context, appWidgetId, editTextIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setOnClickPendingIntent(R.id.widget_text, editTextPendingIntent)
            }

            // Update the widget with the new RemoteViews
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == "com.example.EDITTEXT_CLICK") {
            // Extract the text from the Intent
            val widgetText = intent.getStringExtra("widgetText") ?: ""

            // Prepare RemoteViews and update the widget
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setTextViewText(R.id.widget_text, widgetText)
            }

            // Update all instances of the widget
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetComponent = ComponentName(context, MyAppWidgetProvider::class.java)
            appWidgetManager.updateAppWidget(widgetComponent, remoteViews)
        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }
}
