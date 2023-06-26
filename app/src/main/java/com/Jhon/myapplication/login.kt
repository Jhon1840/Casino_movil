package com.Jhon.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import android.widget.Toast

class login : AppCompatActivity() {

    //funcionamiento del login

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnIniciarSesion: Button

    //music

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //funcionamiento del login

        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        val db = DatabaseHelper(this@login)

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (db.checkCredentials(usuario, contrasena)) {
                // Las credenciales son válidas, se puede navegar a la siguiente actividad
                Log.d("MainActivity", "Credenciales válidas, iniciar sesión exitoso")
                showToast("Gracias por el dinero gratis")

                //ir al menu
                val intent = Intent(this, datos::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
                finish()


            } else {
                // Las credenciales no son válidas, imprimir datos de la base de datos en la consola
                Log.d("MainActivity", "Credenciales inválidas, datos de la base de datos:")
                db.printUserData()

                showToast("Error en la credenciales mi rey")
            }
        }


        //ir al sing up
        val button: Button = findViewById(R.id.singup)
        button.setOnClickListener {
            val text = etUsuario.text.toString()
            val intent = Intent(this, singUp::class.java)
            intent.putExtra("text", text)
            startActivity(intent)
        }

        //diseño de editview
        val editText: EditText = findViewById(R.id.etUsuario)
        val editTextpassword: EditText = findViewById(R.id.etContrasena)
        editText.hint = "Usuario..."
        editText.setHintTextColor(Color.LTGRAY)

        editTextpassword.hint = "Usuario..."
        editTextpassword.setHintTextColor(Color.LTGRAY)


        //Borrar texto
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Verificar si el texto del EditText está vacío
                if (s.isNullOrEmpty()) {
                    editTextpassword.hint = "Password..."
                    editText.hint = "Usuario..."
                } else {
                    editText.hint = null
                    editTextpassword.hint = null
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}