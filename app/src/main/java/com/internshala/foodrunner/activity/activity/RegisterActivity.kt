package com.internshala.foodrunner.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.internshala.foodrunner.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Register Yourself"
    }
}