package com.example.proyectopmdm.ui.stream
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectopmdm.R

class TwitchWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_stream)

        // Inicializamos el WebView
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // URL del stream de Twitch
        val streamUrl = "https://www.twitch.tv/christmas24h"
        //Cargamos la url del stream
        webView.loadUrl(streamUrl)
    }
}
