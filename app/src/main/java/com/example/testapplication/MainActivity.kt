package com.example.testapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
//    private var mReceiver: BroadcastReceiver? = null

    public var takenImage : Bitmap? = null
    private lateinit var layout: ConstraintLayout

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.main_layout)

        /*
        Global swipe gesture implementation; however, this doesn't get applied to
        Video or RecyclerView fragment and their views need to be individually set with
        the OnSwipeTouchListener object in order to implement swipe on them. Why????
        TODO: Find out why
         */

        layout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            //            override fun onSwipeLeft() {
//                super.onSwipeLeft()
//                Toast.makeText(this@MainActivity, "Swipe Left gesture detected",
//                    Toast.LENGTH_SHORT)
//                    .show()
//            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                Toast.makeText(
                    this@MainActivity,
                    "Swipe Right gesture detected",
                    Toast.LENGTH_SHORT
                ).show()
                onBackPressed()
            }
        })

        startService(Intent(this, ScreenService::class.java))
//        Log.d("logging", "from MainActivity: takenImage is $takenImage")


    }

}