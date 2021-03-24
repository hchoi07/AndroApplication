package com.example.testapplication.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testapplication.BuildConfig
import com.example.testapplication.R
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), View.OnClickListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var navController: NavController? = null
    lateinit var currentPhotoPath: String
    private lateinit var photoFile: File
    private val FILE_NAME = "photo.jpg"
    private val AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"
//    private val output: File? = null


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
        view.findViewById<Button>(R.id.api_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.recyclerView_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.camera_btn).setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.image_btn -> navController!!.navigate(R.id.action_mainFragment_to_imageFragment)
            R.id.video_btn -> navController!!.navigate(R.id.action_mainFragment_to_videoFragment)
            //R.id.otherApp_btn -> navController!!.navigate(R.id.action_mainFragment_to_openMap)
            R.id.otherApp_btn -> openMap()
            R.id.api_btn -> navController!!.navigate(R.id.action_mainFragment_to_apiFragment)
            R.id.recyclerView_btn -> navController!!.navigate(R.id.action_mainFragment_to_recycler)
            R.id.camera_btn -> dispatchTakePictureIntent()
        }
    }

    private fun openMap(){
        Log.i("logging", "Clicked map button")
        val gmmIntentUri = Uri.parse("https://www.google.com/maps/place/Bellevue+Botanical+Garden/@47.609198,-122.1809505,17z/data=!3m1!4b1!4m5!3m4!1s0x54906c66ffcf2bb7:0xf3a991ee744c3f03!8m2!3d47.6091944!4d-122.1787565")

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

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent("android.media.action.IMAGE_CAPTURE").also {
            photoFile = createImageFile()
        }
       photoFile?.also {
           val photoURI = context?.let { FileProvider.getUriForFile(
               it,
               "com.example.testapplication.fileprovider",
               photoFile
           ) }
           takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
           startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
           activity?.setResult(RESULT_OK, takePictureIntent)
       }
        Log.d("logging", "photoFile is : $photoFile")
        Log.d("logging", "takePictureIntent is : $takePictureIntent")
    }
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDirectory: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDirectory).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("logging", "intent is : $data")
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val uri = Uri.fromFile(output)

            notifyMediaStoreScanner(photoFile)
        }
//            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//            val photoIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//            val f = File(photoFile.absolutePath)
//            val contentUri = Uri.fromFile(f)
//            photoIntent.data = contentUri
//            context?.sendBroadcast(photoIntent)
            // to render this "takenImage" into a specific image view:
            // imageview.setImageBitmap(takenImage)
//        }
    }

//    private fun galleryAddPic() {
//        val f = File(currentPhotoPath) //set your picture's path
//        try {
//            MediaStore.Images.Media.insertImage(
//                activity?.contentResolver,
//                f.absolutePath, f.name, null
//            )
//            activity?.sendBroadcast(
//                Intent(
//                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)
//                )
//            )
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//    }
    private fun galleryAddPic(){
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            activity?.sendBroadcast(mediaScanIntent)
        }
    }
    fun notifyMediaStoreScanner(file: File) {
        Log.d("logging", "notifyMediaStoreScanner: photoFile is: $file")

        try {
            MediaStore.Images.Media.insertImage(
                context?.getContentResolver(),
                file.absolutePath, file.name, null
            )
            context?.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)
                )
            )
            Log.d("logging", "notifyMediaStoreScanner: Intent is: ${Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file))}")

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }


}