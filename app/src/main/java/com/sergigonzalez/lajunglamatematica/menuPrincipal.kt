package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class menuPrincipal : AppCompatActivity() {

    private lateinit var BT_EmpezarJuego: Button
    private lateinit var BT_PartidasGuardadas: Button
    private lateinit var BT_Ranking: Button
    private lateinit var BT_Salir: Button
    val db = FirebaseFirestore.getInstance()
    var id = ""
    var animGame = false
    private var mLastClickTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email


        db.collection("users")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        id = document.id
                        animGame = document.data["animGame"] as Boolean
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }

        InitButtons()
        initListeners()
    }

    private fun InitButtons() {
        BT_EmpezarJuego = findViewById(R.id.BT_EmpezarJuego)
        BT_PartidasGuardadas = findViewById(R.id.BT_PartidasGuardadas)
        BT_Ranking = findViewById(R.id.BT_Ranking)
        BT_Salir = findViewById(R.id.BT_Salir)
    }

    private fun initListeners() {
        BT_EmpezarJuego.setOnClickListener {
                if (animGame) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@setOnClickListener
                    } else {
                        val Niveles: Intent = Intent(applicationContext, Nivel::class.java)
                        startActivity(Niveles)
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                } else if(!animGame){
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@setOnClickListener
                    } else {
                        val Animacion: Intent = Intent(applicationContext, AnimationGame::class.java)
                        startActivity(Animacion)
                        db.collection("users").document(id).update("animGame", true)
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                }
            }
        BT_PartidasGuardadas.setOnClickListener {
            val PartidasGuardadas: Intent = Intent(applicationContext, PartidasGuardadas::class.java)
            startActivity(PartidasGuardadas)
            println("Partidas Guardadas")
        }
        BT_Ranking.setOnClickListener {
            val Ranking: Intent = Intent(applicationContext, Ranking::class.java)
            startActivity(Ranking)
            println("Ranking")
        }
        BT_Salir.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Title")
            alertDialog.setMessage("Message")

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"
            ) { dialog, which -> dialog.dismiss() }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"
            ) { dialog, which -> dialog.dismiss() }

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()

            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val btnNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            btnNeutral.layoutParams = layoutParams
            btnPositive.layoutParams = layoutParams
            btnNegative.layoutParams = layoutParams






            /*
            val builder = AlertDialog.Builder(this)
            //builder.setMessage("Seleccione Cerrar Sesión o Salir y Cerrar sesión")

            builder.setPositiveButton("Cerrar Sesión") { dialog, which ->
                val CerrarSesion: Intent = Intent(applicationContext, AuthActivity::class.java)
                startActivity(CerrarSesion)
                finish()
            }

            builder.setNegativeButton("Salir y cerrar Sesión") { dialog, which ->
                finish()
            }
            builder.setNeutralButton("Cancelar",null)



            builder.show()

             */
        }
    }
}