package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.main.activity_nivel.*

class Pruebate : AppCompatActivity() {
    //Titulo y Definicion
    private lateinit var TituloTextView: TextView
    private lateinit var DefinicionTextView: TextView
    //Numeros y simbolos
    private lateinit var Number1TextView: TextView
    private lateinit var SignoTextView: TextView
    private lateinit var Number2TextView: TextView
    private lateinit var Number3TextView: TextView
    private lateinit var Signo1TextView: TextView
    private lateinit var Number4TextView: TextView
    private lateinit var Number5TextView: TextView
    private lateinit var Signo2TextView: TextView
    private lateinit var Number6TextView: TextView
    private lateinit var Number7TextView: TextView
    private lateinit var Signo3TextView: TextView
    private lateinit var Number8TextView: TextView
    private lateinit var Number9TextView: TextView
    private lateinit var Signo4TextView: TextView
    private lateinit var Number10TextView: TextView
    private lateinit var Number11TextView: TextView
    private lateinit var Signo5TextView: TextView
    private lateinit var Number12TextView: TextView
    private lateinit var Number13TextView: TextView
    private lateinit var Signo6TextView: TextView
    private lateinit var Number14TextView: TextView
    private lateinit var Number15TextView: TextView
    private lateinit var Signo7TextView: TextView
    private lateinit var Number16TextView: TextView
    private lateinit var Number17TextView: TextView
    private lateinit var Signo8TextView: TextView
    private lateinit var Number18TextView: TextView
    private lateinit var Number19TextView: TextView
    private lateinit var Signo9TextView: TextView
    private lateinit var Number20TextView: TextView
    //Boton
    private lateinit var BT_Corregir: Button
    private lateinit var pasaNivelTextView: TextView
    private lateinit var AnimacionNext: LottieAnimationView
    //Resultados
    private lateinit var Resultado1: EditText
    private lateinit var Resultado2: EditText
    private lateinit var Resultado3: EditText
    private lateinit var Resultado4: EditText
    private lateinit var Resultado5: EditText
    private lateinit var Resultado6: EditText
    private lateinit var Resultado7: EditText
    private lateinit var Resultado8: EditText
    private lateinit var Resultado9: EditText
    private lateinit var Resultado10: EditText
    //Animaciones
    //Columna1
    private lateinit var correctoResultado1: LottieAnimationView
    private lateinit var incorrectoResultado1: LottieAnimationView
    private lateinit var correctoResultado2: LottieAnimationView
    private lateinit var incorrectoResultado2: LottieAnimationView
    //Columna2
    private lateinit var correctoResultado3: LottieAnimationView
    private lateinit var incorrectoResultado3: LottieAnimationView
    private lateinit var correctoResultado4: LottieAnimationView
    private lateinit var incorrectoResultado4: LottieAnimationView
    //Columna3
    private lateinit var correctoResultado5: LottieAnimationView
    private lateinit var incorrectoResultado5: LottieAnimationView
    private lateinit var correctoResultado6: LottieAnimationView
    private lateinit var incorrectoResultado6: LottieAnimationView
    //Columna4
    private lateinit var correctoResultado7: LottieAnimationView
    private lateinit var incorrectoResultado7: LottieAnimationView
    private lateinit var correctoResultado8: LottieAnimationView
    private lateinit var incorrectoResultado8: LottieAnimationView
    //Columna5
    private lateinit var correctoResultado9: LottieAnimationView
    private lateinit var incorrectoResultado9: LottieAnimationView
    private lateinit var correctoResultado10: LottieAnimationView
    private lateinit var incorrectoResultado10: LottieAnimationView

    private lateinit var loadingAnimation: LottieAnimationView

    //Linear Layout
    private lateinit var CargaPruebate: LinearLayout
    private lateinit var Pruebate: LinearLayout

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var id = ""
    private var pruebateSuma = false
    private var pruebateResta = false
    private var pruebateMultiplica = false
    private var pruebateDivision = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pruebate)
        val Signos = arrayOf("+","-","x","/")
        FindID()
        loadingAnimation.speed = 4.50F
        buscaPruebate()
        CargaPruebate.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
        CargaPruebate.visibility = View.GONE
        Pruebate.visibility = View.VISIBLE

        when {
            !pruebateSuma -> {
                Suma(Signos)
            }
            !pruebateResta -> {
                Resta(Signos)
            }
            !pruebateMultiplica -> {
                Multiplica(Signos)
            }
            !pruebateDivision -> {
                Division(Signos)
            }
        }

    },2500)
    }

    private fun buscaPruebate(){
        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("TAG", "${document.id} => ${document.data}")
                id = document.id
                pruebateSuma = document.data["pruebateSuma"] as Boolean
                pruebateResta = document.data["pruebateResta"] as Boolean
                pruebateMultiplica = document.data["pruebateMultiplica"] as Boolean
                pruebateDivision = document.data["pruebateDivision"] as Boolean
            }
        }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    private fun guardaPruebate(){

        db.collection("users").document(id).update("pruebateSuma", pruebateSuma)
        db.collection("users").document(id).update("pruebateResta", pruebateResta)
        db.collection("users").document(id).update("pruebateMultiplica", pruebateMultiplica)
        db.collection("users").document(id).update("pruebateDivision", pruebateDivision)

    }


    @SuppressLint("CutPasteId")
    private fun FindID(){
        //Titulo,Definicion
        TituloTextView = findViewById(R.id.TituloTextView)
        DefinicionTextView = findViewById(R.id.DefinicionTextView)
        //Fila1 Columna1
        Number1TextView = findViewById(R.id.Number1TextView)
        SignoTextView = findViewById(R.id.SignoTextView)
        Number2TextView = findViewById(R.id.Number2TextView)
        //Fila2 Columna1
        Number3TextView = findViewById(R.id.Number3TextView)
        Signo1TextView = findViewById(R.id.Signo1TextView)
        Number4TextView = findViewById(R.id.Number4TextView)
        //Fila1 Columna2
        Number5TextView = findViewById(R.id.Number5TextView)
        Signo2TextView = findViewById(R.id.Signo2TextView)
        Number6TextView = findViewById(R.id.Number6TextView)
        //Fila2 Columna2
        Number7TextView = findViewById(R.id.Number7TextView)
        Signo3TextView = findViewById(R.id.Signo3TextView)
        Number8TextView = findViewById(R.id.Number8TextView)
        //Fila1 Columna3
        Number9TextView = findViewById(R.id.Number9TextView)
        Signo4TextView = findViewById(R.id.Signo4TextView)
        Number10TextView = findViewById(R.id.Number10TextView)
        //Fila2 Columna3
        Number11TextView = findViewById(R.id.Number11TextView)
        Signo5TextView = findViewById(R.id.Signo5TextView)
        Number12TextView = findViewById(R.id.Number12TextView)
        //Fila1 Columna4
        Number13TextView = findViewById(R.id.Number13TextView)
        Signo6TextView = findViewById(R.id.Signo6TextView)
        Number14TextView = findViewById(R.id.Number14TextView)
        //Fila2 Columna4
        Number15TextView = findViewById(R.id.Number15TextView)
        Signo7TextView = findViewById(R.id.Signo7TextView)
        Number16TextView = findViewById(R.id.Number16TextView)
        //Fila1 Columna5
        Number17TextView = findViewById(R.id.Number17TextView)
        Signo8TextView = findViewById(R.id.Signo8TextView)
        Number18TextView = findViewById(R.id.Number18TextView)
        //Fila2 Columna5
        Number19TextView = findViewById(R.id.Number19TextView)
        Signo9TextView = findViewById(R.id.Signo9TextView)
        Number20TextView = findViewById(R.id.Number20TextView)
        //Bottom
        BT_Corregir = findViewById(R.id.BT_Corregir)
        pasaNivelTextView = findViewById(R.id.cargaNivel)
        AnimacionNext = findViewById(R.id.AnimalAnimation)
        //Resultado
        Resultado1 = findViewById(R.id.Resultado1)
        Resultado2 = findViewById(R.id.Resultado2)
        Resultado3 = findViewById(R.id.Resultado3)
        Resultado4 = findViewById(R.id.Resultado4)
        Resultado5 = findViewById(R.id.Resultado5)
        Resultado6 = findViewById(R.id.Resultado6)
        Resultado7 = findViewById(R.id.Resultado7)
        Resultado8 = findViewById(R.id.Resultado8)
        Resultado9 = findViewById(R.id.Resultado9)
        Resultado10 = findViewById(R.id.Resultado10)
        //Animaciones
        //Columna1
        correctoResultado1 = findViewById(R.id.correctoResultado1)
        incorrectoResultado1 = findViewById(R.id.IncorrectoResultado1)
        correctoResultado2 = findViewById(R.id.correctoResultado2)
        incorrectoResultado2 = findViewById(R.id.IncorrectoResultado2)
        //Columna2
        correctoResultado3 = findViewById(R.id.correctoResultado3)
        incorrectoResultado3 = findViewById(R.id.IncorrectoResultado3)
        correctoResultado4 = findViewById(R.id.correctoResultado4)
        incorrectoResultado4 = findViewById(R.id.IncorrectoResultado4)
        //Columna3
        correctoResultado5 = findViewById(R.id.correctoResultado5)
        incorrectoResultado5 = findViewById(R.id.IncorrectoResultado5)
        correctoResultado6 = findViewById(R.id.correctoResultado6)
        incorrectoResultado6 = findViewById(R.id.IncorrectoResultado6)
        //Columna4
        correctoResultado7 = findViewById(R.id.correctoResultado7)
        incorrectoResultado7 = findViewById(R.id.IncorrectoResultado7)
        correctoResultado8 = findViewById(R.id.correctoResultado8)
        incorrectoResultado8 = findViewById(R.id.IncorrectoResultado8)
        //Columna5
        correctoResultado9 = findViewById(R.id.correctoResultado9)
        incorrectoResultado9 = findViewById(R.id.IncorrectoResultado9)
        correctoResultado10 = findViewById(R.id.correctoResultado10)
        incorrectoResultado10 = findViewById(R.id.IncorrectoResultado10)
        loadingAnimation = findViewById(R.id.loadingAnimation)
        //Linear Layout
        CargaPruebate = findViewById(R.id.CargaPruebate)
        Pruebate = findViewById(R.id.Pruebate)
    }

    @SuppressLint("SetTextI18n")
    private fun Suma (Signos:Array<String>) {

        if(!pruebateSuma) {

            TituloTextView.text = "¿Qué és la suma?"
            DefinicionTextView.text = "Consiste en añadir dos números o más para obtener una cantidad total."

            //Fila1 Columna1
            Number1TextView.text = generaNumeros().toString()
            SignoTextView.text = Signos[0]
            Number2TextView.text = generaNumeros().toString()
            //Fila2 Columna1
            Number3TextView.text = generaNumeros().toString()
            Signo1TextView.text = Signos[0]
            Number4TextView.text = generaNumeros().toString()
            //Fila1 Columna2
            Number5TextView.text = generaNumeros().toString()
            Signo2TextView.text = Signos[0]
            Number6TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number7TextView.text = generaNumeros().toString()
            Signo3TextView.text = Signos[0]
            Number8TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number9TextView.text = generaNumeros().toString()
            Signo4TextView.text = Signos[0]
            Number10TextView.text = generaNumeros().toString()
            //Fila1 Columna3
            Number11TextView.text = generaNumeros().toString()
            Signo5TextView.text = Signos[0]
            Number12TextView.text = generaNumeros().toString()
            //Fila2 Columna3
            Number13TextView.text = generaNumeros().toString()
            Signo6TextView.text = Signos[0]
            Number14TextView.text = generaNumeros().toString()
            //Fila1 Columna4
            Number15TextView.text = generaNumeros().toString()
            Signo7TextView.text = Signos[0]
            Number16TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number17TextView.text = generaNumeros().toString()
            Signo8TextView.text = Signos[0]
            Number18TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number19TextView.text = generaNumeros().toString()
            Signo9TextView.text = Signos[0]
            Number20TextView.text = generaNumeros().toString()

            //Botton
            BT_Corregir.setOnClickListener {
                pruebateSuma = true
                SumaResultado()
                BT_Corregir.visibility = View.GONE
                pasaNivelTextView.text = "Pasando al nivel.."
                pasaNivelTextView.visibility = View.VISIBLE
                AnimacionNext.visibility = View.VISIBLE
                AnimalAnimation.speed = 2.50F
                Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this, Nivel::class.java)
                    startActivity(mainIntent)
                    finish()
                }, 4000)

            }
        }
    }

    private fun SumaResultado(){
        val Number1 = Number1TextView.text.toString().toInt()
        val Number2 = Number2TextView.text.toString().toInt()
        val ResultadoSuma1 = Number1 + Number2

        val Number3 = Number3TextView.text.toString().toInt()
        val Number4 = Number4TextView.text.toString().toInt()
        val ResultadoSuma2 = Number3 + Number4

        val Number5 = Number5TextView.text.toString().toInt()
        val Number6 = Number6TextView.text.toString().toInt()
        val ResultadoSuma3 = Number5 + Number6

        val Number7 = Number7TextView.text.toString().toInt()
        val Number8 = Number8TextView.text.toString().toInt()
        val ResultadoSuma4 = Number7 + Number8

        val Number9 = Number9TextView.text.toString().toInt()
        val Number10 = Number10TextView.text.toString().toInt()
        val ResultadoSuma5 = Number9 + Number10

        val Number11 = Number11TextView.text.toString().toInt()
        val Number12 = Number12TextView.text.toString().toInt()
        val ResultadoSuma6 = Number11 + Number12

        val Number13 = Number13TextView.text.toString().toInt()
        val Number14 = Number14TextView.text.toString().toInt()
        val ResultadoSuma7 = Number13 + Number14

        val Number15 = Number15TextView.text.toString().toInt()
        val Number16 = Number16TextView.text.toString().toInt()
        val ResultadoSuma8 = Number15 + Number16

        val Number17 = Number17TextView.text.toString().toInt()
        val Number18 = Number18TextView.text.toString().toInt()
        val ResultadoSuma9 = Number17 + Number18

        val Number19 = Number19TextView.text.toString().toInt()
        val Number20 = Number20TextView.text.toString().toInt()
        val ResultadoSuma10 = Number19 + Number20

        val ArrResultados = arrayOf(ResultadoSuma1,ResultadoSuma2,ResultadoSuma3,ResultadoSuma4,ResultadoSuma5,ResultadoSuma6,ResultadoSuma7,ResultadoSuma8,ResultadoSuma9,ResultadoSuma10)
        compruebaResultado(ArrResultados)

    }

    private fun Resta (Signos:Array<String>) {

        if(!pruebateResta) {

            TituloTextView.text = "¿Qué és la Resta?"
            DefinicionTextView.text = "Consiste en restar dos números o más para obtener una cantidad total."

            //Fila1 Columna1
            Number1TextView.text = generaNumeros().toString()
            SignoTextView.text = Signos[1]
            Number2TextView.text = generaNumeros().toString()
            //Fila2 Columna1
            Number3TextView.text = generaNumeros().toString()
            Signo1TextView.text = Signos[1]
            Number4TextView.text = generaNumeros().toString()
            //Fila1 Columna2
            Number5TextView.text = generaNumeros().toString()
            Signo2TextView.text = Signos[1]
            Number6TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number7TextView.text = generaNumeros().toString()
            Signo3TextView.text = Signos[1]
            Number8TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number9TextView.text = generaNumeros().toString()
            Signo4TextView.text = Signos[1]
            Number10TextView.text = generaNumeros().toString()
            //Fila1 Columna3
            Number11TextView.text = generaNumeros().toString()
            Signo5TextView.text = Signos[1]
            Number12TextView.text = generaNumeros().toString()
            //Fila2 Columna3
            Number13TextView.text = generaNumeros().toString()
            Signo6TextView.text = Signos[1]
            Number14TextView.text = generaNumeros().toString()
            //Fila1 Columna4
            Number15TextView.text = generaNumeros().toString()
            Signo7TextView.text = Signos[1]
            Number16TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number17TextView.text = generaNumeros().toString()
            Signo8TextView.text = Signos[1]
            Number18TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number19TextView.text = generaNumeros().toString()
            Signo9TextView.text = Signos[1]
            Number20TextView.text = generaNumeros().toString()
/*
            while(
                    (Number1TextView.toString().toInt() < Number2TextView.toString().toInt())
                    || (Number3TextView.toString().toInt() < Number4TextView.toString().toInt())
                    || (Number5TextView.toString().toInt() < Number6TextView.toString().toInt())
                    || (Number7TextView.toString().toInt() < Number8TextView.toString().toInt())
                    || (Number9TextView.toString().toInt() < Number10TextView.toString().toInt())
                    || (Number11TextView.toString().toInt() < Number12TextView.toString().toInt())
                    || (Number13TextView.toString().toInt() < Number14TextView.toString().toInt())
                    || (Number15TextView.toString().toInt() < Number16TextView.toString().toInt())
                    || (Number17TextView.toString().toInt() < Number18TextView.toString().toInt())
                    || (Number19TextView.toString().toInt() < Number20TextView.toString().toInt())
            ) {
                Number1TextView.text = generaNumeros().toString()
                Number3TextView.text = generaNumeros().toString()
                Number5TextView.text = generaNumeros().toString()
                Number7TextView.text = generaNumeros().toString()
                Number9TextView.text = generaNumeros().toString()
                Number11TextView.text = generaNumeros().toString()
                Number13TextView.text = generaNumeros().toString()
                Number15TextView.text = generaNumeros().toString()
                Number17TextView.text = generaNumeros().toString()
                Number19TextView.text = generaNumeros().toString()
            }
 */

            //Botton
            BT_Corregir.setOnClickListener {
                pruebateResta = true
                RestaResultado()
                BT_Corregir.visibility = View.GONE
                pasaNivelTextView.text = "Pasando al nivel.."
                pasaNivelTextView.visibility = View.VISIBLE
                AnimacionNext.visibility = View.VISIBLE
                AnimalAnimation.speed = 2.50F
                Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this, Nivel::class.java)
                    startActivity(mainIntent)
                    finish()
                }, 4000)

            }
        }
    }

    private fun RestaResultado(){
        val Number1 = Number1TextView.text.toString().toInt()
        val Number2 = Number2TextView.text.toString().toInt()
        val ResultadoSuma1 = Number1 - Number2

        val Number3 = Number3TextView.text.toString().toInt()
        val Number4 = Number4TextView.text.toString().toInt()
        val ResultadoSuma2 = Number3 - Number4

        val Number5 = Number5TextView.text.toString().toInt()
        val Number6 = Number6TextView.text.toString().toInt()
        val ResultadoSuma3 = Number5 - Number6

        val Number7 = Number7TextView.text.toString().toInt()
        val Number8 = Number8TextView.text.toString().toInt()
        val ResultadoSuma4 = Number7 - Number8

        val Number9 = Number9TextView.text.toString().toInt()
        val Number10 = Number10TextView.text.toString().toInt()
        val ResultadoSuma5 = Number9 - Number10

        val Number11 = Number11TextView.text.toString().toInt()
        val Number12 = Number12TextView.text.toString().toInt()
        val ResultadoSuma6 = Number11 - Number12

        val Number13 = Number13TextView.text.toString().toInt()
        val Number14 = Number14TextView.text.toString().toInt()
        val ResultadoSuma7 = Number13 - Number14

        val Number15 = Number15TextView.text.toString().toInt()
        val Number16 = Number16TextView.text.toString().toInt()
        val ResultadoSuma8 = Number15 - Number16

        val Number17 = Number17TextView.text.toString().toInt()
        val Number18 = Number18TextView.text.toString().toInt()
        val ResultadoSuma9 = Number17 - Number18

        val Number19 = Number19TextView.text.toString().toInt()
        val Number20 = Number20TextView.text.toString().toInt()
        val ResultadoSuma10 = Number19 - Number20

        val ArrResultados = arrayOf(ResultadoSuma1,ResultadoSuma2,ResultadoSuma3,ResultadoSuma4,ResultadoSuma5,ResultadoSuma6,ResultadoSuma7,ResultadoSuma8,ResultadoSuma9,ResultadoSuma10)
        compruebaResultado(ArrResultados)

    }

    private fun Multiplica (Signos:Array<String>) {

        if(!pruebateMultiplica) {

            TituloTextView.text = "¿Qué és la Multiplicación?"
            DefinicionTextView.text = "Consiste en multiplicar dos números o más para obtener una cantidad total."

            //Fila1 Columna1
            Number1TextView.text = generaNumeros().toString()
            SignoTextView.text = Signos[2]
            Number2TextView.text = generaNumeros().toString()
            //Fila2 Columna1
            Number3TextView.text = generaNumeros().toString()
            Signo1TextView.text = Signos[2]
            Number4TextView.text = generaNumeros().toString()
            //Fila1 Columna2
            Number5TextView.text = generaNumeros().toString()
            Signo2TextView.text = Signos[2]
            Number6TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number7TextView.text = generaNumeros().toString()
            Signo3TextView.text = Signos[2]
            Number8TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number9TextView.text = generaNumeros().toString()
            Signo4TextView.text = Signos[2]
            Number10TextView.text = generaNumeros().toString()
            //Fila1 Columna3
            Number11TextView.text = generaNumeros().toString()
            Signo5TextView.text = Signos[2]
            Number12TextView.text = generaNumeros().toString()
            //Fila2 Columna3
            Number13TextView.text = generaNumeros().toString()
            Signo6TextView.text = Signos[2]
            Number14TextView.text = generaNumeros().toString()
            //Fila1 Columna4
            Number15TextView.text = generaNumeros().toString()
            Signo7TextView.text = Signos[2]
            Number16TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number17TextView.text = generaNumeros().toString()
            Signo8TextView.text = Signos[2]
            Number18TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number19TextView.text = generaNumeros().toString()
            Signo9TextView.text = Signos[2]
            Number20TextView.text = generaNumeros().toString()

            //Botton
            BT_Corregir.setOnClickListener {
                pruebateMultiplica = true
                MultiplicaResultado()
                BT_Corregir.visibility = View.GONE
                pasaNivelTextView.text = "Pasando al nivel.."
                pasaNivelTextView.visibility = View.VISIBLE
                AnimacionNext.visibility = View.VISIBLE
                AnimalAnimation.speed = 2.50F
                Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this, Nivel::class.java)
                    startActivity(mainIntent)
                    finish()
                }, 4000)

            }
        }
    }

    private fun MultiplicaResultado(){
        val Number1 = Number1TextView.text.toString().toInt()
        val Number2 = Number2TextView.text.toString().toInt()
        val Resultado1 = Number1 * Number2

        val Number3 = Number3TextView.text.toString().toInt()
        val Number4 = Number4TextView.text.toString().toInt()
        val Resultado2 = Number3 * Number4

        val Number5 = Number5TextView.text.toString().toInt()
        val Number6 = Number6TextView.text.toString().toInt()
        val Resultado3 = Number5 * Number6

        val Number7 = Number7TextView.text.toString().toInt()
        val Number8 = Number8TextView.text.toString().toInt()
        val Resultado4 = Number7 * Number8

        val Number9 = Number9TextView.text.toString().toInt()
        val Number10 = Number10TextView.text.toString().toInt()
        val Resultado5 = Number9 * Number10

        val Number11 = Number11TextView.text.toString().toInt()
        val Number12 = Number12TextView.text.toString().toInt()
        val Resultado6 = Number11 * Number12

        val Number13 = Number13TextView.text.toString().toInt()
        val Number14 = Number14TextView.text.toString().toInt()
        val Resultado8 = Number13 * Number14

        val Number15 = Number15TextView.text.toString().toInt()
        val Number16 = Number16TextView.text.toString().toInt()
        val Resultado9 = Number15 * Number16

        val Number17 = Number17TextView.text.toString().toInt()
        val Number18 = Number18TextView.text.toString().toInt()
        val Resultado10 = Number17 * Number18

        val Number19 = Number19TextView.text.toString().toInt()
        val Number20 = Number20TextView.text.toString().toInt()
        val Resultado11 = Number19 * Number20

        val ArrResultados = arrayOf(Resultado1,Resultado2,Resultado3,Resultado4,Resultado5,Resultado6,Resultado8,Resultado9,Resultado10,Resultado11)
        compruebaResultado(ArrResultados)

    }

    private fun Division (Signos:Array<String>) {

        if(!pruebateDivision) {

            TituloTextView.text = "¿Qué és la Division?"
            DefinicionTextView.text = "Consiste en dividir dos números o más para obtener una cantidad total."

            //Fila1 Columna1
            Number1TextView.text = generaNumeros().toString()
            SignoTextView.text = Signos[3]
            Number2TextView.text = generaNumeros().toString()
            //Fila2 Columna1
            Number3TextView.text = generaNumeros().toString()
            Signo1TextView.text = Signos[3]
            Number4TextView.text = generaNumeros().toString()
            //Fila1 Columna2
            Number5TextView.text = generaNumeros().toString()
            Signo2TextView.text = Signos[3]
            Number6TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number7TextView.text = generaNumeros().toString()
            Signo3TextView.text = Signos[3]
            Number8TextView.text = generaNumeros().toString()
            //Fila2 Columna2
            Number9TextView.text = generaNumeros().toString()
            Signo4TextView.text = Signos[3]
            Number10TextView.text = generaNumeros().toString()
            //Fila1 Columna3
            Number11TextView.text = generaNumeros().toString()
            Signo5TextView.text = Signos[3]
            Number12TextView.text = generaNumeros().toString()
            //Fila2 Columna3
            Number13TextView.text = generaNumeros().toString()
            Signo6TextView.text = Signos[3]
            Number14TextView.text = generaNumeros().toString()
            //Fila1 Columna4
            Number15TextView.text = generaNumeros().toString()
            Signo7TextView.text = Signos[3]
            Number16TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number17TextView.text = generaNumeros().toString()
            Signo8TextView.text = Signos[3]
            Number18TextView.text = generaNumeros().toString()
            //Fila2 Columna4
            Number19TextView.text = generaNumeros().toString()
            Signo9TextView.text = Signos[3]
            Number20TextView.text = generaNumeros().toString()
/*
            while(
                    (Number1TextView.toString().toInt() < Number2TextView.toString().toInt())
                    || (Number3TextView.toString().toInt() < Number4TextView.toString().toInt())
                    || (Number5TextView.toString().toInt() < Number6TextView.toString().toInt())
                    || (Number7TextView.toString().toInt() < Number8TextView.toString().toInt())
                    || (Number9TextView.toString().toInt() < Number10TextView.toString().toInt())
                    || (Number11TextView.toString().toInt() < Number12TextView.toString().toInt())
                    || (Number13TextView.toString().toInt() < Number14TextView.toString().toInt())
                    || (Number15TextView.toString().toInt() < Number16TextView.toString().toInt())
                    || (Number17TextView.toString().toInt() < Number18TextView.toString().toInt())
                    || (Number19TextView.toString().toInt() < Number20TextView.toString().toInt())
            ) {
                Number1TextView.text = generaNumeros().toString()
                Number3TextView.text = generaNumeros().toString()
                Number5TextView.text = generaNumeros().toString()
                Number7TextView.text = generaNumeros().toString()
                Number9TextView.text = generaNumeros().toString()
                Number11TextView.text = generaNumeros().toString()
                Number13TextView.text = generaNumeros().toString()
                Number15TextView.text = generaNumeros().toString()
                Number17TextView.text = generaNumeros().toString()
                Number19TextView.text = generaNumeros().toString()
            }
*/
            //Botton
            BT_Corregir.setOnClickListener {
                pruebateDivision = true
                DivisionResultado()
                BT_Corregir.visibility = View.GONE
                pasaNivelTextView.text = "Pasando al nivel.."
                pasaNivelTextView.visibility = View.VISIBLE
                AnimacionNext.visibility = View.VISIBLE
                AnimalAnimation.speed = 2.50F
                Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this, Nivel::class.java)
                    startActivity(mainIntent)
                    finish()
                }, 4000)

            }
        }
    }

    private fun DivisionResultado(){
        val Number1 = Number1TextView.text.toString().toInt()
        val Number2 = Number2TextView.text.toString().toInt()
        val Resultado1 = Number1 * Number2

        val Number3 = Number3TextView.text.toString().toInt()
        val Number4 = Number4TextView.text.toString().toInt()
        val Resultado2 = Number3 * Number4

        val Number5 = Number5TextView.text.toString().toInt()
        val Number6 = Number6TextView.text.toString().toInt()
        val Resultado3 = Number5 * Number6

        val Number7 = Number7TextView.text.toString().toInt()
        val Number8 = Number8TextView.text.toString().toInt()
        val Resultado4 = Number7 * Number8

        val Number9 = Number9TextView.text.toString().toInt()
        val Number10 = Number10TextView.text.toString().toInt()
        val Resultado5 = Number9 * Number10

        val Number11 = Number11TextView.text.toString().toInt()
        val Number12 = Number12TextView.text.toString().toInt()
        val Resultado6 = Number11 * Number12

        val Number13 = Number13TextView.text.toString().toInt()
        val Number14 = Number14TextView.text.toString().toInt()
        val Resultado8 = Number13 * Number14

        val Number15 = Number15TextView.text.toString().toInt()
        val Number16 = Number16TextView.text.toString().toInt()
        val Resultado9 = Number15 * Number16

        val Number17 = Number17TextView.text.toString().toInt()
        val Number18 = Number18TextView.text.toString().toInt()
        val Resultado10 = Number17 * Number18

        val Number19 = Number19TextView.text.toString().toInt()
        val Number20 = Number20TextView.text.toString().toInt()
        val Resultado11 = Number19 * Number20

        val ArrResultados = arrayOf(Resultado1,Resultado2,Resultado3,Resultado4,Resultado5,Resultado6,Resultado8,Resultado9,Resultado10,Resultado11)
        compruebaResultado(ArrResultados)

    }

    private fun compruebaResultado(ArrResultados:Array<Int>){
        //Columna 1 Fila 1
        if(Resultado1.text.toString() == ArrResultados[0].toString()) {
            correctoResultado1.visibility = View.VISIBLE
            Resultado1.visibility = View.GONE
            correctoResultado1.playAnimation()
        } else {
            incorrectoResultado1.visibility = View.VISIBLE
            Resultado1.visibility = View.GONE
            incorrectoResultado1.playAnimation()
        }
        //Columna 1 Fila 2
        if(Resultado2.text.toString() == ArrResultados[1].toString()) {
            correctoResultado2.visibility = View.VISIBLE
            Resultado2.visibility = View.GONE
            correctoResultado2.playAnimation()
        } else {
            incorrectoResultado2.visibility = View.VISIBLE
            Resultado2.visibility = View.GONE
            incorrectoResultado2.playAnimation()
        }
        //Columna 2 Fila 1
        if(Resultado3.text.toString() == ArrResultados[2].toString()) {
            correctoResultado3.visibility = View.VISIBLE
            Resultado3.visibility = View.GONE
            correctoResultado3.playAnimation()
        } else {
            incorrectoResultado3.visibility = View.VISIBLE
            Resultado3.visibility = View.GONE
            incorrectoResultado3.playAnimation()
        }
        //Columna 2 Fila 2
        if(Resultado4.text.toString() == ArrResultados[3].toString()) {
            correctoResultado4.visibility = View.VISIBLE
            Resultado4.visibility = View.GONE
            correctoResultado4.playAnimation()
        } else {
            incorrectoResultado4.visibility = View.VISIBLE
            Resultado4.visibility = View.GONE
            incorrectoResultado4.playAnimation()
        }
        //Columna 3 Fila 1
        if(Resultado5.text.toString() == ArrResultados[4].toString()) {
            correctoResultado5.visibility = View.VISIBLE
            Resultado5.visibility = View.GONE
            correctoResultado5.playAnimation()
        } else {
            incorrectoResultado5.visibility = View.VISIBLE
            Resultado5.visibility = View.GONE
            incorrectoResultado5.playAnimation()
        }
        //Columna 3 Fila 2
        if(Resultado6.text.toString() == ArrResultados[5].toString()) {
            correctoResultado6.visibility = View.VISIBLE
            Resultado6.visibility = View.GONE
            correctoResultado6.playAnimation()
        } else {
            incorrectoResultado6.visibility = View.VISIBLE
            Resultado6.visibility = View.GONE
            incorrectoResultado6.playAnimation()
        }
        //Columna 4 Fila1
        if(Resultado7.text.toString() == ArrResultados[6].toString()) {
            correctoResultado7.visibility = View.VISIBLE
            Resultado7.visibility = View.GONE
            correctoResultado7.playAnimation()
        } else {
            incorrectoResultado7.visibility = View.VISIBLE
            Resultado7.visibility = View.GONE
            incorrectoResultado7.playAnimation()
        }
        //Columna 4 Fila 2
        if(Resultado8.text.toString() == ArrResultados[7].toString()) {
            correctoResultado8.visibility = View.VISIBLE
            Resultado8.visibility = View.GONE
            correctoResultado8.playAnimation()
        } else {
            incorrectoResultado8.visibility = View.VISIBLE
            Resultado8.visibility = View.GONE
            incorrectoResultado8.playAnimation()
        }
        //Columna 5 Fila 1
        if(Resultado9.text.toString() == ArrResultados[8].toString()) {
            correctoResultado9.visibility = View.VISIBLE
            Resultado9.visibility = View.GONE
            correctoResultado9.playAnimation()
        } else {
            incorrectoResultado9.visibility = View.VISIBLE
            Resultado9.visibility = View.GONE
            incorrectoResultado9.playAnimation()
        }
        //Columna 5 Fila 2
        if(Resultado10.text.toString() == ArrResultados[9].toString()) {
            correctoResultado10.visibility = View.VISIBLE
            Resultado10.visibility = View.GONE
            correctoResultado10.playAnimation()
        } else {
            incorrectoResultado10.visibility = View.VISIBLE
            Resultado10.visibility = View.GONE
            incorrectoResultado10.playAnimation()
        }
        guardaPruebate()
    }

    private fun generaNumeros(): Int {
        return (0..9).random()
    }
}