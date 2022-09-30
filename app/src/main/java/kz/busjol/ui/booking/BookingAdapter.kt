package kz.busjol.ui.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.databinding.ItemPaymentBinding

class BookingAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Payment, BookingViewHolder>(BookingComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class BookingComparator : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment) =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Payment, newItem: Payment) =
            oldItem.type == newItem.type
    }

    interface OnItemClickListener {
        fun onPaymentButtonClicked(payment: Payment)
    }
}