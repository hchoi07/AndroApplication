package com.example.testapplication

import com.google.gson.annotations.SerializedName


data class Button (@SerializedName("RECYCLER_ITEMS") val recycler_items: ArrayList<RecyclerItems>)

data class RecyclerItems(
//    @SerializedName("TITLE") val title: String,
//    @SerializedName("BUTTON_TEXT") val button_text: String,
//    @SerializedName("DESCRIPTION") val description: String)

    var TITLE: String,
    var BUTTON_TEXT: String,
    var DESCRIPTION: String)

