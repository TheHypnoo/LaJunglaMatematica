package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.airbnb.lottie.LottieAnimationView

class AnimationGame : AppCompatActivity() {
    private lateinit var Loading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_animation_game)
        Loading = findViewById(R.id.Loading)
        Loading.speed = 0.25F
        Loading.reverseAnimationSpeed()
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, pruebate::class.java)
            startActivity(mainIntent)
            finish()
        }, 5000)
    }
}