package com.test.gatien.gosecury

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val camera = this.getSystemService(Context.CAMERA_SERVICE)

        val button = findViewById<Button>(R.id.btnAuthentication)
        button.setOnClickListener(){
            button.setBackgroundColor(Color.parseColor("#00574B"))

        }
    }

}
