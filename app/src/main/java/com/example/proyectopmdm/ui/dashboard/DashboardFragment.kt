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
import com.google.android.exoplayer2.ui.PlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null

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

        val youTubePlayerView: YouTubePlayerView = binding.playerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "_ttcR7VDouE"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        val textView2: TextView = binding.textViewexo2
        dashboardViewModel.textYoutube.observe(viewLifecycleOwner) {
            textView2.text = it
        }
        initializePlayer()

        return root
    }

    private fun initializePlayer(){
        player = ExoPlayer.Builder(requireContext()).build()
        val playerView : PlayerView = binding.playerViewExoPlayer2
        playerView.player = player

        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/raw/doctops")
        val mediaItem = MediaItem.fromUri(videoUri)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.setPlayWhenReady(false)
        player?.release()
        player = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
