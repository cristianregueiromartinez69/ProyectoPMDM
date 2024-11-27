package com.example.proyectopmdm.ui.dashboard

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_LOCATION_PERMISSION = 2
    private val REQUEST_BLUETOOTH_PERMISSION = 3
    private var bluetoothAdapter: BluetoothAdapter? = null

    // BroadcastReceiver para recibir resultados del escaneo de Bluetooth
    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Obtener el dispositivo Bluetooth detectado
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                val deviceName = if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                } else {

                }
                device.name
                val deviceAddress = device.address // Dirección MAC del dispositivo
                Toast.makeText(context, "Dispositivo encontrado: $deviceName, $deviceAddress", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar el BluetoothAdapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            // El dispositivo no soporta Bluetooth
            Toast.makeText(requireContext(), "Bluetooth no está disponible en este dispositivo", Toast.LENGTH_SHORT).show()
        }

        // Establecer el listener para el botón de Bluetooth
        binding.bluetookButtonId.setOnClickListener {
            checkBluetooth()
        }

        return root
    }

    private fun checkBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Bluetooth no está disponible", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si los permisos de ubicación están concedidos
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen permisos, solicitar permisos de ubicación
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            // Si los permisos de ubicación están concedidos, verificar los permisos de Bluetooth
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // Si no se tienen permisos de Bluetooth, solicitar permisos
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.BLUETOOTH_SCAN, android.Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_BLUETOOTH_PERMISSION)
            } else {
                // Si ambos permisos están concedidos, comienza el escaneo
                startScanning()
            }
        }

        // Si Bluetooth no está habilitado, solicita al usuario habilitarlo
        if (!bluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            // Si Bluetooth ya está habilitado, comienza el escaneo
            startScanning()
        }
    }

    // Inicia el escaneo de dispositivos Bluetooth
    private fun startScanning() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(bluetoothReceiver, filter)

        // Inicia el escaneo de dispositivos
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.BLUETOOTH_SCAN
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        bluetoothAdapter!!.startDiscovery()
        Toast.makeText(requireContext(), "Escaneando dispositivos Bluetooth...", Toast.LENGTH_SHORT).show()
    }

    // Manejar la respuesta del usuario para habilitar Bluetooth
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth habilitado, comienza el escaneo
                startScanning()
            } else {
                // El usuario no habilitó Bluetooth
                Toast.makeText(requireContext(), "Bluetooth no habilitado", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Manejar la respuesta del usuario a la solicitud de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso de ubicación concedido, proceder con Bluetooth
                    checkBluetooth()
                } else {
                    // Permiso de ubicación denegado
                    Toast.makeText(requireContext(), "Permiso de ubicación necesario para escanear dispositivos Bluetooth", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_BLUETOOTH_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Permisos de Bluetooth concedidos, proceder con el escaneo
                    startScanning()
                } else {
                    // Permisos de Bluetooth denegados
                    Toast.makeText(requireContext(), "Permisos de Bluetooth necesarios para escanear dispositivos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Desregistrar el receptor cuando la vista se destruya
        requireActivity().unregisterReceiver(bluetoothReceiver)
    }
}
