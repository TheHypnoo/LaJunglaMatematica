package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.common.base.Strings
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*
import kotlin.random.Random.Default.nextInt

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
    private var isRunning: Boolean = false;
    private var list = arrayOf("+","-","*","/")
    var time_in_milli_seconds = 0L
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
        countdown_timer = object : CountDownTimer(time_in_seconds,10) {
            override fun onFinish() {
                loadConfeti()
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

        timer.text = "$minute:$seconds"
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
                println("correcto1")
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                println("Incorrecto1")
            }
        }

        resultado2.setOnClickListener{
            if(resultado2.text == resultado.toString()) {
                println("correcto2")
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                println("Incorrecto2")
            }
        }

        resultado3.setOnClickListener{
            if(resultado3.text == resultado.toString()) {
                println("correcto3")
                time_in_milli_seconds += 3000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                level()
            } else {
                time_in_milli_seconds -= 2000L
                countdown_timer.cancel()
                startTimer(time_in_milli_seconds)
                println("Incorrecto3")
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

        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        btn_confirm.setOnClickListener{
            time_in_milli_seconds = START_MILLI_SECONDS
            startTimer(time_in_milli_seconds)
            level()
            dialog.dismiss()
        }
    }
}