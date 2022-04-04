package kz.busjol.ui.driver_scanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentDriverScannerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DriverScannerFragment : BaseFragment<FragmentDriverScannerBinding>(FragmentDriverScannerBinding::inflate) {

    private lateinit var codeScanner: CodeScanner
    private val viewModel: DriverScannerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        lifecycleScope.launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(android.Manifest.permission.CAMERA)
                    .check()

            if (permissionResult.isGranted) {
                codeScanner.startPreview()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}