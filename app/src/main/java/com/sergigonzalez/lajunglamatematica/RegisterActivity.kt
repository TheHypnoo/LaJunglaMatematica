package com.sergigonzalez.lajunglamatematica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.net.PasswordAuthentication

class RegisterActivity : AppCompatActivity() {
    private lateinit var Usuario: EditText
    private lateinit var FechaNacimiento: EditText
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var RegisterButton: Button
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Registro"
        FindID()
        RegisterButton.setOnClickListener{

        }

    }

    fun FindID() {
        Usuario = findViewById(R.id.UsuarioEditText)
        FechaNacimiento = findViewById(R.id.FechaNacimientoEditText)
        Email = findViewById(R.id.emailEditText)
        Password = findViewById(R.id.passwordEditText)
        RegisterButton = findViewById(R.id.RegisterButton)
    }

    fun GuardaUsuario() {
        val user = Usuario.text
        val date = FechaNacimiento.text
        val email = Email.text
        val password = Password.text

        if(user.isEmpty() && date.isEmpty() && email.isEmpty() && password.isEmpty()) {
            showAlert()
        } else {
            val users = hashMapOf(
                    "Nombre de Usuario" to user,
                    "Fecha de Nacimiento" to date,
                    "Email" to email,
                    "Password" to password
            )

            db.collection("users").add(users).addOnSuccessListener { documentReference ->
                        Log.d("DB: Users", "Añadido con el ID => ${documentReference.id}")
                        showHome()
                    }
                    .addOnFailureListener { e ->
                        Log.w("DB: Users", "Error al añadir el usuario", e)
                    }
        }

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
        val homeIntent = Intent(this,menuPrincipal::class.java)
        startActivity(homeIntent)
    }

}