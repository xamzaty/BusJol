package kz.busjol.ui.bus_plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.R
import kz.busjol.data.network.model.BusPlan
import kz.busjol.databinding.ItemSeatBinding

class BusPlanAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<BusPlan, BusPlanViewHolder>(SeatComparator()) {

    companion object {
        const val VIEW_TYPE_WHEEL = R.layout.item_wheel
        const val VIEW_TYPE_SEATS = R.layout.item_seat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusPlanViewHolder {
        val binding = ItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusPlanViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BusPlanViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class SeatComparator : DiffUtil.ItemCallback<BusPlan>() {
        override fun areItemsTheSame(oldItem: BusPlan, newItem: BusPlan) =
            oldItem.place == newItem.place

        override fun areContentsTheSame(oldItem: BusPlan, newItem: BusPlan) =
            oldItem.place == newItem.place
    }

    interface OnItemClickListener {
        fun onSeatClicked(seatList: List<BusPlan>)
    }
}