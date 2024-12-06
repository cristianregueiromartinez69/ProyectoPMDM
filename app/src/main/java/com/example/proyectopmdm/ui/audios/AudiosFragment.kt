package com.example.proyectopmdm.ui.audios

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.R
import com.example.proyectopmdm.databinding.FragmentAudioBinding

/**
 * Fragmento que gestiona la reproducción de un archivo de audio local y proporciona controles
 * para la reproducción, además de la capacidad de abrir un enlace de YouTube.
 * @author cristian
 * @version 1.0
 */
class AudiosFragment : Fragment(){

    // Variable privada para manejar el enlace con la vista XML
    private var _binding: FragmentAudioBinding? = null

    /**
     * Propiedad para acceder de forma segura al enlace de vista no nulo.
     */
    private val binding get() = _binding!!

    // MediaPlayer para gestionar la reproducción de audio
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Método que se llama para inflar y configurar la vista del fragmento.
     *
     * @param inflater Objeto utilizado para inflar el diseño XML.
     * @param container Contenedor principal donde se insertará la vista del fragmento.
     * @param savedInstanceState Estado previamente guardado del fragmento, si existe.
     * @return La vista raíz inflada y configurada.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Crear ViewModel asociado al fragmento
        val audiosViewModel =
            ViewModelProvider(this)[AudiosViewModel::class.java]

        // Inflar el diseño del fragmento y establecer el enlace con la vista
        _binding = FragmentAudioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observar cambios en el texto del ViewModel y actualizarlos en el TextView
        val textView: TextView = binding.textAudios
        audiosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Inicializar el MediaPlayer con un archivo de audio local
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.song)

        mediaPlayer?.start()



        // Configuración del botón de pausa/reproducción
        binding.pauseAudiosId.setOnClickListener {
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
                binding.pauseAudiosId.text = "play"
            }
            else{
                mediaPlayer?.start()
                binding.pauseAudiosId.text = "stop"
            }
        }

        // Configuración del botón para retroceder en el audio
        binding.atrasAudiosId.setOnClickListener {
            mediaPlayer?.let {
                val newPosition = it.currentPosition - 10000
                if(newPosition >= 0){
                    it.seekTo(newPosition)
                }
                else{
                    it.seekTo(0)
                }
            }
        }

        // Configuración del botón para avanzar en el audio
        binding.advanceAudiosId.setOnClickListener {
            mediaPlayer?.let {
                val newPosition = it.currentPosition + 10000
                if(newPosition <= it.duration){
                    it.seekTo(newPosition)
                }
                else{
                    it.seekTo(it.duration)
                }
            }
        }




        return root
    }

    /**
     * Método llamado cuando la vista asociada al fragmento es destruida.
     * Se liberan los recursos utilizados.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer?.release()
        mediaPlayer = null
    }


}