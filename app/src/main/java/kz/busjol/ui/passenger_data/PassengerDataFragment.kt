package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.View
import android.view.View.inflate
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPassengerDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengerDataFragment :
    BaseFragment<FragmentPassengerDataBinding>(FragmentPassengerDataBinding::inflate), UserDataListAdapter.TextFieldListener {

    private val viewModel: PassengerDataViewModel by viewModel()
    private val listAdapter = UserDataListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        initData()
        initRv()
    }

    override fun checkFields(isAnyFieldEmpty: Boolean) {

    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initData() {
        viewModel.userList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun initRv() {
        binding.passengersList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}