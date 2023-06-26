package com.Jhon.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class datos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        val usuario = intent.getStringExtra("usuario")


        val db = DatabaseHelper(this)
        val dinero = db.getMoneyByUsername(usuario!!)


        val buttonAbrirOtraActividad: Button = findViewById(R.id.BtnSalir)
        buttonAbrirOtraActividad.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            intent.putExtra("usuario", usuario)
            finish()
        }

        val buttonjuegos: Button = findViewById(R.id.juegos)
        buttonjuegos.setOnClickListener {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            intent.putExtra("usuario", usuario)
            finish()
        }

        val buttondinero: Button = findViewById(R.id.dineross)
        buttondinero.setOnClickListener {
            val intent = Intent(this, comprarD::class.java)
            startActivity(intent)
            intent.putExtra("usuario", usuario)
            finish()
        }

        // Mostrar el dinero en el TextView
        val textViewName: TextView = findViewById(R.id.textView_name)
        textViewName.text = "$usuario"
        val textDinero: TextView= findViewById(R.id.textView_dinero)
        textDinero.text = "Chuflaydorlares: $dinero"
    }
}