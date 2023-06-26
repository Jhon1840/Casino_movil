package com.Jhon.myapplication

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class menu : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mediaPlayer = MediaPlayer.create(this, R.raw.segundo)

        mediaPlayer.start()
        mediaPlayer.isLooping = true

        val usuario = intent.getStringExtra("usuario")


        //ir al login up
        val button: Button = findViewById(R.id.ruleta)
        button.setOnClickListener {
            val intent = Intent(this, ruleta::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)

        }
    }
}