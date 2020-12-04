package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private lateinit var startGame: Button
    private lateinit var ranking: Button
    private lateinit var leave: Button
    private val db = FirebaseFirestore.getInstance()
    private var id = ""
    private var pruebateSuma = false
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_menu)
        buscaPruebate()
        findID()
        initListeners()
    }

    private fun findID() {
        startGame = findViewById(R.id.startGame)
        ranking = findViewById(R.id.ranking)
        leave = findViewById(R.id.leave)
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
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    private fun initListeners() {
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
            alertDialog.setTitle("Title")
            alertDialog.setMessage("Message")

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"
            ) { dialog, _ -> dialog.dismiss() }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"
            ) { dialog, _ -> dialog.dismiss() }

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel"
            ) { dialog, _ -> dialog.dismiss() }
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