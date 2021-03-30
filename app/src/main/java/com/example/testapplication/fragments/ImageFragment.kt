package com.example.testapplication.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testapplication.MainActivity
import com.example.testapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class ImageFragment : Fragment(), View.OnClickListener{


    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    private lateinit var photoFile: File
    private val FILE_NAME = "photo.jpg"
    var frgTakenImage : Bitmap? = null
//    var matrix: Matrix? = null
//    var takenImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        frgTakenImage = (activity as MainActivity).takenImage
        Log.d("logging", "frgTakenImage is : $frgTakenImage")



        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        view.findViewById<FloatingActionButton>(R.id.camera_btn).setOnClickListener(this)

        loadImage(imageView)
    }

    private fun loadImage(imageView: ImageView) {
        if (frgTakenImage == null) {
            val media = "android.resource://com.example.testapplication/drawable/trees"
            Glide.with(this).load(media).into(imageView)
        } else {

            var rotatedImage = rotateImage(frgTakenImage!!, 90f)
            Glide.with(this).load(rotatedImage).into(imageView)
        }
//        imageView.setImageBitmap(frgTakenImage)


        (activity as MainActivity).takenImage = frgTakenImage

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.camera_btn -> dispatchTakePictureIntent()

        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent("android.media.action.IMAGE_CAPTURE").also {
            photoFile = createImageFile(FILE_NAME)
        }
        val fileProvider = context?.let { FileProvider.getUriForFile(
            it,
            "com.example.testapplication.fileprovider",
            photoFile
        ) }
        photoFile.also {
    //            val photoURI = context?.let { FileProvider.getUriForFile(
    //                it,
    //                "com.example.testapplication.fileprovider",
    //                photoFile
    //            ) }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    //            activity?.setResult(Activity.RESULT_OK, takePictureIntent)
        }
        Log.d("logging", "photoFile is : $photoFile")
        Log.d("logging", "takePictureIntent is : $takePictureIntent")
    }

    private fun createImageFile(fileName: String): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDirectory = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDirectory).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            val uri = Uri.fromFile(output)
//            val takenImage = data?.extras?.get("data") as Bitmap
            frgTakenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            Log.d("logging", "takenImage's absolute path is : ${photoFile.absolutePath}")
            Log.d("logging", "takenImage is : $frgTakenImage")

            loadImage(imageView)

//            imageView.setImageBitmap(takenImage)
//            notifyMediaStoreScanner(photoFile)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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
            Log.d(
                "logging", "notifyMediaStoreScanner: Intent is: ${
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(
                            file
                        )
                    )
                }"
            )

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

}