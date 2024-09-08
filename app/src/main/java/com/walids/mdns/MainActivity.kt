package com.walids.mdns

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button:Button = findViewById(R.id.button)
        button.setOnClickListener { addWidgetToHomeScreen() }
    }

    private fun addWidgetToHomeScreen() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val myProvider = ComponentName(this, MyAppWidgetProvider::class.java)
        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            // Request to pin the widget
            appWidgetManager.requestPinAppWidget(myProvider, null, null)
        } else {
            // TODO:Handle the case where pinning widgets is not supported, For example, show a message to the user
        }
    }
}