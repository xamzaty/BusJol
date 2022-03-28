package kz.busjol.ui.rate_app

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogRateAppBinding

class RateAppDialog : BaseBottomFragmentDialog<DialogRateAppBinding>(DialogRateAppBinding::inflate, false) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}