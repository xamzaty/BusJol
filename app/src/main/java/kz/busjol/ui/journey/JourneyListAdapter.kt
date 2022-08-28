package kz.busjol.ui.journey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.databinding.ItemJourneyBinding
import kz.busjol.domain.model.Journey

class JourneyListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Journey, JourneyViewHolder>(TripComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val binding = ItemJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class TripComparator : DiffUtil.ItemCallback<Journey>() {
        override fun areItemsTheSame(oldItem: Journey, newItem: Journey) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Journey, newItem: Journey) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onCityClicked(journeyResponse: Journey)
    }
}