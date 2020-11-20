package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window

class AnimationLoading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_animation_loading)
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, menuPrincipal::class.java)
            startActivity(mainIntent)
            finish()
        }, 5000)
    }
}