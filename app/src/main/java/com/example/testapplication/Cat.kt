package com.example.testapplication

import com.google.gson.annotations.SerializedName

data class Cat(@SerializedName("file") val file: String) {
    public fun getFilePath(): String {
        return file
    }
}