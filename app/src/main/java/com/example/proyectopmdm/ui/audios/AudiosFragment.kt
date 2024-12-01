package com.example.proyectopmdm.ui.audios

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.R
import com.example.proyectopmdm.databinding.FragmentAudioBinding
import com.example.proyectopmdm.databinding.FragmentDashboardBinding
import com.example.proyectopmdm.ui.dashboard.DashboardViewModel

class AudiosFragment : Fragment(){

    private var _binding: FragmentAudioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val audiosViewModel =
            ViewModelProvider(this)[AudiosViewModel::class.java]

        _binding = FragmentAudioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAudios
        audiosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.song)

        mediaPlayer?.start()




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}