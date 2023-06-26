package com.Jhon.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class comprarD : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var saldo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar_d)

        // Inicializar la instancia de DatabaseHelper
        db = DatabaseHelper(this)


        val usuario = intent.getStringExtra("usuario")


        // Obtener el saldo actual del usuario
        saldo = db.getMoneyByUsername("$usuario")

        // Mostrar el saldo en un TextView
        val saldoTextView: TextView = findViewById(R.id.saldoTextView)
        saldoTextView.text = "Saldo: $saldo Chuflayrdolares"

        // Asociar eventos de clic a los botones
        val btnMinus100: Button = findViewById(R.id.btn100)
        val btnMinus2000: Button = findViewById(R.id.btn2000)
        val btnMinus50000: Button = findViewById(R.id.btn50000)
        val btnMinus200: Button = findViewById(R.id.btn200)

        btnMinus100.setOnClickListener {
            actualizarSaldo(+100)
        }

        btnMinus2000.setOnClickListener {
            actualizarSaldo(+2000)
        }

        btnMinus50000.setOnClickListener {
            actualizarSaldo(+50000)
        }

        btnMinus100.setOnClickListener {
            actualizarSaldo(+100)
        }
    }

    private fun actualizarSaldo(cantidad: Int) {
        // Actualizar el saldo
        saldo += cantidad

        // Mostrar el saldo actualizado en un TextView
        val saldoTextView: TextView = findViewById(R.id.saldoTextView)
        saldoTextView.text = "Saldo: $saldo Chuflayrdolares"

        // Actualizar el saldo en la base de datos
        db.updateMoneyByUsername("jhon", saldo)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Cerrar la conexión con la base de datos al finalizar la actividad
        db.close()
    }
}