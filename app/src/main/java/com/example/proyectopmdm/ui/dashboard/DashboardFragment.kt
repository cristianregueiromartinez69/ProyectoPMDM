package com.example.proyectopmdm.ui.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null
    private var trackSelector: DefaultTrackSelector? = null

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

        initializePlayer()



        return root
    }

    private fun initializePlayer() {
        trackSelector = DefaultTrackSelector(requireContext()).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }

        player = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector!!)
            .build()

        val playerView: PlayerView = binding.playerView
        playerView.player = player

        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/raw/doctops")
        val mediaItem = MediaItem.fromUri(videoUri)
        player?.setMediaItem(mediaItem)
        player?.playWhenReady = true
        player?.prepare()

        // Listener para cambiar la velocidad de reproducción
        playerView.setControllerVisibilityListener { visibility ->
            if (visibility == View.VISIBLE) {
                // Aquí puedes configurar un menú o dialogo para cambiar la velocidad
                // Ejemplo de cambio de velocidad
                player?.setPlaybackParameters(player!!.playbackParameters.withSpeed(1.5f))
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }


}
