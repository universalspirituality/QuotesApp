package com.universalspirituality.quotesfromswami

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

class QuoteWidgetProvider : AppWidgetProvider() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("QuoteWidgetProvider", "onUpdate called")

        // Get a random quote from the database
        val databaseHelper = DatabaseHelper(context)
        val quote = databaseHelper.getRandomQuote()

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            views.setTextViewText(R.id.quote_text_view, quote)

            // Set the image for the ImageView
            val imageId = R.drawable.swami001
            views.setImageViewResource(R.id.image_view, imageId)

            // update the widget with the current id
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "quote_widget_prefs"
        private const val INTERVAL_PREF_KEY = "update_interval"

        @RequiresApi(Build.VERSION_CODES.S)
        fun updateWidget(context: Context) {
            Log.d("QuoteWidgetProvider", "updateWidget called")

            val widgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = widgetManager.getAppWidgetIds(ComponentName(context, QuoteWidgetProvider::class.java))

            // Get the interval from shared preferences
            val prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val interval = prefs.getLong(INTERVAL_PREF_KEY, 0).toInt()
            Log.d("QuoteWidgetProvider", "interval: $interval")

            // Update the widget with the new quote
            for (widgetId in widgetIds) {
                Log.d("QuoteWidgetProvider", "widgetId: $widgetId")

                val views = RemoteViews(context.packageName, R.layout.widget_layout)
                val databaseHelper = DatabaseHelper(context)
                val quote = databaseHelper.getRandomQuote()

                views.setTextViewText(R.id.quote_text_view, quote)

                // Set the image for the ImageView
                val imageId = R.drawable.swami001
                views.setImageViewResource(R.id.image_view, imageId)
                val requestCode = widgetId + (Math.random() * 100).toInt()

                // Set up the intent for the FullscreenQuoteActivity
                val fullscreenIntent = Intent(context, FullscreenQuoteActivity::class.java)
                fullscreenIntent.putExtra(FullscreenQuoteActivity.EXTRA_QUOTE_TEXT, quote)
                val pendingIntent = PendingIntent.getActivity(context, requestCode, fullscreenIntent, PendingIntent.FLAG_MUTABLE)
                views.setOnClickPendingIntent(R.id.quote_text_view, pendingIntent)


                // update the widget with the current id
                widgetManager.updateAppWidget(widgetId, views)
            }
        }

    }


}
