package com.Jhon.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private val DELAY: Long = 4000
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.inicio)

        mediaPlayer.start()

        // Temporizador para cambiar a la actividad de login después de 4 segundos
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, login::class.java)
            startActivity(intent)
            finish()
        }, DELAY)



    }
}