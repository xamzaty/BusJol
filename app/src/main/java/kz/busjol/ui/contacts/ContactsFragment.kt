package kz.busjol.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentContactsBinding
import kz.busjol.ui.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val viewModel: ContactsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            callButton.setOnClickListener {
                val phone = "+77055002555"
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                startActivity(intent)
            }
        }
    }
}