package kz.busjol.ui.city_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.busjol.databinding.ItemCitySelectorBinding
import kz.busjol.domain.model.City

class CityListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<City, CityViewHolder>(CityComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCitySelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CityComparator : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: City, newItem: City) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onCityClicked(city: City)
    }
}