package kz.busjol.ui.city_picker

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.databinding.ItemCitySelectorBinding
import kz.busjol.domain.model.City

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