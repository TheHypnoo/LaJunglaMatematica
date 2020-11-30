package com.sergigonzalez.lajunglamatematica

import android.annotation.SuppressLint
import android.content.Intent
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
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser
    private val email = user?.email
    private var id = ""
    private var numero1 = 0
    private var numero2 = 0
    private var Resultado = 0
    private var dbPuntuacion = 0
    private var puntuacion = 0

    private var dondeEstoy = -1
    private var pruebateSuma = false
    private var pruebateResta = false
    private var pruebateMultiplica = false
    private var pruebateDivision = false
    private var finalSuma = false
    private var finalResta = false
    private var finalMultiplica = false
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_nivel)
        findID()
        loadingAnimation.speed = 4.50F
        buscaNivel()
         Handler(Looper.getMainLooper()).postDelayed({
             loadingAnimation.visibility = View.GONE
             cargaNivel.visibility = View.GONE
             todoNivel.visibility = View.VISIBLE
             compruebaEjecuta()
         }, 2500)

    }

    override fun onStart() {
        super.onStart()
        findID()
        loadingAnimation.speed = 4.50F
        buscaNivel()
        Handler(Looper.getMainLooper()).postDelayed({
            loadingAnimation.visibility = View.GONE
            cargaNivel.visibility = View.GONE
            todoNivel.visibility = View.VISIBLE
            compruebaEjecuta()
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

        if(puntuacion >= dbPuntuacion)
            db.collection("users").document(id).update("puntuacion", puntuacion)

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
                        dbPuntuacion = document.data["puntuacion"].toString().toLong().toInt()
                        pruebateSuma = document.data["pruebateSuma"] as Boolean
                        pruebateResta = document.data["pruebateResta"] as Boolean
                        pruebateMultiplica = document.data["pruebateMultiplica"] as Boolean
                        pruebateDivision = document.data["pruebateDivision"] as Boolean
                        dondeEstoy = document.data["dondeEstoy"] .toString().toLong().toInt()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
    }

    @SuppressLint("SetTextI18n")
    private fun whenSuma(){
        //Si lo tengo guardado es más grande de lo que tengo, lo cojo.
        if(dbSuma >= lvlSuma) {
            lvlSuma = dbSuma
        }
        //Si llego al final, entonces finalizo el nivel y paso al siguiente
        if(lvlSuma >= 10) {
            finalSuma = true
            nivelResta()
            dondeEstoy = 1
            db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
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
            5 -> {
                enunciadoNivel.text = "Para cenar mi mama ha hecho mis cenas favoritas: crema de verdura y" +
                        " tortilla de patatas. Si en la mesa hay $numero1 platos de crema de verduras y $numero2 de" +
                        " tortilla de patatas. ¿En cuántos platos está repartida nuestra cena?"
            }
            6 -> {
                enunciadoNivel.text = "Después de cenar como cada noche, miramos un rato la tele en el salón." +
                        " Este es grande con $numero1 sofás y $numero2 sillas. ¿Cuántos sitios para sentarse tiene" +
                        " entonces el salón?"
            }
            7 -> {
                enunciadoNivel.text = "Ya es un nuevo día, y hoy tengo que ir a visitar a mi yaya. Mientras voy en" +
                        " coche a casa de mi yaya voy observando el paisaje y mi mama me propone" +
                        " que cuente todas las palmeras y arbustos que vea hasta llegar a su casa." +
                        " Cuando llegamos había contado $numero1 palmeras y $numero2 arbustos y mi mama me" +
                        " pregunta: “¿Cuántas plantas has visto en total?”"
            }
            8 -> {
                enunciadoNivel.text = "Como siempre que venimos a visitar a mi yaya, mi mama le ha traído algo" +
                        " para que desayune. Hoy le hemos traído $numero1 magdalenas y la yaya nos ha dicho" +
                        " que aún le quedaban $numero2 magdalenas de la última vez. ¿Cuántas magdalenas" +
                        " tiene en total ahora?"
            }
            9 -> {
                enunciadoNivel.text = "A más, hoy mi mama también le ha traído $numero1 naranjas porque le gustan" +
                        " mucho a mi yaya. ¿Cuántas piezas de fruta tiene ahora mi yaya si ya tenía $numero2" +
                        " naranjas?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelSuma(){
            numero1 = generaNumeros()
            numero2 = generaNumeros()
            whenSuma()
        //Revisar esta parte de la visibility
            if (lvlSuma >= 3) {
                lvlUP.visibility = View.VISIBLE

                lvlUP.setOnClickListener {
                    if (!pruebateResta) {
                        val mainIntent = Intent(this, Pruebate::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        nivelResta()
                    }
                    dondeEstoy = 1
                    db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
                    lvlUP.visibility = View.GONE
                }
            } else {
                lvlUP.visibility = View.GONE
            }

                queHacer.text = "Suma los dos numeros y escribe el resultado abajo."
                Resultado = numero1 + numero2

                bt_corregir.setOnClickListener {

                    if (ResultadoEditText.text.toString() == Resultado.toString()) {
                        Correcto.visibility = View.VISIBLE
                        Correcto.playAnimation()
                        ++puntuacion
                    } else {
                        Incorrecto.visibility = View.VISIBLE
                        Incorrecto.playAnimation()
                    }

                    bt_corregir.visibility = View.GONE

                    Handler(Looper.myLooper()!!).postDelayed({
                        ++lvlSuma

                        if (lvlSuma >= dbSuma || puntuacion >= dbPuntuacion) {
                            guardaNivel()
                        }

                        desmarcar()

                        if (lvlSuma >= 3) {
                            lvlUP.visibility = View.VISIBLE

                            lvlUP.setOnClickListener {
                                if (!pruebateResta) {
                                    val mainIntent = Intent(this, Pruebate::class.java)
                                    startActivity(mainIntent)
                                    finish()
                                } else {
                                    nivelResta()
                                }
                                dondeEstoy = 1
                                db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
                                lvlUP.visibility = View.GONE
                            }
                            nivelSuma()
                        } else {
                            nivelSuma()
                        }
                    }, 1500)
                }
            }

    @SuppressLint("SetTextI18n")
    private fun whenResta(){

        if(dbResta >= lvlResta) {
            lvlResta = dbResta
        }

        if(lvlResta == 10) {
            finalResta = true
            nivelMultiplica()
            dondeEstoy = 2
            db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
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
            5 -> {
                enunciadoNivel.text = "Al salir del cole vamos a comer a casa mi yaya. Mi yaya prepara $numero1 bandejas" +
                        " de canelones. Si nos comemos $numero2 bandejas. ¿Sobra alguna bandeja de" +
                        " canelones?"
            }
            6 -> {
                enunciadoNivel.text = "Cuándo nos comemos los canelones, mi yaya saca una bandeja con $numero1" +
                        "galletas. Empezamos a comer galletas hasta que estamos llenos. ¿Cuántas" +
                        "galletas nos hemos comido si en la bandeja solo quedan $numero2 galletas?"
            }
            7 -> {
                enunciadoNivel.text = "Por la tarde, acompaño a mi madre a comprar al mercado fruta. " +
                        "Allí compramos $numero1 piezas de fruta y seguramente mañana solo queden $numero2 piezas." +
                        "¿Cuántas piezas de fruta nos habremos comido?"
            }
            8 -> {
                enunciadoNivel.text = "Mientras regresamos a casa, escucho a mi madre decirle a mi padre que de" +
                        " los $numero1 € que le había dado para comprar solo le quedan $numero2. " +
                        "¿Cuánto dinero se ha gastado en fruta?"
            }
            9 -> {
                enunciadoNivel.text = "Cuando llegamos a casa, mi mama me pide que prepare la mesa para $numero1, ya" +
                        " que esa misma noche teníamos una cena con la familia, pero justo cuando" +
                        " acaba de terminar de montar la mesa me avisa mi mama que $numero2 al final no podrá asistir." +
                        " ¿Cuántas personas seremos al final?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelResta() {
        numero1 = generaNumeros()
        numero2 = generaNumeros()
        whenResta()
            if (!finalSuma) {
                lvlDown.visibility = View.VISIBLE
                lvlDown.setOnClickListener {
                    nivelSuma()
                    dondeEstoy = 0
                    db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
                    lvlDown.visibility = View.GONE
                }
            } else {
                lvlDown.visibility = View.GONE
            }
            if (finalSuma && !finalMultiplica && lvlResta >= 3) {
                lvlUP.visibility = View.VISIBLE
                lvlUP.setOnClickListener {
                    if (!pruebateMultiplica) {
                        val mainIntent = Intent(this, Pruebate::class.java)
                        startActivity(mainIntent)
                        finish()
                        dondeEstoy = 2
                        db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
                    } else {
                        nivelMultiplica()
                    }
                    lvlUP.visibility = View.GONE
                }
            } else {
                lvlUP.visibility = View.GONE
            }

            queHacer.text = "Resta los dos numeros y escribe el resultado abajo."
            Resultado = numero1 - numero2

            bt_corregir.setOnClickListener {
                if (ResultadoEditText.text.toString() == Resultado.toString()) {
                    Correcto.visibility = View.VISIBLE
                    Correcto.playAnimation()
                    ++puntuacion
                } else {
                    Incorrecto.visibility = View.VISIBLE
                    Incorrecto.playAnimation()
                }
                bt_corregir.visibility = View.GONE
                Handler(Looper.myLooper()!!).postDelayed({
                    ++lvlResta
                    if (lvlResta >= dbResta || puntuacion >= dbPuntuacion) {
                        guardaNivel()
                    }
                    desmarcar()
                    if (lvlResta >= 3 && finalSuma) {
                        lvlUP.visibility = View.VISIBLE
                        lvlUP.setOnClickListener {
                            if (!pruebateMultiplica) {
                                val mainIntent = Intent(this, Pruebate::class.java)
                                startActivity(mainIntent)
                                finish()
                                dondeEstoy = 2
                            } else {
                                dondeEstoy = 2
                                nivelMultiplica()
                            }
                            db.collection("users").document(id).update("dondeEstoy",dondeEstoy)
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
    private fun whenMultiplica(){
        if(dbMultiplica >= lvlMultiplica) {
            lvlMultiplica = dbMultiplica
        }

        if(lvlMultiplica == 10) {
            finalMultiplica = true
            nivelDivision()
            dondeEstoy = 4
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
                enunciadoNivel.text = "Después de dejar la fruta en casa, he ido a desayunar con una amiga y nos" +
                        "hemos pedido $numero1 bocadillos a $numero2€. " +
                        " ¿Cuánto cuestan los bocadillos?"
            }
            4 -> {
                enunciadoNivel.text = "A más, también nos hemos bebidos $numero1 zumos de naranja a $numero2€. " +
                        "¿Cuánto cuestan los zumos de naranja?"
            }
            5 -> {
                enunciadoNivel.text = "Y una vez hemos pagamos, le acompaño a comprar-se alguna ropa." +
                        " Sí se acaba comprando $numero1 prendas a $numero2€, ¿Cuánto dinero se gasta en ropa?"
            }
            6 -> {
                enunciadoNivel.text = "También yo me acabo comprando alguna camiseta a $numero1€ cada una." +
                        " Si he comprado $numero2, ¿Cuánto dinero me he gastado yo?"
            }
            7 -> {
                enunciadoNivel.text = "Después de pasarnos la mañana comprando ropa, decidimos ir a comer a un" +
                        "restaurante de tapas muy famoso de la ciudad. En total, me como $numero1 tapas y" +
                        "cada una me cuesta $numero2 €. ¿Cuánto dinero pago por la comida?"
            }
            8 -> {
                enunciadoNivel.text = "En cambio, mi amiga coge $numero1 tapas y cada una le cuestan $numero2€." +
                        "¿A cuánto le sale a ella la comida?"
            }
            9 -> {
                enunciadoNivel.text = "Una vez hemos pagado, la acompaño a su casa con el coche y de camino" +
                        "a casa paro en la gasolinera para rellenar el depósito. Si en total relleno $numero1 litros" +
                        "y el litro cuesta $numero2 €. ¿Cuánto pago por la gasolina del coche?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelMultiplica() {
        if (pruebateMultiplica) {
            if (!finalResta) {
                lvlDown.visibility = View.VISIBLE
                lvlDown.setOnClickListener {
                    nivelResta()
                    lvlDown.visibility = View.GONE
                }
            } else {
                lvlDown.visibility = View.GONE
            }
            if (finalResta && !finalDivision) {
                lvlUP.visibility = View.VISIBLE
                lvlUP.setOnClickListener {
                    if (!pruebateDivision) {
                        val mainIntent = Intent(this, Pruebate::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        nivelDivision()
                    }
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

            bt_corregir.setOnClickListener {
                if (ResultadoEditText.text.toString() == Resultado.toString()) {
                    Correcto.visibility = View.VISIBLE
                    Correcto.playAnimation()
                    ++puntuacion
                } else {
                    Incorrecto.visibility = View.VISIBLE
                    Incorrecto.playAnimation()
                }
                bt_corregir.visibility = View.GONE
                Handler(Looper.myLooper()!!).postDelayed({
                    ++lvlMultiplica
                    if (lvlMultiplica >= dbMultiplica || puntuacion >= dbPuntuacion) {
                        guardaNivel()
                    }
                    Incorrecto.visibility = View.GONE
                    Correcto.visibility = View.GONE
                    bt_corregir.visibility = View.VISIBLE
                    ResultadoEditText.text.clear()
                    if (lvlMultiplica >= 3 && finalResta) {
                        lvlUP.visibility = View.VISIBLE
                        lvlUP.setOnClickListener {
                            if (!pruebateDivision) {
                                val mainIntent = Intent(this, Pruebate::class.java)
                                startActivity(mainIntent)
                                finish()
                            } else {
                                nivelDivision()
                            }
                            lvlUP.visibility = View.GONE
                        }
                        nivelMultiplica()
                    } else {
                        nivelMultiplica()
                    }
                }, 1500)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun whenDivision(){
        if(dbDivision >= lvlDivision) {
            lvlDivision = dbDivision
        }

        if(lvlDivision == 10) {
            finalDivision = true
            lvlUP.visibility = View.GONE
            lvlDown.visibility = View.GONE
        }
        while(numero1 < numero2) numero1 = generaNumeros()

        when (lvlDivision) {
            0 -> {
                enunciadoNivel.text =
                        "¡Hola amigo! ¿Me puedes ayudar un momento? He quedado con mis amigos para merendar y he comprado $numero1 magdalenas. " +
                                "Si somos $numero2 amigos ¿Cuántas magdalenas nos podemos comer cada uno?"
            }
            1 -> {
                enunciadoNivel.text =
                        "Cuando nos acabamos las magdalenas un amigo saca una bolsa de" +
                                "chuches, si en total hay $numero1 chuches y repartimos $numero2 chuches a cada uno." +
                                "¿Entre cuantas personas podríamos repartir las chuches a partes iguales?"
            }
            2 -> {
                enunciadoNivel.text = "Después de pasar la tarde con mis amigos, vuelvo a casa y esa noche mi" +
                        " mama ha preparado tortilla de patatas para cenar. Si la tortilla esta cortada en $numero1" +
                        " trozos y somos $numero2 personas cenando. ¿Cuántos trozos nos podemos comer cada uno?"
            }
            3 -> {
                enunciadoNivel.text = "A parte de tortilla, mama también ha hecho una tarta de chocolate de postre." +
                        "Si la tarta esta cortada en $numero1 trozos y cada uno nos comemos $numero2 trozos." +
                        "¿Cuántas personas podrían comer de esa tarta?"
            }
            4 -> {
                enunciadoNivel.text = "A más, como es viernes noche, nos toca noche de peli y para decidir quién la" +
                        "elige mi papa siempre nos plantea un problema a mi y a mi hermana, ¿me" +
                        "ayudas a responder de manera correcta? Mira esté es el problema: “Si tengo" +
                        "$numero1 € para repartir entre $numero2 hijos. ¿Cuánto dinero le tengo que dar a cada hijo?”"
            }
            5 -> {
                enunciadoNivel.text = "Después de ver la peli, me lavo los dientes y me voy a dormir. A la mañana" +
                        "siguiente cuando me levanto, huelo las tortitas con chocolate tan ricas que" +
                        "prepara mamá. Si en total hay preparadas x tortitas y somos x personas para" +
                        "desayunar. ¿Cuántas tortitas nos podemos comer cada uno?"
            }
            6 -> {
                enunciadoNivel.text = "Una vez hemos desayunado, nos dirigimos al río dónde hacemos una" +
                        "barbacoa. Al llegar somos los primeros de la familia i, por tanto, nos toca elegir" +
                        "sitio. Si en total somos $numero1 personas y en cada mesa solo caben $numero2. " +
                        "¿Cuántas mesas debemos coger para que nadie se quede sin sitio?"
            }
            7 -> {
                enunciadoNivel.text = "Entre mi papa y yo, empezamos a hacer la barbacoa. Tenemos salchichas," +
                        "hamburguesa y panceta. Si en total hacemos $numero1 salchichas y hay $numero2 personas" +
                        "que quieren. ¿Cuántas salchichas se pueden comer cada uno?"
            }
            8 -> {
                enunciadoNivel.text = "Y si hacemos $numero1 hamburguesas y $numero2 personas quieren." +
                        " ¿Cuántas se pueden comer cada una?"
            }
            9 -> {
                enunciadoNivel.text = "Y de panceta, ¿si hay $numero1 pancetas y $numero2 personas quieren?"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nivelDivision() {
        if (pruebateDivision) {
            if (!finalMultiplica) {
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

            while(numero1 < numero2 && numero1 % numero2 != 0) {
                numero1 = generaNumeros()
                numero2 = generaNumeros()
            }

            whenDivision()
            queHacer.text = "Divide los dos numeros y escribe el resultado abajo."
            Resultado = numero1 / numero2

            bt_corregir.setOnClickListener {
                if (ResultadoEditText.text.toString() == Resultado.toString()) {
                    Correcto.visibility = View.VISIBLE
                    Correcto.playAnimation()
                    ++puntuacion
                } else {
                    Incorrecto.visibility = View.VISIBLE
                    Incorrecto.playAnimation()
                }
                bt_corregir.visibility = View.GONE
                Handler(Looper.myLooper()!!).postDelayed({
                    ++lvlDivision
                    if (lvlDivision >= dbDivision || puntuacion >= dbPuntuacion) {
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
    }

    private fun generaNumeros(): Int {
        return (1..9).random()
    }

    private fun compruebaEjecuta() {
        if(dondeEstoy < 0) {
            dondeEstoy = 0
        }

        when {
            dbSuma >= 10 -> {
                finalSuma = true
            }
            dbResta >= 10 -> {
                finalResta = true
            }
            dbMultiplica >= 10 -> {
                finalMultiplica = true
            }
            dbDivision >= 10 -> {
                finalDivision = true
            }
        }

        when (dondeEstoy){
            0 -> {
                nivelSuma()
            }
            1 -> {
                nivelResta()
            }
            2 -> {
                nivelMultiplica()
            }
            3 -> {
                nivelDivision()
            }

        }
    }

    private fun desmarcar() {
        Incorrecto.visibility = View.GONE
        Correcto.visibility = View.GONE
        bt_corregir.visibility = View.VISIBLE
        ResultadoEditText.text.clear()
    }

}