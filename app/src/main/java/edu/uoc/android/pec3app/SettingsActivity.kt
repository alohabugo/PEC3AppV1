package edu.uoc.android.pec3app

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment
import java.io.File
import java.io.IOException


class SettingsActivity : AppCompatActivity() {

    //handle capture request Intent
    val REQUEST_IMAGE_CAPTURE = 1
    var currentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        this.btnCamera.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                dispatchTakePictureIntent()
            }
        })

    }

    private fun dispatchTakePictureIntent() {
    // fun that invokes an Intent to capture a photo
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    //get captured image and display in imageview
    private fun showCapturedImage(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.extras != null) {
                val imageBitmap = data.extras!!.get("data") as Bitmap
                this.imageCamera.setImageBitmap(imageBitmap)
                this.textNoImg.visibility = View.INVISIBLE
            } else {
                this.textNoImg.text = "There is no image!"
                this.textNoImg.visibility = View.VISIBLE
            }
        }
    }

    /*
    @Throws(IOException::class)
    private fun storeImage() : File {
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile("imageapp","jpg",storageDir)
        currentPhotoPath = image.getAbsolutePath()
        return image
    }
    */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        showCapturedImage(requestCode,resultCode,data)
    }

}
