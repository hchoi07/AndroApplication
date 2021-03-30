package com.example.testapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.Button
import com.example.testapplication.OnSwipeTouchListener
import com.example.testapplication.adapters.ButtonAdapter
import com.example.testapplication.R
import com.google.gson.Gson


class Recycler : Fragment() {

    var gson = Gson()
//    don't do this, it makes the recycler_view nullable:
//    var recycler_view : View? = null

    private lateinit var recycler_view : RecyclerView

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ButtonAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)

        recycler_view.setOnTouchListener(object : OnSwipeTouchListener(context)
        {
//            override fun onSwipeLeft() {
//                super.onSwipeLeft()
//                Toast.makeText(context, "Swipe Left gesture detected",
//                    Toast.LENGTH_SHORT)
//                    .show()
//            }
            override fun onSwipeRight() {
                super.onSwipeRight()
//                Toast.makeText(
//                    context,
//                    "Swipe Right gesture detected",
//                    Toast.LENGTH_SHORT
//                ).show()
                activity?.onBackPressed()
            }
//            override fun onSwipeUp() {
//                super.onSwipeUp()
//                Toast.makeText(context, "Swipe up gesture detected", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            override fun onSwipeDown() {
//                super.onSwipeDown()
//                Toast.makeText(context, "Swipe down gesture detected", Toast.LENGTH_SHORT)
//                    .show()
//            }
        })

        val assetManager = requireActivity().assets
        var jsonFileString: String = assetManager.open("button_data.json").bufferedReader().use { it.readText() }
//        Log.d("logging", "jsonFileString is : $jsonFileString")
        var button_list = gson.fromJson(jsonFileString, Button::class.java)
//        Log.d("logging", "###list of buttons : $button_list")

//        recycler_view.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = ButtonAdapter(button_list)
//            Log.d("logging", "adapter is: $adapter")
//        }
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = ButtonAdapter(button_list)

    }

}

