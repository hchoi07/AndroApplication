package com.example.testapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.fragment.app.FragmentTransaction

/**
 * Implementation of App Widget functionality.
 */
class TestAppExpandedWidget : AppWidgetProvider() {

    private val MyOnClick1 = "myOnClickTag1"
    private val MyOnClick2 = "myOnClickTag2"
    private val MyOnClick3 = "myOnClickTag3"

    protected fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, TestAppExpandedWidget::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

//            views.setTextViewText(R.id.appwidget_text, widgetText)

            //val intent = Intent(context, MainActivity::class.java)
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)

            //val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val views = RemoteViews(context.packageName, R.layout.test_app_widget)

            views.setOnClickPendingIntent(R.id.button, getPendingSelfIntent(context, MyOnClick1))
            views.setOnClickPendingIntent(R.id.button2, getPendingSelfIntent(context, MyOnClick2))
            views.setOnClickPendingIntent(R.id.button3, getPendingSelfIntent(context, MyOnClick3))


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        if (MyOnClick1.equals(intent.action)) {
            //your onClick action is here
            Log.i("Widget$$", "Clicked button1");
            Log.i("Widget$$", "button 1's intent.action is: " + intent.action);
            Log.i("Widget$$", "button 1's intent is: " + intent);
            Log.i("Widget$$", "button 1's context is: " + context);
            context?.startActivity(intent)



        }
        if (MyOnClick2.equals(intent.action)) {
            //your onClick action is here
            Log.i("Widget$$", "Clicked button2");
        }
        if (MyOnClick3.equals(intent.action)) {
            //your onClick action is here
            Log.i("Widget$$", "Clicked button3");
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
