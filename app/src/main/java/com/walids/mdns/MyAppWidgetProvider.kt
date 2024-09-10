package com.walids.mdns

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.startActivity

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
        if (intent.action == "OPENBROWSER") {
            val chooserIntent = Intent.createChooser(intent, "Open with")
            startActivity(context, chooserIntent, null)
        }
    }

    private fun updateWidget(context: Context, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        views.setTextViewText(R.id.widget_text, text)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        // Loop over all widget instances (in case there are multiple)
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Intent to open the link
            val intent = Intent(Intent.ACTION_VIEW).apply {
                action = "OPENBROWSER"
                data = Uri.parse(text)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            // Create a PendingIntent to be triggered when the TextView is clicked
            val pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_IMMUTABLE)

            // Set the PendingIntent to the TextView in the widget
            views.setOnClickPendingIntent(R.id.widget_text, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}