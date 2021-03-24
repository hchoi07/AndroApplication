package com.example.testapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R

class TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private val code = arrayOf("10000",
        "10001", "10002", "5b87628",
        "db8d14e", "9913dc4", "e120f96",
        "466251b")

    private val colors = arrayOf("red", "blue",
        "yellow", "green",
        "orange", "purple",
        "navy", "white")

    private val chars = arrayOf("aaaaa",
        "bbbbb", "ccccc", "ddddd",
        "eeeee", "fffff", "ggggg",
        "hhhhh 21")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemCode: TextView
        var itemColors: TextView
        var itemChars: TextView

        init {
            itemCode = itemView.findViewById(R.id.kodePertanyaan)
            itemColors = itemView.findViewById(R.id.kategori)
            itemChars = itemView.findViewById(R.id.isiPertanyaan)

//            itemView.setOnClickListener {
//                var position: Int = getAdapterPosition()
//                val context = itemView.context
//                val intent = Intent(context, RecyclerTest::class.java).apply {
//                    putExtra("NUMBER", position)
//                    putExtra("CODE", itemCode.text)
//                    putExtra("CATEGORY", itemColors.text)
//                    putExtra("CONTENT", itemChars.text)
//                }
//                context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        Log.d("logging", "entering onCreateViewHolder on TEST")

        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemCode.text = code[i]
        viewHolder.itemColors.text = colors[i]
        viewHolder.itemChars.text = chars[i]

    }

    override fun getItemCount(): Int {
        return code.size
    }
}