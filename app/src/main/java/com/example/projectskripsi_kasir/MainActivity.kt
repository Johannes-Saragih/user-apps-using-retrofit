package com.example.projectskripsi_kasir

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.projectskripsi_kasir.Model.MainModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {

    private lateinit var btnbarcode: CardView
    private lateinit var btnriwayat: CardView
    private lateinit var btnkeluar: CardView

    private lateinit var tvusername: TextView

    private lateinit var buttonyes: Button
    private lateinit var buttonno: Button

    private val mainModel by viewModels<MainModel>()

    private val loadingbar: Long = 3000

    var username = ""
    var userId = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.getStringExtra("name").toString()
        userId = intent.getStringExtra("id").toString()

        btnbarcode = findViewById(R.id.barcodebtn)
        btnriwayat = findViewById(R.id.riwayat)
        tvusername = findViewById(R.id.tvusername)
        btnkeluar = findViewById(R.id.btnkeluar)

        tvusername.text = " Hi "+ username +"! We hope you enjoy our application."


        btnbarcode.setOnClickListener{
            val intent = Intent(this, BarcodeActivity::class.java).apply {
                putExtra("id",userId)
                putExtra("username",username)
            }
            startActivity(intent)
        }

        btnriwayat.setOnClickListener{
            val intent = Intent(this, RiwayatTransaksi::class.java).apply {
                putExtra("id",userId)
            }
            startActivity(intent)
        }

        btnkeluar.setOnClickListener {
            val dialoginformation = Dialog(this)
            val dialog = layoutInflater.inflate(R.layout.confirmlogout, null)

            buttonyes= dialog.findViewById(R.id.btnyes)
            buttonno= dialog.findViewById(R.id.btncancel)

            buttonyes.setOnClickListener {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }

            buttonno.setOnClickListener {
                dialoginformation.dismiss()
            }

            dialoginformation.setContentView(dialog)
            dialoginformation.setCancelable(false)
            dialoginformation.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialoginformation.show()
        }
    }
}

