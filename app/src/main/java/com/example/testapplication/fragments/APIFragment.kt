package com.example.testapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.testapplication.Cat
import com.example.testapplication.CatInterface
import com.example.testapplication.R
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIFragment : Fragment(), View.OnClickListener {

    var requestQueue: RequestQueue? = null
    val baseUrl: String = "https://aws.random.cat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.refresh_btn).setOnClickListener(this)
//        makeRequest()
        getMethod()

    }

    override fun onClick(v: View?) {
//        navController!!.navigate(R.id.action_apiFragment_self)
        getMethod()
    }

    private fun makeRequest() {
        val imageView = view?.findViewById<ImageView>(R.id.apiView)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, baseUrl, null,
            { response ->
                try {
                    val imageResponse = response.getString("file")
                    Log.d("logging", "random cat's response is: $imageResponse")
                    Glide.with(this).load(imageResponse).into(imageView!!)


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )
        requestQueue?.add(jsonObjectRequest)
    }

    private fun makeRequest2(input: String) {
        val imageView = view?.findViewById<ImageView>(R.id.apiView)

        Glide.with(this).load(input).into(imageView!!)
    }

    fun getMethod() {
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(CatInterface::class.java)

            // Do the GET request and get response
        val response = service.getCats()
        response?.enqueue(object: Callback<Cat?> {
            override fun onResponse(call: Call<Cat?>, response: Response<Cat?>) {
                Log.d("logging", "response is : ${response.body()}")
                response.body()?.let { makeRequest2(it.file) }
            }
            override fun onFailure(call: Call<Cat?>, t: Throwable) {
                Log.d("logging", "failed!!")
            }
        })
    }

}