package com.example.projectskripsi_kasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.projectskripsi_kasir.Model.MainModel

class LoginActivity : AppCompatActivity() {

    private lateinit var btnlogin : Button
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var textdaftar : TextView

    private val mainModel: MainModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.edtemail)
        password = findViewById(R.id.edtpassword)
        btnlogin = findViewById(R.id.btnlogin)
        textdaftar = findViewById(R.id.daftar)

        btnlogin.setOnClickListener {

            if(email.text.toString().isNotEmpty() and password.text.toString().isNotEmpty()){
                mainModel.loginUser(email.text.toString(), password.text.toString())

                mainModel.isSuccess.observe(this, Observer { isSuccess ->

                    var Name = ""
                    var User_id = ""

                    if (isSuccess) {
                        mainModel.username.observe(this , Observer {
                                username -> username?.let {
                            Name = username
                        }
                        })
                        mainModel.userid.observe(this , Observer {
                                userid -> userid?.let {
                            User_id = userid
                        }
                        })

                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("name", Name)
                            putExtra("id", User_id)
                        }
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                })
                mainModel.toast.observe(this, Observer { message ->
                    message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else {
                Toast.makeText(this,"Lengkapin email dan password", Toast.LENGTH_SHORT).show()
            }
        }

        textdaftar.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}