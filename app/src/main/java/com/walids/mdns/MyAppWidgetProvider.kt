package com.walids.mdns

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class MyAppWidgetProvider:AppWidgetProvider() {
    companion object { lateinit var text:String }
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
            if (appWidgetIds != null && appWidgetIds.isNotEmpty()) {
                // This is the ID of the newly created widget
                updateWidget(context, appWidgetIds[0])
            }
        }
        if (intent.action == "GETEDITTEXT") {
            val edittext = intent.getStringExtra("EditText text")
            text = edittext.toString()
        }
    }

    private fun updateWidget(context: Context, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        views.setTextViewText(R.id.widget_text, text)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}