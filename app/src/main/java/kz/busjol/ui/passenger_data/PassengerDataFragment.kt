package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.databinding.FragmentPassengerDataBinding

class PassengerDataFragment : Fragment() {

    private lateinit var passengerDataViewModel: PassengerDataViewModel
    private var _binding: FragmentPassengerDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        passengerDataViewModel = ViewModelProvider(this)[PassengerDataViewModel::class.java]

        _binding = FragmentPassengerDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        passengerDataViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}