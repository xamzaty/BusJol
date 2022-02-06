package kz.busjol.ui.ticket_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentTicketScannerBinding

class TicketScannerFragment : BaseFragment<FragmentTicketScannerBinding>(FragmentTicketScannerBinding::inflate) {
    private lateinit var codeScanner: CodeScanner
    private lateinit var ticketScannerViewModel: TicketScannerViewModel

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