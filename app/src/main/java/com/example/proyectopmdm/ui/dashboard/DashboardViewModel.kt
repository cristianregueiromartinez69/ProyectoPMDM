package com.example.proyectopmdm.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Video de terror"
    }
    val text: LiveData<String> = _text

    private val _textClick = MutableLiveData<String>().apply {
        value = "TUTORIAL SUBIR VÍDEO ANDROID STUDIO"
    }
    val textYoutube : LiveData<String> = _textClick
}