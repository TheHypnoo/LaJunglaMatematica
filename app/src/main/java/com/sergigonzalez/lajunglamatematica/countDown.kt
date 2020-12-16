package com.sergigonzalez.lajunglamatematica

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


class countDown : AppCompatActivity() {
    var START_MILLI_SECONDS = 16000L
    private lateinit var timer: TextView
    private lateinit var number1: TextView
    private lateinit var number2: TextView
    private lateinit var symbol: TextView
    private lateinit var resultado1: Button
    private lateinit var resultado2: Button
    private lateinit var resultado3: Button
    private lateinit var viewKonfetti: KonfettiView
    private lateinit var countdown_timer: CountDownTimer
    private lateinit var correctAnimation: LottieAnimationView
    private lateinit var incorrectAnimation: LottieAnimationView
    private var isRunning: Boolean = false;
    private var list = arrayOf("+","-","*","/")
    var time_in_milli_seconds = 0L
    var correct = true
    private var resultado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_count_down)
        findID()
        dialog()
    }

    private fun findID(){
        timer = findViewById(R.id.timer)
        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        symbol = findViewById(R.id.symbol)
        correctAnimation = findViewById(R.id.correct)
        incorrectAnimation = findViewById(R.id.incorrect)
        resultado1 = findViewById(R.id.resultado1)
        resultado2 = findViewById(R.id.resultado2)
        resultado3 = findViewById(R.id.resultado3)
        viewKonfetti = findViewById(R.id.viewKonfetti)
    }

    private fun pauseTimer() {

        countdown_timer.cancel()
        isRunning = false
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds,750) {
            override fun onFinish() {
                timer.text = "0:0"
                loadConfeti()
                resultado1.isClickable = false
                resultado2.isClickable = false
                resultado3.isClickable = false
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.GONE
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()

        isRunning = true

    }

    private fun resetTimer() {
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTextUI()
    }

    private fun updateTextUI() {
        val minute = (time_in_milli_seconds / 1000) / 60
        val seconds = (time_in_milli_seconds / 1000) % 60
        println("Seconds: $seconds")
        if(seconds <= 10L) {
            timer.setTextColor(Color.RED)
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        } else if(seconds <= 20){
            timer.setTextColor(Color.YELLOW)
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        } else {
            timer.setTextColor(Color.GREEN)
            if(!correct) {
                timer.setTextColor(Color.RED)
            }
        }
        timer.text = "$minute:$seconds"
        correct = true
    }

    private fun level(){
        number1.text = generaNumeros().toString()
        number2.text = generaNumeros().toString()
        symbol.text = list[0]
        resultado = number1.text.toString().toInt() + number2.text.toString().toInt()
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
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
            }
        }

        resultado2.setOnClickListener{
            if(resultado2.text == resultado.toString()) {
                correct = true
                incorrectAnimation.visibility = View.GONE
                correctAnimation.visibility = View.VISIBLE
                correctAnimation.playAnimation()
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
            }
        }

        resultado3.setOnClickListener{
            if(resultado3.text == resultado.toString()) {
                correct = true
                incorrectAnimation.visibility = View.GONE
                correctAnimation.visibility = View.VISIBLE
                correctAnimation.playAnimation()
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                correct = false
                correctAnimation.visibility = View.GONE
                incorrectAnimation.visibility = View.VISIBLE
                incorrectAnimation.playAnimation()
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
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

    private fun dialog(){
        val view = View.inflate(this, R.layout.dialog_countdown, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val textContraReloj = view.findViewById<TextView>(R.id.textContraReloj)
        textContraReloj.text = "Bienvenido al Contra Reloj Matematico,\n para empezar a jugar apriete JUGAR!"

        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        btn_confirm.setOnClickListener{
            time_in_milli_seconds = START_MILLI_SECONDS
            startTimer(time_in_milli_seconds)
            level()
            resultado1.isClickable = true
            resultado2.isClickable = true
            resultado3.isClickable = true
            dialog.dismiss()
        }
    }
}