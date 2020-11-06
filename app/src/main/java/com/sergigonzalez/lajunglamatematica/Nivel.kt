package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Nivel : AppCompatActivity() {

    private lateinit var ET_Respuesta: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel)
        InitButtons()
        initListeners()
    }

    private fun InitButtons() {
        ET_Respuesta = findViewById(R.id.editTextNumber)
    }
    private fun initListeners() {
//"https://medium.com/mobile-app-development-publication/making-android-edittext-accept-number-only-efbe2ba1cd69"
    }
}