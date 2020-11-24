package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Nivel : AppCompatActivity() {
    private lateinit var queHacer: TextView
    private lateinit var enunciadoNivel: TextView
    private lateinit var Correcto: LottieAnimationView
    private lateinit var Incorrecto: LottieAnimationView
    private lateinit var ResultadoEditText: EditText
    private lateinit var bt_corregir: Button
    val db = FirebaseFirestore.getInstance()
    var id = ""
    private var numero1 = 0
    private var numero2 = 0
    private var Resultado = 0
    private var posNivel = -1
    private var x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nivel)
        findID()
        whenNiveles()
        nivelSuma()
    }

    override fun onStart() {
        buscaNivel()
        super.onStart()

    }

    private fun findID(){
        queHacer = findViewById(R.id.queHacer)
        enunciadoNivel = findViewById(R.id.enunciadoNivel)
        Correcto = findViewById(R.id.Correcto)
        Incorrecto = findViewById(R.id.Incorrecto)
        ResultadoEditText = findViewById(R.id.ResultadoEditText)
        bt_corregir = findViewById(R.id.bt_corregir)
    }

    private fun buscaNivel(): Int {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        db.collection("users")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        id = document.id
                        posNivel = document.data["queNivel"].toString().toLong().toInt()
                        println("InfoNivel: $posNivel")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
        return posNivel
    }

    @SuppressLint("SetTextI18n")
    private fun whenNiveles(){
        println("Level: $posNivel")
        if(posNivel >= x) {
            x = posNivel

        when (x) {
            0 -> {
                enunciadoNivel.text =
                        " ¡Hola amigo! Necesito tu ayuda." +
                                " Mi mama me ha pedido que vaya a comprar fruta al mercado " +
                                "y me ha pedido que compre " +numero1+ " manzanas  y " +numero2+ " peras. " +
                                "¿Cuantas piezas de fruta  tengo que comprar en total?"
            }
            1 -> {
                enunciadoNivel.text =
                        "A cambio de comprarle la fruta, mi mama me dio " +
                                numero1 + " euros así que los fui a meter en mi hucha. A más, yo ya tenía en mi hucha " +
                                numero2 + " euros. ¿Cuánto dinero tengo ahora en la hucha?"
                }
            2 -> {
                enunciadoNivel.text =
                        "Y este es el puto lvl3"
            }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelSuma(){
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenNiveles()
        queHacer.text = "Suma los dos numeros y escribe el resultado abajo."
        Resultado = numero1 + numero2
        bt_corregir.setOnClickListener{
            if(ResultadoEditText.text.toString() == Resultado.toString()) {
                Correcto.visibility = View.VISIBLE
                Correcto.playAnimation()
            } else {
                Incorrecto.visibility = View.VISIBLE
                Incorrecto.playAnimation()
            }
            bt_corregir.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                ++x
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                nivelSuma()
            }, 3000)

        }
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

}