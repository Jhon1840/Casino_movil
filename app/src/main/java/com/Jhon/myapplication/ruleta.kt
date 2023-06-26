package com.Jhon.myapplication

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class ruleta : AppCompatActivity() ,Animation.AnimationListener {

    private lateinit var mediaPlayer: MediaPlayer

    private var count = 0
    private var flag = false

    private var powerButton: ImageView? = null

    private var pointerImageView: ImageView? = null
    private var infoText: TextView? = null
    private var prizeText = "N/A"

    private var saldoActual = 500 // Saldo inicial del usuario
    private lateinit var saldoTextView: TextView

    private lateinit var db: DatabaseHelper // Objeto de la clase DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruleta)

        db = DatabaseHelper(this) // Inicializar el objeto de la base de datos

        mediaPlayer = MediaPlayer.create(this, R.raw.segundo)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        powerButton = findViewById(R.id.powerButton)
        powerButton!!.setOnTouchListener(PowerTouchListener())

        pointerImageView = findViewById(R.id.imageWheel)
        infoText = findViewById(R.id.infoText)

        saldoTextView = findViewById(R.id.saldoTextView)
        saldoTextView.text = "Saldo: $saldoActual Chuflayrdolares"

        intSpinner()
    }

    private fun intSpinner() {
        pointerImageView = findViewById(R.id.imageWheel)
        infoText = findViewById(R.id.infoText)
    }

    fun startSpinner() {
        val prizes = intArrayOf(-200, -1000, -600, 1000, -500, -40, -20, 700, 3000, 400, 1000, 1200)
        var mSpinDuration: Long = 0
        var mSpinRevolution = 0f

        mSpinRevolution = 3600f
        mSpinDuration = 5000

        if (count >= 30) {
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60) {
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()
        }

        val end = Math.floor(Math.random() * 3600).toInt()
        val numOfPrizes = prizes.size
        val degreesPerPrize = 360 / numOfPrizes
        val shift = 0
        val prizeIndex = (shift + end) % numOfPrizes

        prizeText = "Ganaste ${prizes[prizeIndex]}  Chuflayrdolares"

        val rotateAnim = RotateAnimation(
            0f, mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                infoText!!.text = "Suerte :3..."
            }

            override fun onAnimationEnd(p0: Animation?) {
                infoText!!.text = prizeText

                if (prizeText.contains("Chuflayrdolares")) {
                    val prize = prizeText.split(" ")[1].toInt()
                    val saldoNuevo = saldoActual + prize

                    if (saldoNuevo <= 0) {
                        showToast("No tienes suficiente dinero para jugar")
                        powerButton?.isEnabled = false
                    } else {
                        actualizarSaldo(saldoNuevo)
                        db.updateMoneyByUsername("jhon", saldoNuevo)
                    }
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        rotateAnim.fillAfter = true
        pointerImageView!!.startAnimation(rotateAnim)
    }

    private fun actualizarSaldo(saldo: Int) {
        saldoActual = saldo
        saldoTextView.text = "Saldo: $saldoActual Chuflayrdolares"
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private inner class PowerTouchListener : View.OnTouchListener {
        override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

            when (motionEvent!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    flag = true
                    count = 0
                    Thread {
                        while (flag) {
                            count++
                            if (count == 100) {
                                try {
                                    Thread.sleep(100)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                count = 0
                            }
                            try {
                                Thread.sleep(10)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    }.start()
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    flag = false
                    startSpinner()
                    return false
                }
            }

            return false
        }
    }

    override fun onAnimationStart(p0: Animation?) {
        infoText!!.text = "Suerte :3..."
    }

    override fun onAnimationEnd(p0: Animation?) {
        infoText!!.text = prizeText
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }


}