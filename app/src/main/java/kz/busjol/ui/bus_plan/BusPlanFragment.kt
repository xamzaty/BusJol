package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.databinding.FragmentBusPlanBinding

class BusPlanFragment : Fragment() {

    private lateinit var busPlanViewModel: BusPlanViewModel
    private var _binding: FragmentBusPlanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        busPlanViewModel = ViewModelProvider(this)[BusPlanViewModel::class.java]

        _binding = FragmentBusPlanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        busPlanViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}