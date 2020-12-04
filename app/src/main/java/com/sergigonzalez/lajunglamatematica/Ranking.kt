package com.sergigonzalez.lajunglamatematica

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
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
    private var puntuacion = 0
    private lateinit var top1: TextView
    private lateinit var puntuaTop1: TextView
    private lateinit var top2: TextView
    private lateinit var puntuaTop2: TextView
    private lateinit var top3: TextView
    private lateinit var puntuaTop3: TextView
    private lateinit var top4: TextView
    private lateinit var puntuaTop4: TextView
    private lateinit var top5: TextView
    private lateinit var puntuaTop5: TextView
    private lateinit var top6: TextView
    private lateinit var puntuaTop6: TextView
    private lateinit var top7: TextView
    private lateinit var puntuaTop7: TextView
    private lateinit var top8: TextView
    private lateinit var puntuaTop8: TextView
    private lateinit var top9: TextView
    private lateinit var puntuaTop9: TextView
    private lateinit var top10: TextView
    private lateinit var puntuaTop10: TextView
    private lateinit var animationCargaRanking: LottieAnimationView
    private lateinit var cargaRanking: TextView
    private lateinit var LayoutCarga: LinearLayout
    private lateinit var LayoutRanking: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ranking)
        buscaPuntuacion()
        FindID()
        animationCargaRanking.speed = 4.50F
        Handler(Looper.getMainLooper()).postDelayed({
            LayoutCarga.visibility = View.GONE
            LayoutRanking.visibility = View.VISIBLE
            ordenaMejor()
            escribe()
        },2500)
    }

    private fun FindID(){
        LayoutCarga = findViewById(R.id.LayoutCarga)
        LayoutRanking = findViewById(R.id.LayoutRanking)
        animationCargaRanking = findViewById(R.id.animationCargaRanking)
        cargaRanking = findViewById(R.id.cargaRanking)
        top1 = findViewById(R.id.top1)
        puntuaTop1 = findViewById(R.id.puntuaTop1)
        top2 = findViewById(R.id.top2)
        puntuaTop2 = findViewById(R.id.puntuaTop2)
        top3 = findViewById(R.id.top3)
        puntuaTop3 = findViewById(R.id.puntuaTop3)
        top4 = findViewById(R.id.top4)
        puntuaTop4 = findViewById(R.id.puntuaTop4)
        top5 = findViewById(R.id.top5)
        puntuaTop5 = findViewById(R.id.puntuaTop5)
        top6 = findViewById(R.id.top6)
        puntuaTop6 = findViewById(R.id.puntuaTop6)
        top7 = findViewById(R.id.top7)
        puntuaTop7 = findViewById(R.id.puntuaTop7)
        top8 = findViewById(R.id.top8)
        puntuaTop8 = findViewById(R.id.puntuaTop8)
        top9 = findViewById(R.id.top9)
        puntuaTop9 = findViewById(R.id.puntuaTop9)
        top10 = findViewById(R.id.top10)
        puntuaTop10 = findViewById(R.id.puntuaTop10)
    }

    private fun buscaPuntuacion() {
        Jugadores = ArrayList()
        Puntuacion = ArrayList()
        //Este eres tu
        db.collection("users").whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("DB: Users", "${document.id} => ${document.data}")
                id = document.id
                Jugadores.add(x,document.data["Nombre de Usuario"].toString()).toString()
                Puntuacion.add(x,document.data["puntuacion"].toString().toLong().toInt()).toString()
                x++
            }
        }.addOnFailureListener { exception ->
                Log.w("TAG", "Error al obtener la información: ", exception)
            }


        //Este los demas
        db.collection("users").whereNotEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("DB: Users", "${document.id} => ${document.data}")
                id = document.id
                Jugadores.add(x,document.data["Nombre de Usuario"].toString()).toString()
                Puntuacion.add(x,document.data["puntuacion"].toString().toLong().toInt()).toString()
                x++
            }
        }.addOnFailureListener { exception ->
                Log.w("TAG", "Error al obtener la información: ", exception)
            }
    }

    private fun ordenaMejor(){
        var tmp: Int
        var tmp2: String
        for(x in 0 until Puntuacion.size) {
            for(y in 0 until Puntuacion.size) {
                if(Puntuacion[x] > Puntuacion[y]) {
                    tmp = Puntuacion[x]
                    tmp2 = Jugadores[x]
                    Jugadores[x] = Jugadores[y]
                    Jugadores[y] = tmp2
                    Puntuacion[x] = Puntuacion[y]
                    Puntuacion[y] = tmp
                }
            }
        }
    }

    private fun escribe(){
        if(Jugadores.size == 1) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 2) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 3) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
        }else if(Jugadores.size == 4) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 5) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top5.text = Jugadores[puntuacion]
            puntuaTop5.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 6) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top5.text = Jugadores[puntuacion]
            puntuaTop5.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top6.text = Jugadores[puntuacion]
            puntuaTop6.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 7) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top5.text = Jugadores[puntuacion]
            puntuaTop5.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top6.text = Jugadores[puntuacion]
            puntuaTop6.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top7.text = Jugadores[puntuacion]
            puntuaTop7.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 8) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top5.text = Jugadores[puntuacion]
            puntuaTop5.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top6.text = Jugadores[puntuacion]
            puntuaTop6.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top7.text = Jugadores[puntuacion]
            puntuaTop7.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top8.text = Jugadores[puntuacion]
            puntuaTop8.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 9) {
            top1.text = Jugadores[puntuacion]
            puntuaTop1.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top2.text = Jugadores[puntuacion]
            puntuaTop2.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top3.text = Jugadores[puntuacion]
            puntuaTop3.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top4.text = Jugadores[puntuacion]
            puntuaTop4.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top5.text = Jugadores[puntuacion]
            puntuaTop5.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top6.text = Jugadores[puntuacion]
            puntuaTop6.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top7.text = Jugadores[puntuacion]
            puntuaTop7.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top8.text = Jugadores[puntuacion]
            puntuaTop8.text = Puntuacion[puntuacion].toString()
            puntuacion++
            top9.text = Jugadores[puntuacion]
            puntuaTop9.text = Puntuacion[puntuacion].toString()
            puntuacion++
        } else if(Jugadores.size == 10) {
            top10.text = Jugadores[puntuacion]
            puntuaTop10.text = Puntuacion[puntuacion].toString()
        }
    }
}