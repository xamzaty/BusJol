package kz.busjol.ui.booking

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.databinding.ItemPaymentBinding

class BookingViewHolder(
    private val binding: ItemPaymentBinding,
    private val listener: BookingAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(payment: Payment) {
        binding.apply {
            if (!payment.isChosen) {
                button.visibility = View.GONE
                selectorImage.setImageResource(R.drawable.ic_selector_selected)
            } else {
                button.visibility = View.VISIBLE
                selectorImage.setImageResource(R.drawable.radio_button_unselected)
            }

            if (payment.type == Payment.PaymentType.BANK_CARDS) {
                title.text = itemView.context.getString(R.string.with_bank_cards)
                image.setImageResource(R.drawable.ic_card_image)
            } else {
                title.text = itemView.context.getString(R.string.kaspi)
                image.setImageResource(R.drawable.kaspi_logo)
            }

            selectorImage.setOnClickListener {
                payment.isChosen = !payment.isChosen
            }

            button.apply {
                setTitle(R.string.proceed_to_checkout_button)
                onSetClickListener {
                    listener.onPaymentButtonClicked(payment)
                }
            }

        }
    }
}