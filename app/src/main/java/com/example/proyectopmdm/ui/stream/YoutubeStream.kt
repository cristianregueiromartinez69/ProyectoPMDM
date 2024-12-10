package com.example.proyectopmdm.ui.stream

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectopmdm.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.OnInitializedListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeStream : AppCompatActivity() {

    private val videoId = "M90qWFGEsv4"  // El ID del video en YouTube (puedes cambiar esto con cualquier ID de directo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_stream)

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtubePlayerView)

        // Inicializar el YouTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        // Iniciar el reproductor de YouTube con el ID del video
        youTubePlayerView.initialize(object : OnInitializedListener {
            override fun onInitializationSuccess(
                youTubePlayer: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    // Cargar el video en el reproductor
                    youTubePlayer.loadVideo(videoId, 0f)  // 0f para que empiece desde el inicio
                    youTubePlayer.play()  // Reproduce el video
                }
            }

            override fun onInitializationFailure(
                youTubePlayer: YouTubePlayer,
                errorReason: String
            ) {
                // Manejar el error si la inicialización falla
            }
        })
    }
}
