package com.walids.mdns

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editText:EditText = findViewById(R.id.editTextText)
        val button:Button = findViewById(R.id.button)

        button.setOnClickListener {
            val text = editText.text.toString()
            // Prepare the intent to update the widget
            val intent = Intent(this, MyAppWidgetProvider::class.java).apply {
                action = "com.example.EDITTEXT_CLICK"
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
                putExtra("widgetText", text)
            }
            // Send the broadcast to the widget provider
            sendBroadcast(intent)
            addWidgetToHomeScreen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addWidgetToHomeScreen() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val myProvider = ComponentName(this, MyAppWidgetProvider::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            // Request to pin the widget
            appWidgetManager.requestPinAppWidget(myProvider, null, null)
        } else {
            // Handle the case where pinning widgets is not supported
            // For example, show a message to the user
        }
    }
}