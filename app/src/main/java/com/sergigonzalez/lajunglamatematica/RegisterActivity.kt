package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var user: EditText
    private lateinit var birthDate: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btRegister: Button
    private var mLastClickTime: Long = 0
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_register)
        FindID()
        btRegister.setOnClickListener{
            val user = user.text.toString()
            val date = birthDate.text.toString()
            val email = email.text.toString()

            if (this.email.text.isNotEmpty() && password.text.isNotEmpty() && birthDate.text.isNotEmpty() && this.user.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email.text.toString(), password.text.toString()).addOnCompleteListener addOnSuccessListener@{
                    if (it.isSuccessful) {
                        val users = hashMapOf(
                            "Nombre de Usuario" to user,
                            "Fecha de Nacimiento" to date,
                            "Email" to email.toLowerCase(Locale.ROOT),
                            "lvlSuma" to 0,
                            "lvlResta" to 0,
                            "lvlMultiplica" to 0,
                            "lvlDivision" to 0,
                            "puntuacion" to 0,
                            "dondeEstoy" to 0,
                            "vidas" to 0,
                            "pruebateSuma" to false,
                            "pruebateResta" to false,
                            "pruebateMultiplica" to false,
                            "pruebateDivision" to false
                        )
                        db.collection("users").add(users).addOnSuccessListener { documentReference ->
                            Log.d("DB: Users", "Añadido con el ID => ${documentReference.id}")
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                                return@addOnSuccessListener
                            } else {
                                showHome()
                            }
                            mLastClickTime = SystemClock.elapsedRealtime()
                        }.addOnFailureListener { e ->
                            Log.w("DB: Users", "Error al añadir el usuario", e)
                        }
                    } else {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                            return@addOnSuccessListener
                        } else {
                            showAlert("Se ha producido un error registando al usuario")
                        }
                        mLastClickTime = SystemClock.elapsedRealtime()

                    }
                }
                } else {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                    return@setOnClickListener
                } else {
                    showAlert("Alguno de los campos estan vacios")
                }
                mLastClickTime = SystemClock.elapsedRealtime()
            }
        }

    }

    private fun FindID() {
        user = findViewById(R.id.UsuarioEditText)
        birthDate = findViewById(R.id.FechaNacimientoEditText)
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        btRegister = findViewById(R.id.loginButton)
    }

    private fun showAlert(Message: String) {
        val view = View.inflate(this, R.layout.dialog_error, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        btn_confirm.setOnClickListener{
            dialog.dismiss()
        }
        val textMessage = view.findViewById<TextView>(R.id.textMessage)
        textMessage.text = Message
    }

    private fun showHome(){
        startActivity(Intent(this, menuPrincipal::class.java))
        finish()
    }

}