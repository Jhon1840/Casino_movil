package com.Jhon.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class datos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        val usuario = intent.getStringExtra("usuario")


        val db = DatabaseHelper(this)
        val dinero = db.getMoneyByUsername(usuario!!)


        // Mostrar el dinero en el TextView
        val textViewDinero: TextView = findViewById(R.id.textView)
        textViewDinero.text = "HOLA: $usuario"
    }
}