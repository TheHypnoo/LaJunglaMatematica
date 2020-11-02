package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PantallaCarga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)
        alMain()
    }

    private fun alMain(){
        val MainActivity: Intent = Intent(applicationContext ,MainActivity:: class.java)
        startActivity(MainActivity)
    }
}