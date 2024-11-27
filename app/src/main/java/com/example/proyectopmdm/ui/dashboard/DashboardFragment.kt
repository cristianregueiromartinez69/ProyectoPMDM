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
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding
import java.util.UUID

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_LOCATION_PERMISSION = 2
    private val REQUEST_BLUETOOTH_PERMISSION = 3
    private var bluetoothAdapter: BluetoothAdapter? = null

    private val discoveredDevices = mutableListOf<BluetoothDevice>()

    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (!discoveredDevices.contains(it)) {
                        discoveredDevices.add(it)
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
                        it.name ?: "Dispositivo Desconocido"
                        val deviceAddress = it.address
                        Toast.makeText(context, "Dispositivo encontrado: $deviceName, $deviceAddress", Toast.LENGTH_SHORT).show()
                    }
                }
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

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Bluetooth no está disponible en este dispositivo", Toast.LENGTH_SHORT).show()
        }

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

        if (!bluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            requestPermissionsAndStartScanning()
        }
    }

    private fun requestPermissionsAndStartScanning() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT),
                REQUEST_BLUETOOTH_PERMISSION)
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(bluetoothReceiver, filter)

        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.BLUETOOTH_SCAN) } != PackageManager.PERMISSION_GRANTED) {
            return
        }

        bluetoothAdapter!!.startDiscovery()
        Toast.makeText(requireContext(), "Escaneando dispositivos Bluetooth...", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            bluetoothAdapter!!.cancelDiscovery()
            if (discoveredDevices.isNotEmpty()) {
                showDeviceList()
            } else {
                Toast.makeText(requireContext(), "No se encontraron dispositivos", Toast.LENGTH_SHORT).show()
            }
        }, 5000)
    }

    private fun showDeviceList() {
        val deviceNames = discoveredDevices.map { if (context?.let { it1 ->
                ActivityCompat.checkSelfPermission(
                    it1,
                    Manifest.permission.BLUETOOTH_CONNECT
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
            it.name ?: "Dispositivo Desconocido" }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Selecciona un dispositivo")
        builder.setItems(deviceNames.toTypedArray()) { _, which ->
            val selectedDevice = discoveredDevices[which]
            connectToDevice(selectedDevice)
        }
        builder.show()
    }

    private fun connectToDevice(device: BluetoothDevice) {
        if (context?.let {
                ActivityCompat.checkSelfPermission(it, Manifest.permission.BLUETOOTH_CONNECT)
            } != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Permiso para conectar no concedido", Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener un UUID válido para la conexión
        val uuid: UUID = device.uuids?.firstOrNull()?.uuid
            ?: UUID.randomUUID() // Si no hay UUID, generar uno aleatorio como fallback

        Thread {
            try {
                val socket = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothAdapter?.cancelDiscovery()
                socket.connect()
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Conectado a ${device.name}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error al conectar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            requestPermissionsAndStartScanning()
        } else if (requestCode == REQUEST_ENABLE_BT) {
            Toast.makeText(requireContext(), "Bluetooth no habilitado", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION || requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startScanning()
            } else {
                Toast.makeText(requireContext(), "Permisos necesarios no concedidos", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().unregisterReceiver(bluetoothReceiver)
    }
}
