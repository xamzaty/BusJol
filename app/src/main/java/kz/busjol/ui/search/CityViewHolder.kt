package kz.busjol.ui.search

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.data.City
import kz.busjol.databinding.ItemCitySelectorBinding

class CityViewHolder(private val binding: ItemCitySelectorBinding, private val listener: CityListAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
        binding.textCity.apply {
            text = city.name
        }
        binding.cityItem.setOnClickListener {
            listener.onCityClicked(city)
        }
    }
}