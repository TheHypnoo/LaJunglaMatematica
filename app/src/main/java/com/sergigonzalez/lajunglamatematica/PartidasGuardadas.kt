package com.sergigonzalez.lajunglamatematica

import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception


class PartidasGuardadas : AppCompatActivity() {

    private lateinit var TV_NombrePartida: TextView
    private lateinit var BT_PrimeraPartida: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_partidas_guardadas)
        InitButtons()
        initListeners()
    }

    private fun InitButtons(){
        TV_NombrePartida = findViewById(R.id.TV_NombrePartida)
        BT_PrimeraPartida = findViewById(R.id.BT_PrimeraPartida)

    }

    private fun initListeners() {
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val myEditor = myPreferences.edit()
        myEditor.putString("NPartida1", "");
        myEditor.commit();

        val builder = AlertDialog.Builder(this)
        //builder.setMessage("We have a message")

        BT_PrimeraPartida.setOnClickListener {
            val input = EditText(this)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = lp

            builder.setTitle("Ingrese un nombre")
            builder.setView(input)
            builder.setPositiveButton("Aceptar") { position, which ->
                try {
                    while(input != null) {
                        myEditor.putString("NPartida1", input.toString())
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "No vàlid", Toast.LENGTH_SHORT).show()
                }
            }
            builder.show()
        }
        val name = myPreferences.getString("NPartida1", "")
        TV_NombrePartida.setText(name)
    }
}