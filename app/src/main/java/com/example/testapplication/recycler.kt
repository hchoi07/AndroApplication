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


class recycler : Fragment() {

    var gson = Gson()
    var recycler_view : View? = null

    private var linearLayoutManager: RecyclerView.LayoutManager? = null
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
        recycler_view = view?.findViewById<RecyclerView>(R.id.recycler_view)
        val assetManager = requireActivity().assets
        var jsonFileString: String = assetManager.open("button_data.json").bufferedReader().use { it.readText() }
        Log.d("logging", "jsonFileString is : $jsonFileString")
        var button_list = gson.fromJson(jsonFileString, Button::class.java)
        Log.d("logging", "###list of buttons : $button_list")

        recycler_view.apply {
            linearLayoutManager = LinearLayoutManager(activity)
            adapter = ButtonAdapter(button_list)
            Log.d("logging", "adapter is: $adapter")
        }

    }

}

