package com.example.proyectopmdm

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyectopmdm.databinding.ActivityMainBinding

/**
 * Actividad principal de la aplicación.
 * Configura la navegación entre los fragmentos utilizando un `BottomNavigationView`
 * y un `NavController` para gestionar la navegación y las acciones de la barra de herramientas.
 * @author cristian
 * @version 1.0
 */
class MainActivity : AppCompatActivity() {

    // Enlace con el binding de la actividad principal
    private lateinit var binding: ActivityMainBinding

    /**
     * Método llamado al crear la actividad.
     * Configura el `BottomNavigationView`, el `NavController` y la barra de acciones.
     *
     * @param savedInstanceState Estado previo de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el binding de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        // Obtiene el controlador de navegación
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Configura la barra de acción para gestionar la navegación
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.navigation_audios,R.id.navigation_streams
            )
        )
        // Vincula la barra de acción con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Vincula el BottomNavigationView con el controlador de navegación
        navView.setupWithNavController(navController)
    }


}


