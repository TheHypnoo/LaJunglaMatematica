package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var lvlUP: LottieAnimationView
    private lateinit var lvlDown: LottieAnimationView
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val email = user?.email
    var id = ""
    private var numero1 = 0
    private var numero2 = 0
    private var Resultado = 0
    private var estoySuma = true
    private var finalSuma = false
    private var estoyResta = false
    private var finalResta = false
    private var dbSuma = -1
    private var dbResta = -1
    private var lvlSuma = 0
    private var lvlResta = 0

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
             if (dbSuma >= 3) {
                 estoySuma = false
                 estoyResta = true
             }
             if (estoySuma) {
                 nivelSuma()
             } else if (estoyResta) {
                 nivelResta()
             }
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
        lvlUP = findViewById(R.id.lvlUP)
        lvlDown = findViewById(R.id.lvlDown)
    }

    private fun guardaNivel(){
        if(lvlSuma >= dbSuma) {
            db.collection("users").document(id).update("lvlSuma", lvlSuma)
        }
        if(lvlResta >= dbResta) {
            db.collection("users").document(id).update("lvlResta", lvlResta)
        }
        println("Guardado: $lvlSuma AND $dbSuma")
    }

    private fun buscaNivel(): Int {

        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        id = document.id
                        dbSuma = document.data["lvlSuma"].toString().toLong().toInt()
                        dbResta = document.data["lvlResta"].toString().toLong().toInt()
                        println("InfoNivel: $dbSuma AND $dbResta")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
        return dbSuma
    }

    @SuppressLint("SetTextI18n")
    private fun whenSuma(){
        if(dbSuma >= lvlSuma) {
            lvlSuma = dbSuma
            println("Switch: $lvlSuma AND $dbSuma")
        }
        if(lvlSuma == 6) {
            finalSuma = true
            nivelResta()
            estoySuma = false
            estoyResta = true
            lvlUP.visibility = View.GONE
            lvlDown.visibility = View.GONE
        }
        when (lvlSuma) {
            0 -> {
                enunciadoNivel.text =
                        " ¡Hola amigo! Necesito tu ayuda." +
                                " Mi mama me ha pedido que vaya a comprar fruta al mercado " +
                                "y me ha pedido que compre " + numero1 + " manzanas  y " + numero2 + " peras. " +
                                "¿Cuantas piezas de fruta  tengo que comprar en total?"
            }
            1 -> {
                enunciadoNivel.text =
                        "A cambio de comprarle la fruta, mi mama me dio " +
                                numero1 + " euros así que los fui a meter en mi hucha. A más, yo ya tenía en mi hucha " +
                                numero2 + " euros. ¿Cuánto dinero tengo ahora en la hucha?"
            }
            2 -> {
                enunciadoNivel.text = "Mi papa también me pidió que le ayudara. Esta vez teníamos que lavar un" +
                        "par de coches y me dijo que necesitábamos $numero1 litros de agua y $numero2 litros de jabón." +
                        "¿Cuántos litros de jabón y agua tengo que coger en total?"
            }
            3 -> {
                enunciadoNivel.text = "Mientras lavamos los coches se nos acabó el jabón y el agua y mi papa me" +
                        "mandó a por $numero1 litros de jabón y $numero2 litros de agua más. ¿Cuántos litros" +
                        "necesitamos esta vez?"
            }
            4 -> {
                enunciadoNivel.text = "Cuándo acabamos de lavar los coches ya era la hora de cenar así que entré" +
                        "en casa y ayudé a mi mama a hacer la cena. Mientras mi mama cocinaba, yo" +
                        "tenía que poner la mesa: $numero1 tenedores, $numero2 cuchillos. ¿Entonces," +
                        "cuántos cubiertos debo poner en la mesa entre cucharas, tenedores y" +
                        "cuchillos?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun whenResta(){
        if(dbResta >= lvlResta) {
            lvlResta = dbResta
            println("Switch: $lvlResta AND $dbResta")
        }

        when (lvlResta) {
            0 -> {
                enunciadoNivel.text =
                        " Resta1"
            }
            1 -> {
                enunciadoNivel.text =
                        "Resta2"
            }
            2 -> {
                enunciadoNivel.text = "Resta3"
            }
            3 -> {
                enunciadoNivel.text = "Resta4"
            }
            4 -> {
                enunciadoNivel.text = "Resta5"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelSuma(){
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenSuma()
        if(!finalSuma) {
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
                    ++lvlSuma
                    if (lvlSuma >= dbSuma) {
                        guardaNivel()
                    }
                    println("PosNivel: $dbSuma X: $lvlSuma")
                    Incorrecto.visibility = View.GONE
                    Correcto.visibility = View.GONE
                    bt_corregir.visibility = View.VISIBLE
                    ResultadoEditText.text.clear()
                    if (lvlSuma >= 3) {
                        lvlUP.visibility = View.VISIBLE
                        lvlUP.setOnClickListener {
                            nivelResta()
                            lvlUP.visibility = View.GONE
                        }
                        nivelSuma()
                    } else {
                        nivelSuma()
                    }
                }, 1500)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelResta(){
        if(!finalSuma) {
            lvlDown.visibility = View.VISIBLE
            lvlDown.setOnClickListener {
                nivelSuma()
                lvlDown.visibility = View.GONE
            }
        } else {
            lvlDown.visibility = View.GONE
        }
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenResta()
        queHacer.text = "Resta los dos numeros y escribe el resultado abajo."
        Resultado = numero1 - numero2

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
                ++lvlResta
                if (lvlResta >= dbResta) {
                    guardaNivel()
                }
                println("PosNivel: $dbResta X: $lvlResta")
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                nivelResta()
            }, 1500)
        }
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

}