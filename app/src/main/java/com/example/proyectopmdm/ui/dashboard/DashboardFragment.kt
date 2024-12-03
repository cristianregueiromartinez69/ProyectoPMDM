package com.example.proyectopmdm.ui.dashboard
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding

/**
 * Fragmento que gestiona la funcionalidad de la pantalla del dashboard.
 * Incluye un reproductor de video, botones de navegación y acceso a YouTube.
 * @author cristian
 * @version 1.0
 */
class DashboardFragment : Fragment() {

    // Binding para acceder a las vistas del fragmento.
    private var _binding: FragmentDashboardBinding? = null

    /**
     * Acceso no nulo a las vistas del binding.
     * Lanza una excepción si el binding se intenta acceder cuando es nulo.
     */
    private val binding get() = _binding!!

    /**
     * Se ejecuta al crear la vista del fragmento.
     * Configura el ViewModel, inicializa las vistas y establece los listeners.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configura el texto del dashboard desde el ViewModel
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        // Configuración del reproductor de video
        val videoView = binding.videoView

        val pauseButton = binding.pausaID

        val videoUri = "android.resource://${requireContext().packageName}/raw/doctops"
        videoView.setVideoPath(videoUri)

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true // Si quieres que el video se repita
        }

        videoView.start()

        // Configura el botón de pausa/reproducción
        binding.pausaID.setOnClickListener{
            if (videoView.isPlaying) {
                videoView.pause()
                pauseButton.text = "play"
            } else {
                videoView.start()
                pauseButton.text = "stop"
            }
        }

        // Configura el botón para retroceder 10 segundos
        binding.atrasID.setOnClickListener{
            val currentPosition = videoView.currentPosition
            val newPosition = currentPosition - 10000
            if(newPosition >= 0){
                videoView.seekTo(newPosition)
            }
            else{
                videoView.seekTo(0)
            }
        }

        // Configura el botón para adelantar 10 segundos
        binding.adelanteID.setOnClickListener {
            val currentPosition = videoView.currentPosition
            val duration = videoView.duration
            val newPosition = currentPosition + 10000

            if(newPosition <= duration){
                videoView.seekTo(newPosition)
            }
            else{
                videoView.seekTo(duration)
            }
        }

        // Configura el texto y botón de acceso a YouTube desde el ViewModel
        val textViewYoutube: TextView = binding.textoIrYoutube
        dashboardViewModel.textYoutube.observe(viewLifecycleOwner) {
            textViewYoutube.text = it
        }

        binding.buttonyoutube.setOnClickListener{
            openYoutube("https://youtu.be/ChYCCtlqfIc?si=Ka1WTJI86_nQhpYV")
        }



        return root
    }

    /**
     * Se ejecuta al destruir la vista.
     * Libera los recursos asociados al binding.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Abre un enlace de YouTube en un navegador o aplicación instalada.
     *
     * @param url Dirección URL del video de YouTube.
     */
    private fun openYoutube(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}
