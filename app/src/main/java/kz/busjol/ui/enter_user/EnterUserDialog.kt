package kz.busjol.ui.enter_user

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogEnterUserBinding

class EnterUserDialog : BaseBottomFragmentDialog<DialogEnterUserBinding>(DialogEnterUserBinding::inflate, true) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }
}