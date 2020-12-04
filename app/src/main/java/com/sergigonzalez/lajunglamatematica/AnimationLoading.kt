package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.airbnb.lottie.LottieAnimationView

class AnimationLoading : AppCompatActivity() {
    private lateinit var loading: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_animation_loading)
        loading = findViewById(R.id.AnimalAnimation)
        loading.speed = 0.35F
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, menuPrincipal::class.java)
            startActivity(mainIntent)
            finish()
        }, 4500)
    }
}