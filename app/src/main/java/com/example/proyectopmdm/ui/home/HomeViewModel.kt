package com.example.proyectopmdm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * [ViewModel] que gestiona los datos relacionados con la pantalla de inicio de la aplicación.
 * Proporciona un texto que se observa y se muestra en la interfaz de usuario del fragmento de inicio.
 * @author cristian
 * @version 1.0
 */
class HomeViewModel : ViewModel() {

    /**
     * Propiedad privada que almacena el texto que se mostrará en la vista del fragmento.
     * Se inicializa con el valor "Audio and Video Player".
     */
    private val _text = MutableLiveData<String>().apply {
        value = "Audio and Video Player"
    }

    /**
     * Propiedad pública que expone el texto almacenado en [_text] como un [LiveData].
     * Este [LiveData] es observado por la vista para actualizar el texto en la interfaz de usuario.
     */
    val text: LiveData<String> = _text
}