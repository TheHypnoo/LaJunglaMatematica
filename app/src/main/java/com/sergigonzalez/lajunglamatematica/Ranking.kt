package com.sergigonzalez.lajunglamatematica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Ranking : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var id = ""
    private lateinit var Jugadores: ArrayList<String>
    private lateinit var Puntuacion: ArrayList<Int>
    private var x = 0
    private lateinit var top1: TextView
    private lateinit var puntuaTop1: TextView
    private lateinit var top2: TextView
    private lateinit var puntuaTop2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ranking)
        buscaPuntuacion()
        FindID()
        Handler(Looper.getMainLooper()).postDelayed({
            ordenaMejor()
        },2000)
    }

    private fun FindID(){
        top1 = findViewById(R.id.top1)
        puntuaTop1 = findViewById(R.id.puntuaTop1)
        top2 = findViewById(R.id.top2)
        puntuaTop2 = findViewById(R.id.puntuaTop2)
    }

    private fun buscaPuntuacion() {
        Jugadores = ArrayList<String>()
        Puntuacion = ArrayList<Int>()
        //Este eres tu
        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("TAG", "${document.id} => ${document.data}")
                id = document.id
                Jugadores.add(x,document.data["Nombre de usuario"].toString()).toString()
                Puntuacion.add(x,document.data["puntuacion"].toString().toLong().toInt()).toString()
                x++
            }
        }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }


        //Este los demas
        db.collection("users").whereNotEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("TAG", "${document.id} => ${document.data}")
                id = document.id
                Jugadores.add(x,document.data["Nombre de usuario"].toString()).toString()
                Puntuacion.add(x,document.data["puntuacion"].toString().toLong().toInt()).toString()
                x++
            }
        }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    private fun ordenaMejor(){
        var tmp: Int
        for(x in 0 until Puntuacion.size) {
            for(y in 0 until Puntuacion.size) {
                if(Puntuacion[x] > Puntuacion[y]) {
                    tmp = Puntuacion[x]
                    Puntuacion[x] = Puntuacion[y]
                    Puntuacion[y] = tmp
                }
            }
        }
        println("\nArray ordenado: $Puntuacion")

    }

}