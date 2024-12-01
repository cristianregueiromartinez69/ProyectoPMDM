package com.example.proyectopmdm.ui.audios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AudiosViewModel : ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "Mi canción favorita"
    }
    val text: LiveData<String> = _text

    private val _textClick = MutableLiveData<String>().apply {
        value = "TUTORIAL SUBIR AUDIO ANDROID STUDIO"
    }
    val textYoutube : LiveData<String> = _textClick
}