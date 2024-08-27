package com.example.projectskripsi_kasir

import android.annotation.SuppressLint
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.widget.Button
import android.widget.ImageView
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BarcodeActivity : AppCompatActivity() {

    private lateinit var barcode : ImageView
    private lateinit var buttondownload : Button

    private lateinit var back: MaterialToolbar

    private val STORAGE_PERMISSION_CODE = 1

    var userid = ""
    var username = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        barcode = findViewById(R.id.barcode)
        back = findViewById(R.id.topbar)
        buttondownload = findViewById(R.id.download)

        userid = intent.getStringExtra("id").toString()
        username = intent.getStringExtra("username").toString()

        val qrCodeUrl = "https://barcodeapi.org/api/qr/$userid"

        back.setOnClickListener {
            finish()
        }

        buttondownload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("BarcodeActivity", "Permission granted, downloading barcode")
                downloadbarcode(qrCodeUrl)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                Log.d("BarcodeActivity", "Permission not granted, requesting permission")
            }
        }

        Glide.with(this)
            .load(qrCodeUrl)
            .into(barcode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadbarcode(qrCodeUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(qrCodeUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveImageToGallery(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState == Environment.MEDIA_MOUNTED) {
            val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            val myDir = File("$root/UserApps")
            if (!myDir.exists()) {
                myDir.mkdirs()
            }
            val fileName = "Barcode_$username.jpg"
            val file = File(myDir, fileName)
            if (file.exists()) file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        else {
        }
    }
}

