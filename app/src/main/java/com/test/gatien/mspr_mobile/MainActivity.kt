package com.test.gatien.mspr_mobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_auth_click = findViewById(R.id.btnAuthentication) as Button

        btn_auth_click.setOnClickListener {
            btn_auth_click.setBackgroundColor(Color.parseColor("#00574B"))
        }
    }
}
