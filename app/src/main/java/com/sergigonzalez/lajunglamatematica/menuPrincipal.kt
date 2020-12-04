package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class menuPrincipal : AppCompatActivity() {

    private lateinit var startGame: Button
    private lateinit var ranking: Button
    private lateinit var leave: Button
    private val db = FirebaseFirestore.getInstance()
    private var id = ""
    private var pruebateSuma = false
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var mLastClickTime: Long = 0
    private var haJugado: Boolean = false
    private lateinit var animalAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_menu)
        buscaPruebate()
        findID()
        animalAnimation.speed = 0.40F
        Handler(Looper.getMainLooper()).postDelayed({
            animalAnimation.visibility = View.GONE
            initListeners()
        },3000)

    }

    private fun findID() {
        startGame = findViewById(R.id.startGame)
        ranking = findViewById(R.id.ranking)
        leave = findViewById(R.id.leave)
        animalAnimation = findViewById(R.id.AnimalAnimation)
    }

    private fun buscaPruebate(){
        db.collection("users")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        id = document.id
                        pruebateSuma = document.data["pruebateSuma"].toString().toBoolean()
                        haJugado = document.data["haJugado"].toString().toBoolean()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    private fun initListeners() {
        if(haJugado) {
            startGame.text = getString((R.string.Continue))
        } else {
            startGame.text = getString((R.string.startGame))
            haJugado = true
            Handler(Looper.getMainLooper()).postDelayed({
                db.collection("users").document(id).update("haJugado", haJugado)
            },2000)
        }
        startGame.setOnClickListener {
            buscaPruebate()
            Handler(Looper.getMainLooper()).postDelayed({
                if (pruebateSuma) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@postDelayed
                    } else {
                        startActivity(Intent(applicationContext, Nivel::class.java))
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()

                } else if(!pruebateSuma){
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@postDelayed
                    } else {
                        startActivity(Intent(applicationContext, Pruebate::class.java))
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()
                }
            },200)
            }

        ranking.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return@setOnClickListener
            } else {
                startActivity(Intent(applicationContext, Ranking::class.java))
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

        leave.setOnClickListener {
            //Sin hacer :C
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))
            //alertDialog.setTitle("Salir")
            alertDialog.setMessage("Si quiere salir del juego, presione el boton 'Salir'\n\nSi quiere cerrar sesión, presione el boton \n'Cerrar Sesión'")

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Salir"
            ) { _, _ -> finish()  }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cerrar Sesión"
            ) { _, _ -> startActivity(Intent(applicationContext, AuthActivity::class.java)); finish() }
/*
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel"
            ) { dialog, _ -> dialog.dismiss() }

 */
            alertDialog.show()

            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            //val btnNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 5f
            //btnNeutral.layoutParams = layoutParams
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