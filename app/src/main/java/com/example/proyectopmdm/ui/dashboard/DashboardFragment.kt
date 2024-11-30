package com.example.proyectopmdm.ui.dashboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Configurar el VideoView
        val videoView = binding.videoView

        // Establecer la URI del video
        val videoUri = "android.resource://${requireContext().packageName}/raw/doctops"
        videoView.setVideoPath(videoUri)

        // Controladores para pausar y reproducir
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true // Si quieres que el video se repita
        }

        // Iniciar la reproducción automáticamente
        videoView.start()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
