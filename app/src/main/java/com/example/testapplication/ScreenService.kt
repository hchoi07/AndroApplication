package com.example.testapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log


class ScreenService : Service() {

    private var sReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("logging", "onCreate: ScreenService.kt ")
    }

    override fun onStartCommand(intent: Intent?, flag: Int, startIs: Int): Int {

        // Detect screen off
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        var sReceiver = ScreenStateReceiver()
        registerReceiver(sReceiver, filter)
        return START_STICKY
    }

    override fun onDestroy() {
        if (sReceiver != null) unregisterReceiver(sReceiver)
        super.onDestroy()
    }

}