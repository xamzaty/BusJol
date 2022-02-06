package kz.busjol.ui.trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.base.AdapterListener
import kz.busjol.data.City
import kz.busjol.data.Trip
import kz.busjol.databinding.ItemCitySelectorBinding
import kz.busjol.databinding.ItemTripBinding

class TripListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Trip, TripViewHolder>(TripComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class TripComparator : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onCityClicked(trip: Trip)
    }
}