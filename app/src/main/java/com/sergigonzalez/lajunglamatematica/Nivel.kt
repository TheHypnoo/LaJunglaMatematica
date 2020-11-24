package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var cargaNivel: TextView
    private lateinit var todoNivel: LinearLayout
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val email = user?.email
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
        loadingAnimation.speed = 4.50F
        buscaNivel()
         Handler(Looper.getMainLooper()).postDelayed({
             loadingAnimation.visibility = View.GONE
             cargaNivel.visibility = View.GONE
             todoNivel.visibility = View.VISIBLE
             nivelSuma()
         }, 2500)

    }

    private fun findID(){
        queHacer = findViewById(R.id.queHacer)
        enunciadoNivel = findViewById(R.id.enunciadoNivel)
        Correcto = findViewById(R.id.Correcto)
        Incorrecto = findViewById(R.id.Incorrecto)
        ResultadoEditText = findViewById(R.id.ResultadoEditText)
        bt_corregir = findViewById(R.id.bt_corregir)
        loadingAnimation = findViewById(R.id.loadingAnimation)
        cargaNivel = findViewById(R.id.cargaNivel)
        todoNivel = findViewById(R.id.todoNivel)
    }

    private fun guardaNivel(){
        db.collection("users").document(id).update("queNivel",x)
        println("Guardado: $x AND $posNivel")
    }

    private fun buscaNivel(): Int {
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
        if(posNivel >= x) {
            x = posNivel
            println("Switch: $x AND $posNivel")
        }
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
                enunciadoNivel.text = "Mi papa también me pidió que le ayudara. Esta vez teníamos que lavar un"+
                "par de coches y me dijo que necesitábamos $numero1 litros de agua y $numero2 litros de jabón."+
                "¿Cuántos litros de jabón y agua tengo que coger en total?"
            }
            3 -> {
                enunciadoNivel.text = "Mientras lavamos los coches se nos acabó el jabón y el agua y mi papa me"+
                        "mandó a por $numero1 litros de jabón y $numero2 litros de agua más. ¿Cuántos litros"+
                "necesitamos esta vez?"
            }
            4 -> {
                "Cuándo acabamos de lavar los coches ya era la hora de cenar así que entré"+
                "en casa y ayudé a mi mama a hacer la cena. Mientras mi mama cocinaba, yo"+
                "tenía que poner la mesa: $numero1 tenedores, $numero2 cuchillos. ¿Entonces,"+
                "cuántos cubiertos debo poner en la mesa entre cucharas, tenedores y"+
                "cuchillos?"
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
            Handler(Looper.myLooper()!!).postDelayed({
                ++x
                if (x >= posNivel) {
                    guardaNivel()
                }
                println("PosNivel: $posNivel X: $x")
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                nivelSuma()
            }, 1500)
        }
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

}