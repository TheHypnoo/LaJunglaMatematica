package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.preference.PreferenceManager

class EmpezarJuego : AppCompatActivity() {
    private lateinit var BT_NivelTortuga: Button
    private lateinit var BT_NivelZorro: Button
    private lateinit var BT_NivelMono: Button
    private lateinit var BT_NivelLeopardo: Button
    private lateinit var BT_NivelJungla: Button
    private lateinit var BT_GuardarySalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_empezar_juego)
        InitButtons()
        initListeners()
    }

    private fun InitButtons(){
        BT_NivelTortuga = findViewById(R.id.BT_NivelTortuga)
        BT_NivelZorro = findViewById(R.id.BT_NivelZorro)
        BT_NivelMono = findViewById(R.id.BT_NivelMono)
        BT_NivelLeopardo = findViewById(R.id.BT_NivelLeopardo)
        BT_NivelJungla = findViewById(R.id.BT_NivelJungla)
        BT_GuardarySalir = findViewById(R.id.BT_GuardarySalir)
    }

    private fun initListeners() {
        /*val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val myEditor = myPreferences.edit()
        myEditor.putString("NPartida1", "");
        myEditor.putBoolean("NPartida1", false)
        val name = myPreferences.getString("NPartida1", "")
        myEditor.commit();
         */
        BT_NivelTortuga.setOnClickListener { println("Nivel Tortuga")
            val Nivel: Intent = Intent(applicationContext ,Nivel:: class.java)
            startActivity(Nivel)}
        BT_NivelZorro.setOnClickListener { println("Nivel Zorro")}
        BT_NivelMono.setOnClickListener { println("Nivel Mono")}
        BT_NivelLeopardo.setOnClickListener { println("Nivel Leopardo")}
        BT_NivelJungla.setOnClickListener { println("Nivel Jungla")}
        BT_GuardarySalir.setOnClickListener {finish()
            println("Juego cerrado!")}
    }
}