package com.example.testapplication.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.testapplication.R
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class ImageFragment : Fragment(), View.OnClickListener{


    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    private lateinit var photoFile: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        view.findViewById<Button>(R.id.camera_btn).setOnClickListener(this)



        val media = "android.resource://com.example.testapplication/drawable/trees"
        Glide.with(this).load(media).into(imageView)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.camera_btn -> dispatchTakePictureIntent()

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
            activity?.setResult(Activity.RESULT_OK, takePictureIntent)
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
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