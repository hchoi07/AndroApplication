package com.example.testapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ButtonAdapter(private val button_list: Button): RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    init {
        Log.d("logging", "entering ButtonAdapter.kt ")
    }

    val button_list_data = button_list
    val list = ArrayList<RecyclerItems>(button_list_data.recycler_items)

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemtitle : TextView
        var itembutton : android.widget.Button

        init {
            itemtitle = view.findViewById(R.id.btn_title)
            itembutton = view.findViewById(R.id.display_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("logging", "entering onCreateViewHolder")

        val view = LayoutInflater.from(parent.context).inflate(R.layout.btn_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("logging", "entering onBindViewHolder")
        holder.itemtitle.text = list[position].TITLE
        holder.itembutton.setOnClickListener { v ->
            Toast.makeText(
                v.context,
                "position = $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}