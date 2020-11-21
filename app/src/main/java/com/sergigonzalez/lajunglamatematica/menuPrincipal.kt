package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore


class menuPrincipal : AppCompatActivity() {

    private lateinit var BT_EmpezarJuego: Button
    private lateinit var BT_PartidasGuardadas: Button
    private lateinit var BT_Ranking: Button
    private lateinit var BT_Salir: Button
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu)
        var user = FirebaseAuth.getInstance().currentUser
        var email = user?.email

        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("Admin").build()

        var name = user?.displayName

        user!!.updateProfile(profileUpdates)
        println("Email: $email Usuario: $name")

        InitButtons()
        initListeners()
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
            val Niveles: Intent = Intent(applicationContext, AnimationGame::class.java)
            startActivity(Niveles)
            finish()
            println("Empezar Juego")}
        BT_PartidasGuardadas.setOnClickListener {
            val PartidasGuardadas: Intent = Intent(applicationContext, PartidasGuardadas::class.java)
            startActivity(PartidasGuardadas)
            println("Partidas Guardadas")}
        BT_Ranking.setOnClickListener {
            val Ranking: Intent = Intent(applicationContext, Ranking::class.java)
            startActivity(Ranking)
            println("Ranking")}
        BT_Salir.setOnClickListener {finish()
        println("Juego cerrado!")}
    }
}