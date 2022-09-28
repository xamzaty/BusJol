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

    private lateinit var fromCityValue: String
    private lateinit var toCityValue: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val binding = ItemJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneyViewHolder(binding, listener, fromCityValue, toCityValue)
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

    fun fromCityValue(value: String?) {
        if (value != null) {
            fromCityValue = value
        }
    }

    fun toCityValue(value: String?) {
        if (value != null) {
            toCityValue = value
        }
    }

    interface OnItemClickListener {
        fun onCityClicked(journeyResponse: Journey)
    }
}