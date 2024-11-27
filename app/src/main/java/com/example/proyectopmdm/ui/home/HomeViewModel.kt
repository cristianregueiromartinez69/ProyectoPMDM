package com.example.proyectopmdm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "App de conexión a través de bluetooth"
    }
    val text: LiveData<String> = _text
}