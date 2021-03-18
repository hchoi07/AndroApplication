package com.example.testapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder


/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each App Widget that belongs to this provider
        appWidgetIds.forEach { appWidgetId ->

            // this launches the main activity:
            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            // this launches the image fragment
//            val pendingIntent2 = NavDeepLinkBuilder(context)
//                .setComponentName(MainActivity::class.java)
//                .setGraph(R.navigation.nav_graph)
//                .setDestination(R.id.imageFragment)
//                .createPendingIntent()

            // this launches the video fragment
//            val pendingIntent3 = NavDeepLinkBuilder(context)
//                .setComponentName(MainActivity::class.java)
//                .setGraph(R.navigation.nav_graph)
//                .setDestination(R.id.videoFragment)
//                .createPendingIntent()



            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.app_widget
            ).apply {
                setOnClickPendingIntent(R.id.launch_btn, pendingIntent)
//                setOnClickPendingIntent(R.id.open_image_btn, pendingIntent2)
//                setOnClickPendingIntent(R.id.open_video_btn, pendingIntent3)
//                setOnClickPendingIntent(R.id.other_app_btn, pendingIntent4)

            }

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
