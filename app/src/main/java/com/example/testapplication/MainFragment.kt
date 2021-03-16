package com.example.testapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation


class MainFragment : Fragment(), View.OnClickListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    // using the navigation library automatically handles the backstack
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.image_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.video_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.otherApp_btn).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.image_btn -> navController!!.navigate(R.id.action_mainFragment_to_imageFragment)
            R.id.video_btn -> navController!!.navigate(R.id.action_mainFragment_to_videoFragment)
            //R.id.otherApp_btn -> navController!!.navigate(R.id.action_mainFragment_to_openMap)
            R.id.otherApp_btn -> openMap()
        }
    }

    public fun openMap(){
        val gmmIntentUri = Uri.parse("geo:47.61787174268605, -122.15306954678934")

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps")
        val packageManager = activity?.packageManager
        if (packageManager != null) {
            mapIntent.resolveActivity(packageManager).let {
                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent)
            }
        }

    }

    public fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }


}