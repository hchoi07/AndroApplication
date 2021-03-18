package com.example.testapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import android.content.Context
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import androidx.navigation.NavController
import androidx.navigation.Navigation



class apiFragment : Fragment(), View.OnClickListener {

    var requestQueue: RequestQueue? = null
    val url: String = "https://aws.random.cat/meow"
//    var navController: NavController? = null


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
        makeRequest()

    }

    override fun onClick(v: View?) {
//        navController!!.navigate(R.id.action_apiFragment_self)
        makeRequest()
    }

    private fun makeRequest() {
        val imageView = view?.findViewById<ImageView>(R.id.apiView)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val imageResponse = response.getString("file")
                    Log.d("logging", "random cat's response is: $imageResponse")
                    Glide.with(this).load(imageResponse).into(imageView!!)


                } catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        requestQueue?.add(jsonObjectRequest)
    }



}