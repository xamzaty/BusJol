package kz.busjol.ui.driver_scanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentDriverScannerBinding

class DriverScannerFragment : BaseFragment<FragmentDriverScannerBinding>(FragmentDriverScannerBinding::inflate) {

    private lateinit var codeScanner: CodeScanner
    private val driverScannerViewModel: DriverScannerViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
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