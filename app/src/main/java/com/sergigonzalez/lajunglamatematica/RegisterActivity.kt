package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {
    private lateinit var user: EditText
    private lateinit var birthDate: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btRegister: Button
    private lateinit var animation: LottieAnimationView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_register)
        //title = "Registro"
        FindID()
        animation.speed = 0.80F
        btRegister.setOnClickListener{
            val user = user.text.toString()
            val date = birthDate.text.toString()
            val email = email.text.toString()
            if (this.email.text.isNotEmpty() && password.text.isNotEmpty() && birthDate.text.isNotEmpty() && this.user.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email.text.toString(), password.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val users = hashMapOf(
                                "Nombre de Usuario" to user,
                                "Fecha de Nacimiento" to date,
                                "Email" to email,
                                "lvlSuma" to 0,
                                "lvlResta" to 0,
                                "lvlMultiplica" to 0,
                                "lvlDivision" to 0,
                                "puntuacion" to 0,
                                "pruebateSuma" to false,
                                "pruebateResta" to false,
                                "pruebateMultiplica" to false,
                                "pruebateDivision" to false
                        )
                        db.collection("users").add(users).addOnSuccessListener { documentReference ->
                            Log.d("DB: Users", "Añadido con el ID => ${documentReference.id}")
                            showHome()
                        }.addOnFailureListener { e ->
                                    Log.w("DB: Users", "Error al añadir el usuario", e)
                        }
                    } else {
                        showAlert()
                    }
                }
                } else {
                    showAlert()
            }
        }

    }

    private fun FindID() {
        user = findViewById(R.id.UsuarioEditText)
        birthDate = findViewById(R.id.FechaNacimientoEditText)
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        btRegister = findViewById(R.id.loginButton)
        animation = findViewById(R.id.AnimalAnimation)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error registando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(){
        val homeIntent = Intent(this,AnimationLoading::class.java)
        startActivity(homeIntent)
        finish()
    }

}