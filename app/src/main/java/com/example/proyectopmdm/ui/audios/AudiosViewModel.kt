package com.example.proyectopmdm.ui.audios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel para gestionar los datos relacionados con el fragmento de audios.
 * Proporciona datos observables para la UI, como el título de la canción y un texto relacionado con YouTube.
 * @author cristian
 * @version 1.0
 */
class AudiosViewModel : ViewModel(){

    // LiveData que contiene el texto de la canción favorita
    private val _text = MutableLiveData<String>().apply {
        value = "Mi canción favorita"
    }

    /**
     * LiveData observable que expone el texto de la canción favorita.
     */
    val text: LiveData<String> = _text

    // LiveData que contiene un texto asociado a un tutorial de YouTube
    private val _textClick = MutableLiveData<String>().apply {
        value = "TUTORIAL SUBIR AUDIO ANDROID STUDIO"
    }

    /**
     * LiveData observable que expone el texto relacionado con un tutorial de YouTube.
     */
    val textYoutubeAudio : LiveData<String> = _textClick
}