package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class menuPrincipal : AppCompatActivity() {

    private lateinit var startGame: Button
    private lateinit var timeTrial: Button
    private lateinit var ranking: Button
    private lateinit var leave: Button
    private lateinit var LayoutMenu: ConstraintLayout
    private val db = FirebaseFirestore.getInstance()
    private var id = ""
    private var pruebateSuma = false
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var mLastClickTime: Long = 0
    private var haJugado: Boolean = false
    private lateinit var animalAnimation: LottieAnimationView
    private var yo: String = ""

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
        }, 3000)
    }

    override fun onResume() {
        super.onResume()
        buscaPruebate()
        findID()
        animalAnimation.speed = 0.40F
        Handler(Looper.getMainLooper()).postDelayed({
            animalAnimation.visibility = View.GONE
            initListeners()
        }, 3000)
    }

    private fun findID() {
        startGame = findViewById(R.id.startGame)
        timeTrial = findViewById(R.id.timeTrial)
        ranking = findViewById(R.id.ranking)
        leave = findViewById(R.id.leave)
        animalAnimation = findViewById(R.id.AnimalAnimation)
        LayoutMenu = findViewById(R.id.LayoutMenu)
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
                        yo = document.data["Nombre de Usuario"].toString()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error al obtener los documentos: ", exception)
                }
    }

    private fun initListeners() {

        if(haJugado) {
            Snackbar.make(LayoutMenu, "Bienvenido de nuevo! $yo", Snackbar.LENGTH_LONG).show()
            startGame.text = getString((R.string.Continue))
        } else {
            Snackbar.make(LayoutMenu, "Bienvenido! $yo", Snackbar.LENGTH_LONG).show()
            startGame.text = getString((R.string.startGame))
            haJugado = true
            Handler(Looper.getMainLooper()).postDelayed({
                db.collection("users").document(id).update("haJugado", haJugado)
            }, 2000)
        }
        startGame.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                if (pruebateSuma) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@postDelayed
                    } else {
                        startActivity(Intent(applicationContext, Nivel::class.java))
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()

                } else if (!pruebateSuma) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                        return@postDelayed
                    } else {
                        startActivity(Intent(applicationContext, Pruebate::class.java))
                    }
                    mLastClickTime = SystemClock.elapsedRealtime()
                }
            }, 500)
            }

        timeTrial.setOnClickListener{
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return@setOnClickListener
            } else {
                startActivity(Intent(applicationContext, countDown::class.java))
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

        ranking.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return@setOnClickListener
            } else {
                startActivity(Intent(applicationContext, Ranking::class.java))
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

        leave.setOnClickListener addOnSuccessListener@{
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return@addOnSuccessListener
            } else {
                val view = View.inflate(this, R.layout.dialog_leave, null)

                val builder = AlertDialog.Builder(this)
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                val btnSalir = view.findViewById<Button>(R.id.btn_leave)

                val btnSignOut = view.findViewById<Button>(R.id.btn_signOut)

                val closeDialog = view.findViewById<ImageView>(R.id.closeDialog)

                btnSalir.setOnClickListener{
                    finish()
                }

                btnSignOut.setOnClickListener{
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(applicationContext, AuthActivity::class.java))
                    finish()
                }

                closeDialog.setOnClickListener{
                    dialog.dismiss()
                }

            }
            mLastClickTime = SystemClock.elapsedRealtime()


        }
    }
}