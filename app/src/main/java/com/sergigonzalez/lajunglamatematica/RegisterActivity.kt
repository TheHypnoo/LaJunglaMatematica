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
            val user = Usuario.text.toString()
            val date = FechaNacimiento.text.toString()
            val email = Email.text.toString()
            val password = Password.text.toString()
            if (Email.text.isNotEmpty() && Password.text.isNotEmpty() && FechaNacimiento.text.isNotEmpty() && Usuario.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.text.toString(), Password.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val users = hashMapOf(
                                "Nombre de Usuario" to user,
                                "Fecha de Nacimiento" to date,
                                "Email" to email,
                                //Esto como que no,no?"Password" to password
                        )
                        db.collection("users").add(users).addOnSuccessListener { documentReference ->
                            Log.d("DB: Users", "Añadido con el ID => ${documentReference.id}")
                            showHome()
                        }
                                .addOnFailureListener { e ->
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

    fun FindID() {
        Usuario = findViewById(R.id.UsuarioEditText)
        FechaNacimiento = findViewById(R.id.FechaNacimientoEditText)
        Email = findViewById(R.id.emailEditText)
        Password = findViewById(R.id.passwordEditText)
        RegisterButton = findViewById(R.id.RegisterButton)
    }

    fun GuardaUsuario() {



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
        finish()
    }

}