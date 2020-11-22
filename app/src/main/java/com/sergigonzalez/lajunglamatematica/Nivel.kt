package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_nivel.*

class Nivel : AppCompatActivity() {
    private lateinit var queHacer: TextView
    private lateinit var enunciadoNivel: TextView
    private lateinit var Correcto: LottieAnimationView
    private lateinit var Incorrecto: LottieAnimationView
    private lateinit var ResultadoEditText: EditText
    private lateinit var bt_corregir: Button
    var numero1: Int = 0
    var numero2: Int = 0
    var Resultado: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nivel)
        FindID()
        Nivel1Suma()
    }

    private fun FindID(){
        queHacer = findViewById(R.id.queHacer)
        enunciadoNivel = findViewById(R.id.enunciadoNivel)
        Correcto = findViewById(R.id.Correcto)
        Incorrecto = findViewById(R.id.Incorrecto)
        ResultadoEditText = findViewById(R.id.ResultadoEditText)
        bt_corregir = findViewById(R.id.bt_corregir)
    }

    @SuppressLint("SetTextI18n")
    private fun Nivel1Suma(){
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        queHacer.text = "Suma los dos numeros y escribe el resultado abajo."
        enunciadoNivel.text =
                " ¡Hola amigo! Necesito tu ayuda." +
                " Mi mama me ha pedido que vaya a comprar fruta al mercado " +
                "y me ha pedido que compre " +numero1+ " manzanas  y " +numero2+ " peras. " +
                "¿Cuantas piezas de fruta  tengo que comprar en total?"

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
                Nivel2Suma()
                Incorrecto.visibility = View.GONE
                Correcto.visibility = View.GONE
                bt_corregir.visibility = View.VISIBLE
                ResultadoEditText.text.clear()
            }, 3000)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun Nivel2Suma(){
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        queHacer.text = "Suma los dos numeros y escribe el resultado abajo."
        enunciadoNivel.text =
                "A cambio de comprarle la fruta, mi mama me dio "+
                        numero1+ " euros así que los fui a meter en mi hucha. A más, yo ya tenía en mi hucha " +
        numero2+ " euros. ¿Cuánto dinero tengo ahora en la hucha?"

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
                val mainIntent = Intent(this, menuPrincipal::class.java)
                startActivity(mainIntent)
                finish()
            }, 4000)

        }
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

}