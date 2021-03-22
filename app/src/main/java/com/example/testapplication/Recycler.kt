package com.example.testapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_recycler.*



class Recycler : Fragment() {

    var gson = Gson()
//    don't do this, it makes the recycler_view nullable
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)
        val assetManager = requireActivity().assets
        var jsonFileString: String = assetManager.open("button_data.json").bufferedReader().use { it.readText() }
//        Log.d("logging", "jsonFileString is : $jsonFileString")
        var button_list = gson.fromJson(jsonFileString, Button::class.java)
        Log.d("logging", "###list of buttons : $button_list")

//        recycler_view.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = ButtonAdapter(button_list)
//            Log.d("logging", "adapter is: $adapter")
//        }
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = ButtonAdapter(button_list)

    }

}

