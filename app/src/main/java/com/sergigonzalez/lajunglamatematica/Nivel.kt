package com.sergigonzalez.lajunglamatematica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView

class Nivel : AppCompatActivity() {
    private lateinit var animacionIncorrecto: LottieAnimationView
    private lateinit var animacionCorrecto: LottieAnimationView
    private lateinit var Corregir: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nivel)
        animacionCorrecto = findViewById(R.id.animacionCorrecto)
        animacionIncorrecto = findViewById(R.id.animacionIncorrecto)
        Corregir = findViewById(R.id.Corregir)
        animacionIncorrecto.pauseAnimation()
        animacionCorrecto.pauseAnimation()
        Corregir.setOnClickListener {
            animacionCorrecto.playAnimation()
            animacionIncorrecto.playAnimation()
            println("Boton apretado, animaciones encendidas!")
        }
    }

}