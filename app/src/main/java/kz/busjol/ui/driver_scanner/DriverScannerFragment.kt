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
import kz.busjol.utils.state.ViewState
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DriverScannerFragment : BaseFragment<FragmentDriverScannerBinding>(FragmentDriverScannerBinding::inflate) {

    private val viewModel: DriverScannerViewModel by viewModel()
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCameraPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initScannerData()
        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun checkCameraPermission() {
        lifecycleScope.launch {
            val permissionResult = TedPermission
                .create()
                .setPermissions(android.Manifest.permission.CAMERA)
                .check()

            if (permissionResult.isGranted) {
                codeScanner.startPreview()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner) {
                handleViewStateChanges(it)
            }
        }
    }

    private fun handleViewStateChanges(state: ViewState<DriverScannerViewState>) {
        when (state) {
            is ViewState.Data -> {
                handleViewState(state.data)
                loaderVisibility(false)
            }
            is ViewState.Error -> {
                onError()
                loaderVisibility(false)
            }
            is ViewState.Loading -> loaderVisibility(true)
        }
    }

    private fun handleViewState(state: DriverScannerViewState) {
        when (state) {
            is DriverScannerViewState.NavigateToTheNextScreen -> {
                Toast.makeText(activity, state.result.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loaderVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.loader.visibility = View.VISIBLE
            binding.loaderBackground.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
            binding.loaderBackground.visibility = View.GONE
        }
    }

    private fun initScannerData() {
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)

        codeScanner.decodeCallback = DecodeCallback { result ->
            activity?.runOnUiThread {
                val jsonResult = JSONObject(result.toString())

                viewModel.onAction(DriverScannerAction.PassQrData(jsonResult))
            }
        }
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}