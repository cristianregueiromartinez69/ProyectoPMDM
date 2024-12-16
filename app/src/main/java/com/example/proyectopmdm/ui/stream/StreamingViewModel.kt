package com.example.proyectopmdm.ui.stream


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel que proporciona datos dinámicos para el fragmento del Stream.
 * Contiene información relacionada con los streams de YouTube y Twitch.
 */
class StreamingViewModel : ViewModel() {

    /**
     * Texto descriptivo para el stream de YouTube.
     * Inicializado con un mensaje informativo.
     */
    private val _youtubeDescription = MutableLiveData<String>().apply {
        value = "Streaming en vivo desde YouTube"
    }
    val youtubeDescription: LiveData<String> = _youtubeDescription

    /**
     * ID del video de YouTube para cargar en el YouTubePlayerView.
     * Inicializado con un ID de ejemplo.
     */
    private val _youtubeVideoId = MutableLiveData<String>().apply {
        value = "M90qWFGEsv4" // Reemplaza con el ID del video que desees mostrar
    }
    val youtubeVideoId: LiveData<String> = _youtubeVideoId

    /**
     * Texto descriptivo para el stream de Twitch.
     * Inicializado con un mensaje informativo.
     */
    private val _twitchDescription = MutableLiveData<String>().apply {
        value = "Streaming en vivo desde Twitch"
    }
    val twitchDescription: LiveData<String> = _twitchDescription

    /**
     * URL del stream de Twitch para cargar en el WebView.
     * Inicializado con una URL de ejemplo.
     */
    private val _twitchStreamUrl = MutableLiveData<String>().apply {
        value = "https://www.twitch.tv/christmas24h"
    }
    val twitchStreamUrl: LiveData<String> = _twitchStreamUrl
}
