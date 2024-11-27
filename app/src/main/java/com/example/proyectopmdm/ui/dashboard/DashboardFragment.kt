package com.example.proyectopmdm.ui.dashboard

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopmdm.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 1


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

        binding.bluetookButtonId.setOnClickListener{
            checkBluetooth()
        }
        return root
    }

    private fun checkBluetooth() {
        // Obtener el BluetoothAdapter
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        // Verificar si el dispositivo tiene Bluetooth
        if (bluetoothAdapter == null) {
            // El dispositivo no tiene Bluetooth
            Toast.makeText(requireContext(), "Bluetooth no está disponible", Toast.LENGTH_SHORT).show()
        } else {
            // Verificar si Bluetooth está habilitado
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            } else {
                // Bluetooth ya está habilitado
                Toast.makeText(requireContext(), "Bluetooth ya está habilitado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Manejar la respuesta del usuario para habilitar Bluetooth
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // El usuario habilitó Bluetooth
                Toast.makeText(requireContext(), "Bluetooth habilitado", Toast.LENGTH_SHORT).show()
            } else {
                // El usuario no habilitó Bluetooth
                Toast.makeText(requireContext(), "Bluetooth no habilitado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}