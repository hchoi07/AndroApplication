package com.example.testapplication

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface CatInterface {
    @GET("meow")
    fun getCats() : Call<Cat>?
}