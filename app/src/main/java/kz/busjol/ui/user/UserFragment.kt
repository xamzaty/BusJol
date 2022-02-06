package kz.busjol.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.BuildConfig
import kz.busjol.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (BuildConfig.DEBUG) {
            binding.apply {
                userEmailEt.setText("h.yerzhanov@gmail.com")
                userPasswordEt.setText("LuPwbpv9w9")
            }
        }
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtons() {
        binding.apply {
            searchTripButton.setOnClickListener {
                val email = userEmailEt.text.toString() == "h.yerzhanov@gmail.com"
                val password = userPasswordEt.text.toString() == "LuPwbpv9w9"
                if (email && password) {

                }
            }
        }
    }
}