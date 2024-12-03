package com.example.proyectopmdm.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentNotificationsBinding

/**
 * Fragmento que gestiona la vista de notificaciones de la aplicación.
 * Muestra un texto dinámico y proporciona un enlace a una red social.
 * @author cristian
 * @version 1.0
 */
class NotificationsFragment : Fragment() {

    /**
     * Referencia al binding del fragmento, utilizado para acceder a las vistas del layout.
     */
    private var _binding: FragmentNotificationsBinding? = null

    /**
     * Propiedad de solo lectura que devuelve el binding del fragmento.
     * Se asegura de que `_binding` no sea nulo al acceder a las vistas.
     */
    private val binding get() = _binding!!

    /**
     * Método llamado para crear la vista del fragmento.
     * Inicializa las vistas y configura el comportamiento de los elementos interactivos.
     *
     * @param inflater Inflador de vistas para inflar el layout del fragmento.
     * @param container El contenedor en el que se insertará la vista del fragmento.
     * @param savedInstanceState Estado guardado del fragmento, si existe.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Se observa el LiveData del ViewModel y se actualiza el texto de la vista
        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Configura el comportamiento del botón de Twitter para abrir el enlace
        binding.twitterID.setOnClickListener{
            openSocialMedia("https://x.com/SanMarinoTeam?t=RAmgRuOegjUNB1kX9tUVNw&s=09")
        }


        return root
    }

    /**
     * Abre una red social a través de un Intent con el URL proporcionado.
     *
     * @param url La URL de la red social que se abrirá.
     */
    private fun openSocialMedia(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    /**
     * Método llamado cuando se destruye la vista del fragmento.
     * Libera los recursos asociados al binding.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}