package kz.busjol.ui.bus_plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.R
import kz.busjol.data.BusPlan
import kz.busjol.data.City
import kz.busjol.databinding.ItemCitySelectorBinding
import kz.busjol.databinding.ItemSeatBinding
import kz.busjol.databinding.ItemWheelBinding
import kz.busjol.ui.search_trip.CityViewHolder

class BusPlanAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<BusPlan, BusPlanViewHolder>(SeatComparator()) {

    companion object {
        const val VIEW_TYPE_WHEEL = 0
        const val VIEW_TYPE_SEATS = 1
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
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onSeatClicked(seat: BusPlan)
    }
}