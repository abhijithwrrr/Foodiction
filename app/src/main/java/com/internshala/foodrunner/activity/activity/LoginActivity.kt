package com.internshala.foodrunner.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.internshala.foodrunner.R

class LoginActivity : AppCompatActivity() {

    lateinit var etMobile: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgot: TextView
    lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgot = findViewById(R.id.txtForgot)
        txtRegister = findViewById(R.id.txtRegister)



        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
            startActivity(intent)
        }

        txtRegister.setOnClickListener {

            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, MiscActivity::class.java)

            startActivity(intent)


        }

    }


}




