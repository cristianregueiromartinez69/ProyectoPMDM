package com.example.proyectopmdm.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pulsa para realizar la conexión"
    }
    val text: LiveData<String> = _text
}