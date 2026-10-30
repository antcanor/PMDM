package com.isengard.laforjadelservidorcentral

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class Laboratorio3Activity : AppCompatActivity() {

    private lateinit var spinnerCanales: Spinner
    private lateinit var playerView: PlayerView
    private lateinit var txtEstadoConexion: TextView

    private var exoPlayer: ExoPlayer? = null
    private var mediaPlayer: MediaPlayer? = null

    private val canalesMap = mapOf(
        "Minas Tirith" to "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "Isengard" to "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        "Rivendell" to "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actuvity_laboratorio3)

        spinnerCanales = findViewById(R.id.spinnerCanales)
        playerView = findViewById(R.id.playerView)
        txtEstadoConexion = findViewById(R.id.txtEstadoConexion)


        inicializarExoPlayer()
        configurarSpinner()


    }

    private fun inicializarExoPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().also { player ->
            playerView.player = player
            // Listener para monitorizar mutaciones del buffer y estados del reproductor
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            txtEstadoConexion.text = "Cargando Búfer..."
                        }
                        Player.STATE_READY -> {
                            txtEstadoConexion.text = "Transmitiendo en vivo"
                        }
                        Player.STATE_ENDED -> {
                            txtEstadoConexion.text = "Conexión terminada"
                            // Disparo acústico concurrente de la alarma local
                            reproducirAlarmaAcustica()
                        }
                        Player.STATE_IDLE -> {
                            txtEstadoConexion.text = "Conexión inactiva"
                        }
                    }
                }
            })
        }
    }

    private fun configurarSpinner() {
        val nombresCanales = canalesMap.keys.toList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombresCanales
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerCanales.adapter = adapter
        // Listener de selección de canal en tiempo real
        spinnerCanales.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val canalSeleccionado = nombresCanales[position]
                val url = canalesMap[canalSeleccionado]
                if (!url.isNullOrEmpty()) {
                    sintonizarCanal(url)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun sintonizarCanal(url: String) {
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    private fun reproducirAlarmaAcustica() {
        // Liberar instancia anterior si la hubiera
        mediaPlayer?.release()
        mediaPlayer = null
        // Instanciar y disparar de forma concurrente el MediaPlayer con el recurso físico local
        mediaPlayer = MediaPlayer.create(this, R.raw.campanas_alarma)?.apply {
            // Liberación automática al terminar la reproducción del audio
            setOnCompletionListener { mp ->
                mp.release()
                mediaPlayer = null
            }
            start()
        }
    }

    override fun onStop() {
        super.onStop()
        // Pausamos el vídeo si la app va a segundo plano
        exoPlayer?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        // Liberación estricta de memoria RAM y decodificadores de hardware
        exoPlayer?.release()
        exoPlayer = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}