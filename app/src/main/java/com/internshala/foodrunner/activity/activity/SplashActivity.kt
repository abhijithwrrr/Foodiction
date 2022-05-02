package com.internshala.foodrunner.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.internshala.foodrunner.R

class SplashActivity : AppCompatActivity() {

    lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        Handler().postDelayed({
            val intent = Intent(this, MiscActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }

}