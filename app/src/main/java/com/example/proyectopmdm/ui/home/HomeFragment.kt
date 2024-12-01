package com.example.proyectopmdm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentHomeBinding

/**
 * Fragmento que representa la pantalla de inicio de la aplicación.
 * Muestra un texto gestionado por un ViewModel.
 * @author cristian
 * @version 1.0
 */
class HomeFragment : Fragment() {

    /**
     * Referencia al objeto [FragmentHomeBinding] utilizado para acceder a los elementos de la vista.
     * Se usa para vincular la interfaz de usuario con la lógica del fragmento.
     */
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    /**
     * Método llamado cuando la vista del fragmento es creada.
     * Se utiliza para inicializar el [ViewModel] y configurar la vista.
     *
     * @param inflater Inflador utilizado para convertir el archivo de diseño XML en una vista.
     * @param container Contenedor al que se agregará la vista del fragmento.
     * @param savedInstanceState Estado guardado de la instancia, si está disponible.
     * @return La vista del fragmento, que se asocia a [binding.root].
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Vincula el texto del ViewModel con el TextView del fragmento.
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }



        return root
    }

    /**
     * Método llamado cuando la vista del fragmento es destruida.
     * Limpia la referencia de [binding] para evitar pérdidas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}