package com.example.proyectopmdm.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel que proporciona datos dinámicos para el fragmento del Dashboard.
 * Contiene texto para mostrar en la interfaz de usuario y enlaces relacionados con YouTube.
 * @author cristian
 * @version 1.0
 */
class DashboardViewModel : ViewModel() {

    /**
     * Texto principal que se muestra en el fragmento del Dashboard.
     * Inicializado con el valor "Video de terror".
     */
    private val _text = MutableLiveData<String>().apply {
        value = "Video de terror"
    }

    /**
     * Texto de solo lectura asociado a [_text].
     * Puede ser observado por la interfaz de usuario para reflejar cambios.
     */
    val text: LiveData<String> = _text


    /**
     * Texto adicional que se muestra en el fragmento, relacionado con un tutorial de YouTube.
     * Inicializado con el valor "TUTORIAL SUBIR VÍDEO ANDROID STUDIO".
     */
    private val _textClick = MutableLiveData<String>().apply {
        value = "TUTORIAL SUBIR VÍDEO ANDROID STUDIO"
    }

    /**
     * Texto de solo lectura asociado a [_textClick].
     * Puede ser observado por la interfaz de usuario para mostrar información adicional.
     */
    val textYoutube : LiveData<String> = _textClick
}