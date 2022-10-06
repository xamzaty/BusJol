package kz.busjol.ui.payment_order_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.databinding.ItemPaymentOrderQrBinding

class PaymentOrderResultAdapter : ListAdapter<Qr, PaymentOrderViewHolder>(PaymentOrderResultComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentOrderViewHolder {
        val binding = ItemPaymentOrderQrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentOrderViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class PaymentOrderResultComparator : DiffUtil.ItemCallback<Qr>() {
        override fun areItemsTheSame(oldItem: Qr, newItem: Qr) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: Qr, newItem: Qr) = oldItem.url == newItem.url
    }
}