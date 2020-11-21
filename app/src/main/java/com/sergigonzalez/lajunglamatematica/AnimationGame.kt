package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class AnimationGame : AppCompatActivity() {
    private lateinit var Start: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_animation_game)
        Start = findViewById(R.id.Start)
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, Pruebate::class.java)

            startActivity(mainIntent)
            finish()
        }, 5000)
    }
}