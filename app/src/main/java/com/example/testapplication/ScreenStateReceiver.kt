package com.example.testapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class ScreenStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_SCREEN_ON == action) {
            Log.d("logging", "The screen is on.")
        } else if (Intent.ACTION_SCREEN_OFF == action) {
            Log.d("logging", "The screen is off.")
        }
    }
}
