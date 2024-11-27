package com.example.proyectopmdm.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Siguenos en las redes sociales!"
    }
    val text: LiveData<String> = _text
}