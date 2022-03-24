package kz.busjol.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import kz.busjol.Consts
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentContactsBinding
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
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Consts.PHONE_NUMBER, null))
                startActivity(intent)
            }
        }
    }
}