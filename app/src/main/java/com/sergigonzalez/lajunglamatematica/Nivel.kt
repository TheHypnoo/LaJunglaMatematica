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
    private var estoyMultiplica = false
    private var finalMultiplica = false
    private var estoyDivision = false
    private var finalDivision = false
    private var dbSuma = -1
    private var dbResta = -1
    private var dbMultiplica = -1
    private var dbDivision = -1
    private var lvlSuma = 0
    private var lvlResta = 0
    private var lvlMultiplica = 0
    private var lvlDivision = 0

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
             //Escojo la media
             if(!finalSuma) {
                 estoySuma = true
             } else if(!finalResta) {
                 estoySuma = false
                 estoyResta = true
             } else if(!finalMultiplica) {
                 estoyResta = false
                 estoyMultiplica = true
             } else if(!finalDivision) {
                 estoyMultiplica = false
                 estoyDivision = true
             }
             when {
                 estoySuma -> {
                     nivelSuma()
                 }
                 estoyResta -> {
                     nivelResta()
                 }
                 estoyMultiplica -> {
                     nivelMultiplica()
                 }
                 estoyDivision -> {
                     nivelDivision()
                 }
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
        if(lvlSuma >= dbSuma)
            db.collection("users").document(id).update("lvlSuma", lvlSuma)

        if(lvlResta >= dbResta)
            db.collection("users").document(id).update("lvlResta", lvlResta)

        if(lvlMultiplica >= dbMultiplica)
            db.collection("users").document(id).update("lvlMultiplica", lvlMultiplica)

        if(lvlDivision >= dbDivision)
            db.collection("users").document(id).update("lvlDivision", lvlDivision)

        println("Guardado: $lvlSuma AND $dbSuma")
    }

    private fun buscaNivel() {

        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        id = document.id
                        dbSuma = document.data["lvlSuma"].toString().toLong().toInt()
                        dbResta = document.data["lvlResta"].toString().toLong().toInt()
                        dbMultiplica = document.data["lvlMultiplica"].toString().toLong().toInt()
                        dbDivision = document.data["lvlDivision"].toString().toLong().toInt()
                        println("InfoNivel: $dbSuma AND $dbResta")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    @SuppressLint("SetTextI18n")
    private fun whenSuma(){
        if(dbSuma >= lvlSuma) {
            lvlSuma = dbSuma
            println("Switch: $lvlSuma AND $dbSuma")
        }
        if(lvlSuma == 6) {
            finalSuma = true
            estoySuma = false
            estoyResta = true
            nivelResta()
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

        if(lvlResta == 6) {
            finalResta = true
            estoyResta = false
            estoyMultiplica = true
            nivelMultiplica()
            lvlUP.visibility = View.GONE
            lvlDown.visibility = View.GONE
        }
        while(numero1 < numero2) numero1 = generaNumeros()

        when (lvlResta) {
            0 -> {
                enunciadoNivel.text =
                        "¡Hola amigo! Necesito tu ayuda. Quiero comerme un donut para merendar y ahora tengo $numero1 euros. " +
                                "Si el donut me cuesta $numero2, ¿cuánto dinero me queda?  "
            }
            1 -> {
                enunciadoNivel.text =
                        "Cuando llego a casa, mi papa me da $numero1 € para que vaya a comprar pizza para cenar. " +
                                "Cuando vuelvo a casa con las pizzas y el cambio, este es de $numero2 €. " +
                                "¿Cuánto me han costado las pizzas?"
            }
            2 -> {
                enunciadoNivel.text = "La pizza está cortada en $numero1 trozos y cojo $numero2. ¿Cuántos trozos quedan de pizza?"
            }
            3 -> {
                enunciadoNivel.text = "A la mañana siguiente, me levanto y bajo a la cocina a prepárame el desayuno. Veo $numero1 naranjas en la cesta de la fruta y cojo $numero2 para hacerme el zumo. " +
                        "¿Cuántas naranjas quedan ahora en la cesta?"
            }
            4 -> {
                enunciadoNivel.text = "Cuando acabo de desayunar mi mama me lleva a la escuela y me da $numero1 € para que me compre el desayuno." +
                        " A la hora del patio, voy al comedor y pido un bocadillo y un zumo y una vez pago me queda $numero2 €. " +
                        "¿Cuánto me ha costado el bocata y el zumo?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun whenMultiplica(){
        if(dbMultiplica >= lvlMultiplica) {
            lvlMultiplica = dbMultiplica
            println("Switch: $lvlMultiplica AND $dbMultiplica")
        }

        if(lvlMultiplica == 6) {
            finalMultiplica = true

            estoyMultiplica = false
            estoyDivision = true
            nivelDivision()
            lvlUP.visibility = View.GONE
            lvlDown.visibility = View.GONE
        }

        when (lvlMultiplica) {
            0 -> {
                enunciadoNivel.text = "¡Hola amigo! Tengo un pequeño problema. Hoy he ido al mercado y he comprado $numero1 plátanos que cuestan $numero2 euros cada uno." +
                        " ¿Me podrías decir cuánto dinero tengo que pagar por los plátanos?"
            }
            1 -> {
                enunciadoNivel.text =
                        "A más, también he comprado $numero1 peras a $numero2 € por pieza. ¿Cuánto tengo que pagar por las peras?"
            }
            2 -> {
                enunciadoNivel.text = "Y, por último, he comprado $numero1 kg de uvas a $numero2 € el kg. ¿A cuánto pago las uvas?"
            }
            3 -> {
                enunciadoNivel.text = "Multiplica4"
            }
            4 -> {
                enunciadoNivel.text = "Multiplica5"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun whenDivision(){
        if(dbDivision >= lvlDivision) {
            lvlDivision = dbDivision
            println("Switch: $lvlDivision AND $dbDivision")
        }

        if(lvlDivision == 6) {
            finalDivision = true
            estoyDivision = false
            lvlUP.visibility = View.GONE
            lvlDown.visibility = View.GONE
        }
        while(numero1 < numero2) numero1 = generaNumeros()

        when (lvlDivision) {
            0 -> {
                enunciadoNivel.text =
                        "¡Hola amigo! ¿Me puedes ayudar un momento? He quedado con mis amigos para merendar y he comprado $numero1 magdalenas. Si somos $numero2 amigos, " +
                                "¿Cuántas magdalenas nos podemos comer cada uno?"
            }
            1 -> {
                enunciadoNivel.text =
                        "Division2"
            }
            2 -> {
                enunciadoNivel.text = "Division3"
            }
            3 -> {
                enunciadoNivel.text = "Division4"
            }
            4 -> {
                enunciadoNivel.text = "Division5"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelSuma(){
        if(!finalResta && lvlSuma >= 3) {
            lvlUP.visibility = View.VISIBLE
            lvlUP.setOnClickListener {
                nivelResta()
                lvlUP.visibility = View.GONE
            }
        } else {
            lvlUP.visibility = View.GONE
        }
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
        if(finalSuma && !finalMultiplica) {
            lvlUP.visibility = View.VISIBLE
            lvlUP.setOnClickListener {
                nivelMultiplica()
                lvlUP.visibility = View.GONE
            }
        } else {
            lvlUP.visibility = View.GONE
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
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                if (lvlResta >= 3 && finalSuma) {
                    lvlUP.visibility = View.VISIBLE
                    lvlUP.setOnClickListener {
                        nivelMultiplica()
                        lvlUP.visibility = View.GONE
                    }
                    nivelResta()
                } else {
                    nivelResta()
                }
            }, 1500)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelMultiplica(){
        if(!finalResta) {
            lvlDown.visibility = View.VISIBLE
            lvlDown.setOnClickListener {
                nivelResta()
                lvlDown.visibility = View.GONE
            }
        } else {
            lvlDown.visibility = View.GONE
        }
        if(finalResta && !finalDivision) {
            lvlUP.visibility = View.VISIBLE
            lvlUP.setOnClickListener {
                nivelDivision()
                lvlUP.visibility = View.GONE
            }
        } else {
            lvlUP.visibility = View.GONE
        }
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenMultiplica()
        queHacer.text = "Multiplica los dos numeros y escribe el resultado abajo."
        Resultado = numero1 * numero2

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
                ++lvlMultiplica
                if (lvlMultiplica >= dbMultiplica) {
                    guardaNivel()
                }
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                if (lvlMultiplica >= 3 && finalResta) {
                    lvlUP.visibility = View.VISIBLE
                    lvlUP.setOnClickListener {
                        nivelDivision()
                        lvlUP.visibility = View.GONE
                    }
                    nivelMultiplica()
                } else {
                    nivelMultiplica()
                }
            }, 1500)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelDivision(){
        if(!finalMultiplica) {
            lvlDown.visibility = View.VISIBLE
            lvlDown.setOnClickListener {
                nivelMultiplica()
                lvlDown.visibility = View.GONE
            }
        } else {
            lvlDown.visibility = View.GONE
        }
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenDivision()
        queHacer.text = "Divide los dos numeros y escribe el resultado abajo."
        Resultado = numero1 / numero2

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
                ++lvlDivision
                if (lvlDivision >= dbDivision) {
                    guardaNivel()
                }
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
                nivelDivision()
            }, 1500)
        }
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

}