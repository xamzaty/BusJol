package kz.busjol.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.databinding.FragmentSearchBinding
import kz.busjol.databinding.FragmentTripBinding
import kz.busjol.utils.IOnBackPressed

class TripFragment : Fragment() {

    private lateinit var tripViewModel: TripViewModel
    private var _binding: FragmentTripBinding? = null

    private val binding get() = _binding!!
    private val args: TripFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tripViewModel = ViewModelProvider(this)[TripViewModel::class.java]

        _binding = FragmentTripBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTitle() {
        val fromCity = args.cities[0]
        val toCity = args.cities[1]
        binding.titleTrip.text = getString(R.string.title_trip, fromCity, toCity)
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}