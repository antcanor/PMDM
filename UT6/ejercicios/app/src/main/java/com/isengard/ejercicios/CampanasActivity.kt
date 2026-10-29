package com.isengard.ejercicios

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CampanasActivity : AppCompatActivity(){

    // Instancia de la máquina de estados multimedia (Inicia en null / Estado Idle conceptual)
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campanas)

        val btnPlay = findViewById<Button>(R.id.btnPlayAudio)
        val btnPause = findViewById<Button>(R.id.btnPauseAudio)
        val btnRestart = findViewById<Button>(R.id.btnRestartAudio)

        // Inicialización estática: Pasa automáticamente por los estados Idle -> Initialized -> Prepared
        mediaPlayer = MediaPlayer.create(this, R.raw.campanas_gondor).apply { isLooping=true }

        btnPlay.setOnClickListener {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start() // Estado Started
                Toast.makeText(this, "Sonando: Las Campanas de Gondor", Toast.LENGTH_SHORT).show()
            }
        }

        btnPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                // Tras un stop(), se debe volver a preparar el MediaPlayer antes de usarlo
                mediaPlayer?.prepare()
                mediaPlayer?.seekTo(0)
            }
        }
        btnRestart.setOnClickListener {
            if(mediaPlayer?.isPlaying == false){
                mediaPlayer?.start()
            }else{
                mediaPlayer?.seekTo(0)
            }
        }
    }

    // CONTROL DEL HARDWARE: Liberación obligatoria de recursos compartidos del sistema
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Estado End
        mediaPlayer = null
    }
}