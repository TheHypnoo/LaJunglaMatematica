package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {
    private lateinit var Obtener: Button
    private lateinit var nomUsuari: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var SignUpButton: Button
    private lateinit var logInButton: Button
    // Access a Cloud Firestore instance from your Activity
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        nomUsuari = findViewById(R.id.nomUsuari)
        Obtener = findViewById(R.id.Obtener)
        setup()
        CrearUsuari()
        Obtener.setOnClickListener {
            saberUsuari()
        }

    }

    private fun setup(){
        title = "Autenticación"
        SignUpButton = findViewById(R.id.SignUpButton)
        logInButton = findViewById(R.id.logInButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        SignUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "")
                    } else {
                        showAlert()
                    }
                }
            }
        }

        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "")
                    } else {
                        showAlert()
                    }
                }
            }
        }

    }

    private fun CrearUsuari(){
        // Create a new user with a first and last name
        val user = hashMapOf(
            "Nombre" to "Sergi",
            "Apellido" to "Gonzalez",
            "Nombre de Usuario" to "TheHypnoo",
            "Fecha de nacimiento" to 2000
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("DB: Users", "Añadido con el ID => ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("DB: Users", "Error al añadir el usuario", e)
            }
    }

    private fun saberUsuari(){
        val users = db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("", "${document.id} => ${document.data}").toString()
                    if(document.data.equals("Nombre de Usuario")) {
                        val usuario = document.data
                        nomUsuari.setText("$usuario").toString()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB: Users", "Error al intentar obtener la información", exception)
            }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String){
        val homeIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }

}