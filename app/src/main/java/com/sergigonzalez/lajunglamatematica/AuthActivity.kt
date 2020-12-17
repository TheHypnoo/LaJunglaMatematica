package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
                            showAlert("No se ha encontrado al usuario")
                        }
                        SystemClock.elapsedRealtime()
                    }
                }
            } else {
                showAlert("Alguno de los campos estan vacios")
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


    private fun showAlert(Message: String) {

        val view = View.inflate(this, R.layout.dialog_error, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btn_confirm = view.findViewById<Button>(R.id.btn_leaveCountDown)
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