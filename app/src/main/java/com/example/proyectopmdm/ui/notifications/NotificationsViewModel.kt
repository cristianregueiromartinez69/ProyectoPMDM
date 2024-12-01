package com.example.proyectopmdm.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel para el fragmento de notificaciones.
 * Gestiona el texto que se muestra en la vista, en este caso, un mensaje invitando a seguir en redes sociales.
 * @author cristian
 * @version 1.0
 */
class NotificationsViewModel : ViewModel() {

    /**
     * MutableLiveData que contiene el texto que se muestra en la interfaz de usuario.
     * Inicialmente contiene el mensaje "Siguenos en las redes sociales!".
     */
    private val _text = MutableLiveData<String>().apply {
        value = "Siguenos en las redes sociales!"
    }

    /**
     * LiveData que expone el texto para ser observado por la vista.
     */
    val text: LiveData<String> = _text
}