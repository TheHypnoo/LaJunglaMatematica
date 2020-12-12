package com.sergigonzalez.lajunglamatematica

import android.graphics.Color
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
    private lateinit var Jugadores: ArrayList<String>
    private lateinit var yo: String
    private lateinit var Puntuacion: ArrayList<Int>
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
        Handler(Looper.getMainLooper()).postDelayed({
            LayoutCarga.visibility = View.GONE
            LayoutRanking.visibility = View.VISIBLE
            ordenaMejor()
            escribe()
        },2000)
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
                yo = document.data["Nombre de Usuario"].toString()
                Jugadores.add(document.data["Nombre de Usuario"].toString()).toString()
                Puntuacion.add(document.data["puntuacion"].toString().toLong().toInt()).toString()

            }
        }.addOnFailureListener { exception ->
                Log.w("DB: Users", "Error al obtener la información: ", exception)
            }


        //Este los demas
        db.collection("users").whereNotEqualTo("Email", email).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("DB: Users", "${document.id} => ${document.data}")
                Jugadores.add(document.data["Nombre de Usuario"].toString()).toString()
                Puntuacion.add(document.data["puntuacion"].toString().toLong().toInt()).toString()

            }
        }.addOnFailureListener { exception ->
                Log.w("DB: Users", "Error al obtener la información: ", exception)
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
        when {
            Jugadores.size == 1 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 2 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 3 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 4 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 5 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }

            }
            Jugadores.size == 6 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top6.text = Jugadores[puntuacion]
                    top6.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top6.text = Jugadores[puntuacion]
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 7 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top6.text = Jugadores[puntuacion]
                    top6.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top6.text = Jugadores[puntuacion]
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top7.text = Jugadores[puntuacion]
                    top7.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top7.text = Jugadores[puntuacion]
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 8 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top6.text = Jugadores[puntuacion]
                    top6.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top6.text = Jugadores[puntuacion]
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top7.text = Jugadores[puntuacion]
                    top7.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top7.text = Jugadores[puntuacion]
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top8.text = Jugadores[puntuacion]
                    top8.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top8.text = Jugadores[puntuacion]
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size == 9 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top6.text = Jugadores[puntuacion]
                    top6.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top6.text = Jugadores[puntuacion]
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top7.text = Jugadores[puntuacion]
                    top7.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top7.text = Jugadores[puntuacion]
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top8.text = Jugadores[puntuacion]
                    top8.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top8.text = Jugadores[puntuacion]
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top9.text = Jugadores[puntuacion]
                    top8.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop9.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top9.text = Jugadores[puntuacion]
                    puntuaTop9.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
            }
            Jugadores.size >= 10 -> {
                if(Jugadores[puntuacion] == yo) {
                    top1.text = Jugadores[puntuacion]
                    top1.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top1.text = Jugadores[puntuacion]
                    puntuaTop1.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top2.text = Jugadores[puntuacion]
                    top2.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top2.text = Jugadores[puntuacion]
                    puntuaTop2.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top3.text = Jugadores[puntuacion]
                    top3.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top3.text = Jugadores[puntuacion]
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top4.text = Jugadores[puntuacion]
                    top4.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop3.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top4.text = Jugadores[puntuacion]
                    puntuaTop4.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top5.text = Jugadores[puntuacion]
                    top5.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top5.text = Jugadores[puntuacion]
                    puntuaTop5.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top6.text = Jugadores[puntuacion]
                    top6.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top6.text = Jugadores[puntuacion]
                    puntuaTop6.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top7.text = Jugadores[puntuacion]
                    top7.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top7.text = Jugadores[puntuacion]
                    puntuaTop7.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top8.text = Jugadores[puntuacion]
                    top8.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top8.text = Jugadores[puntuacion]
                    puntuaTop8.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top9.text = Jugadores[puntuacion]
                    top8.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop9.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                } else {
                    top9.text = Jugadores[puntuacion]
                    puntuaTop9.text = Puntuacion[puntuacion].toString()
                    puntuacion++
                }
                if(Jugadores[puntuacion] == yo) {
                    top10.text = Jugadores[puntuacion]
                    top10.setTextColor(Color.parseColor("#FF8D33"))
                    puntuaTop10.text = Puntuacion[puntuacion].toString()
                } else {
                    top10.text = Jugadores[puntuacion]
                    puntuaTop10.text = Puntuacion[puntuacion].toString()
                }
            }
        }
    }
}