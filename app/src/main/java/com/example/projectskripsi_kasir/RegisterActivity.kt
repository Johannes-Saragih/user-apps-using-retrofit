package com.example.projectskripsi_kasir

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.projectskripsi_kasir.Model.MainModel
import com.google.android.material.appbar.MaterialToolbar
import androidx.lifecycle.Observer


class RegisterActivity : AppCompatActivity() {

    private lateinit var btndaftar : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var namapengguna : EditText
    private lateinit var back : MaterialToolbar

    private val mainModel: MainModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btndaftar = findViewById(R.id.btndaftar)
        namapengguna = findViewById(R.id.edtnamapengguna)
        email = findViewById(R.id.edtemail)
        password = findViewById(R.id.edtpassword)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        btndaftar.setOnClickListener {
            if (namapengguna.text.toString().isNotEmpty() and email.text.toString().isNotEmpty() and password.text.toString().isNotEmpty()) {
                mainModel.registerUser(namapengguna.text.toString(),email.text.toString(), password.text.toString())
                mainModel.isSuccess.observe(this, Observer { isSuccess ->
                    if (isSuccess){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                })
                mainModel.toast.observe(this, Observer { message ->
                    message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else {
                Toast.makeText(this,"Lengkapin data terlebih dahulu", Toast.LENGTH_SHORT)
            }
        }


    }
}