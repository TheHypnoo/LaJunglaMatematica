package com.sergigonzalez.lajunglamatematica

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager.BadTokenException
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class countDown : AppCompatActivity() {
    var START_MILLI_SECONDS = 16000L
    private lateinit var timer: TextView
    private lateinit var number1: TextView
    private lateinit var number2: TextView
    private lateinit var symbol: TextView
    private lateinit var puntuacionMaximaCountDown: TextView
    private lateinit var resultado1: Button
    private lateinit var resultado2: Button
    private lateinit var resultado3: Button
    private lateinit var viewKonfetti: KonfettiView
    private lateinit var countdown_timer: CountDownTimer
    private lateinit var correctAnimation: LottieAnimationView
    private lateinit var incorrectAnimation: LottieAnimationView
    private var list = arrayOf("+", "-", "x", "/")
    var time_in_milli_seconds = 0L
    var correct = true
    private var resultado = -1
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var id = ""
    private var dbPuntuacion = -1
    private var puntuacion = 0
    private var puntuacionGanada = 0
    private var puntosGanados = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_count_down)
        findID()
        lifecycleScope.launch { withContext(Dispatchers.IO)
            {
                cargaPuntuacion()
            }
            startDialog()
        }
    }

    private fun findID(){
        timer = findViewById(R.id.timer)
        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        symbol = findViewById(R.id.symbol)
        puntuacionMaximaCountDown = findViewById(R.id.puntuacionMaximaCountDown)
        correctAnimation = findViewById(R.id.correct)
        incorrectAnimation = findViewById(R.id.incorrect)
        resultado1 = findViewById(R.id.resultado1)
        resultado2 = findViewById(R.id.resultado2)
        resultado3 = findViewById(R.id.resultado3)
        viewKonfetti = findViewById(R.id.viewKonfetti)
    }

    private fun guardaPuntuacion(){
        if(puntuacion >= dbPuntuacion) {
            db.collection("users").document(id).update("puntuacion", puntuacion)
        }
    }

    private fun cargaPuntuacion(){
        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("DB: Users", "${document.id} => ${document.data}")
                id = document.id
                dbPuntuacion = document.data["puntuacion"].toString().toLong().toInt()
            }
        }
                .addOnFailureListener { exception ->
                    Log.w("DB: Users", "Error al obtener la información: ", exception)
                }
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 750) {
            override fun onFinish() {
                resultado1.isClickable = false
                resultado2.isClickable = false
                resultado3.isClickable = false
                timer.text = "0:00"
                loadConfeti()
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.GONE
                finishDialog()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()

    }

    private fun updateTextUI() {
        val minute = (time_in_milli_seconds / 1000) / 60
        val seconds = (time_in_milli_seconds / 1000) % 60
        if(seconds <= 10L) {
            timer.setTextColor(Color.RED)
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        } else if(seconds <= 20){
            timer.setTextColor(Color.parseColor("#D4AC0D"))
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        } else {
            timer.setTextColor(Color.GREEN)
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        }
        if(seconds <= 9) {
            timer.text = "$minute:0$seconds"
        } else {
            timer.text = "$minute:$seconds"
        }
        correct = true
    }

    private fun level(){
        puntuacionMaximaCountDown.text = "Puntuacion Maxima: $puntuacion"
        if(puntosGanados > 8) {
            puntosGanados = 0
        }
        if(puntosGanados in 0..2) {
            number1.text = generaNumeros().toString()
            number2.text = generaNumeros().toString()
            symbol.text = list[0]
            resultado = number1.text.toString().toInt() + number2.text.toString().toInt()
        }
        if(puntosGanados in 2..4) {
            number1.text = generaNumeros().toString()
            number2.text = generaNumeros().toString()
            symbol.text = list[1]
            resultado = number1.text.toString().toInt() - number2.text.toString().toInt()
        }
        if(puntosGanados in 4..6) {
            number1.text = generaNumeros().toString()
            number2.text = generaNumeros().toString()
            symbol.text = list[2]
            resultado = number1.text.toString().toInt() * number2.text.toString().toInt()
        }
        if(puntosGanados in 6..8) {
            number1.text = generaNumeros().toString()
            number2.text = generaNumeros().toString()
            while ( (number1.text.toString().toInt().toDouble() % number2.text.toString().toInt().toDouble() != 0.0)) {
                number1.text = generaNumeros().toString()
                number2.text = generaNumeros().toString()
            }
            symbol.text = list[3]
            resultado = number1.text.toString().toInt() / number2.text.toString().toInt()
        }

        //En principio son aleatorios los numeros, pero podemos hacer que sean parecidos
        val numAux = (1..3).random()
        if(numAux == 1){
            resultado1.text = resultado.toString()
            resultado2.text = generaNumeros().toString()
            resultado3.text = generaNumeros().toString()
        } else if(numAux == 2){
                resultado1.text = generaNumeros().toString()
                resultado2.text = resultado.toString()
                resultado3.text = generaNumeros().toString()
            }
            else if (numAux == 3){
                resultado1.text = generaNumeros().toString()
                resultado2.text = generaNumeros().toString()
                resultado3.text = resultado.toString()
            }
        resultado1.setOnClickListener{
            if(resultado1.text == resultado.toString()) {
                correct = true
                incorrectAnimation.visibility = View.GONE
                correctAnimation.visibility = View.VISIBLE
                correctAnimation.playAnimation()
                puntuacion++
                puntuacionGanada++
                puntosGanados++
                guardaPuntuacion()
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                puntosGanados++
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            }
        }

        resultado2.setOnClickListener{
            if(resultado2.text == resultado.toString()) {
                correct = true
                incorrectAnimation.visibility = View.GONE
                correctAnimation.visibility = View.VISIBLE
                correctAnimation.playAnimation()
                puntuacion++
                puntuacionGanada++
                puntosGanados++
                guardaPuntuacion()
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                puntosGanados++
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            }
        }

        resultado3.setOnClickListener{
            if(resultado3.text == resultado.toString()) {
                correct = true
                incorrectAnimation.visibility = View.GONE
                correctAnimation.visibility = View.VISIBLE
                correctAnimation.playAnimation()
                puntuacion++
                puntuacionGanada++
                puntosGanados++
                guardaPuntuacion()
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                puntosGanados++
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            }
        }
    }

    private fun generaNumeros(): Int {
        return (0..9).random()
    }

    private fun loadConfeti() {
        viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12))
                .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)
    }

    private fun startDialog(){
        val view = View.inflate(this, R.layout.dialog_countdown, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false);
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val textContraReloj = view.findViewById<TextView>(R.id.textContraReloj)
        textContraReloj.text = "Bienvenido al Contra Reloj Matemático,\n para empezar a jugar apriete JUGAR!"

        val btn_confirm = view.findViewById<Button>(R.id.btn_leaveCountDown)
        btn_confirm.setOnClickListener{
            puntuacion = dbPuntuacion
            puntuacionMaximaCountDown.text = "Puntuacion Maxima: $puntuacion"
            time_in_milli_seconds = START_MILLI_SECONDS
            startTimer(time_in_milli_seconds)
            level()
            resultado1.isClickable = true
            resultado2.isClickable = true
            resultado3.isClickable = true
            dialog.dismiss()
        }
    }

    private fun finishDialog(){

        if (!(this@countDown as Activity).isFinishing) {
            try {
                Handler(Looper.getMainLooper()).postDelayed({
                    val view = View.inflate(this, R.layout.dialog_countdown_finish, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    builder.setCancelable(false);
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                    val textContraReloj = view.findViewById<TextView>(R.id.textContraReloj)
                    textContraReloj.text = "Ha finalizado el reloj, has ganado $puntuacionGanada puntos.\nEn total tienes: $puntuacion\n¿Quieres volver a jugar?"

                    //JUGAR!
                    val btn_playCountDown = view.findViewById<Button>(R.id.btn_playCountDown)
                    btn_playCountDown.setOnClickListener{
                        puntuacion = dbPuntuacion
                        puntuacionMaximaCountDown.text = "Puntuacion Maxima: $puntuacion"
                        time_in_milli_seconds = START_MILLI_SECONDS
                        startTimer(time_in_milli_seconds)
                        level()
                        resultado1.isClickable = true
                        resultado2.isClickable = true
                        resultado3.isClickable = true
                        dialog.dismiss()
                    }
                    //Salir
                    val btn_leaveCountDown = view.findViewById<Button>(R.id.btn_leaveCountDown)
                    btn_leaveCountDown.setOnClickListener{
                        finish()
                        dialog.dismiss()
                    }
                }, 2500)
            } catch (e: BadTokenException) {
                Log.e("WindowManagerBad ", e.toString())
            }
        }
    }
}