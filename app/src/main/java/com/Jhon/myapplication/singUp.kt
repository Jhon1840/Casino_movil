package com.Jhon.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class singUp : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var etCorreo: EditText
    private lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        //indexar nuevo usuario

        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        etCorreo = findViewById(R.id.etCorreo)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        val db = DatabaseHelper(this@singUp)

        btnRegistrar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val correo = etCorreo.text.toString().trim()

            val id = db.insertUsuario(usuario, contrasena, correo)

            if (id != -1L) {
                // Registro exitoso
                etUsuario.text.clear()
                etContrasena.text.clear()
                etCorreo.text.clear()
                showToast("Usuario registrado exitosamente")
            } else {
                // Error al registrar
                showToast("Error al registrar el usuario")
            }
        }



        //ir al login up
        val button: Button = findViewById(R.id.singup)
        button.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}