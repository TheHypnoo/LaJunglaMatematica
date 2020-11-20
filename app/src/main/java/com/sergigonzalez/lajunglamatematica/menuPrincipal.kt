package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class menuPrincipal : AppCompatActivity() {

    private lateinit var BT_EmpezarJuego: Button
    private lateinit var BT_PartidasGuardadas: Button
    private lateinit var BT_Ranking: Button
    private lateinit var BT_Salir: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_menu)

        InitButtons()
        initListeners()
        GuardaPrueba()
        println("Juego iniciado correctamente!")
    }

    private fun InitButtons(){
        BT_EmpezarJuego = findViewById(R.id.BT_EmpezarJuego)
        BT_PartidasGuardadas = findViewById(R.id.BT_PartidasGuardadas)
        BT_Ranking = findViewById(R.id.BT_Ranking)
        BT_Salir = findViewById(R.id.BT_Salir)
    }

    private fun initListeners() {
        BT_EmpezarJuego.setOnClickListener {
            val Niveles: Intent = Intent(applicationContext ,AnimationGame:: class.java)
            startActivity(Niveles)
            println("Empezar Juego")}
        BT_PartidasGuardadas.setOnClickListener {
            val PartidasGuardadas: Intent = Intent(applicationContext ,PartidasGuardadas:: class.java)
            startActivity(PartidasGuardadas)
            println("Partidas Guardadas")}
        BT_Ranking.setOnClickListener {
            val Ranking: Intent = Intent(applicationContext ,Ranking:: class.java)
            startActivity(Ranking)
            println("Ranking")}
        BT_Salir.setOnClickListener {finish()
        println("Juego cerrado!")}
    }

    private fun GuardaPrueba(){
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val myEditor = myPreferences.edit()
        myEditor.putString("NAME", "Sergi");
        myEditor.putInt("AGE", 20);
        myEditor.putBoolean("SINGLE?", false);
        myEditor.commit();
        val name = myPreferences.getString("NAME", "")
        val age = myPreferences.getInt("AGE", 100)
        val isSingle = myPreferences.getBoolean("SINGLE?", true)
        if(!isSingle) {
            println("Es falso")
        }
        println(name)
        println(age)
        println(isSingle)
    }

}