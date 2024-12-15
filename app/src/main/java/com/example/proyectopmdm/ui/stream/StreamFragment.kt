package com.example.proyectopmdm.ui.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentStreamingBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class StreamFragment : Fragment() {

    private var _binding: FragmentStreamingBinding? = null
    private val binding get() = _binding!!
    private lateinit var streamingViewModel: StreamingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inicializar el ViewModel
        streamingViewModel = ViewModelProvider(this).get(StreamingViewModel::class.java)

        // Configurar el YouTube Player
        setupYouTubePlayer()

        // Configurar el WebView para Twitch
        setupTwitchWebView()

        return root
    }

    private fun setupYouTubePlayer() {
        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        streamingViewModel.youtubeVideoId.observe(viewLifecycleOwner) { videoId ->
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    }

    private fun setupTwitchWebView() {
        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        streamingViewModel.twitchStreamUrl.observe(viewLifecycleOwner) { url ->
            webView.loadUrl(url)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
