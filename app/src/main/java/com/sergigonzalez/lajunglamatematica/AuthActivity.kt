package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var register: TextView
    private lateinit var logInButton: Button
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_auth)
        setup()
    }

    private fun setup(){
        //title = "Autenticación"
        register = findViewById(R.id.SignUp)
        logInButton = findViewById(R.id.RegisterButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)



        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                ).addOnCompleteListener {
                    mLastClickTime = if (it.isSuccessful) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                            return@addOnCompleteListener
                        } else {
                            showHome()
                        }
                        SystemClock.elapsedRealtime()
                    } else {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                            return@addOnCompleteListener
                        } else {
                            showAlert()
                        }
                        SystemClock.elapsedRealtime()
                    }
                }
            }
        }

        register.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return@setOnClickListener
            } else {
                startActivity(Intent(applicationContext, RegisterActivity::class.java))
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(){
        startActivity(Intent(this, menuPrincipal::class.java))
        finish()
    }

}