package kz.busjol.ui.journey

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.databinding.ItemJourneyBinding
import kz.busjol.domain.model.Journey

class JourneyViewHolder(private val binding: ItemJourneyBinding, private val listener: JourneyListAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(journey: Journey) {
        val journeyItem = journey.journey

        binding.apply {
            tripLayout.setOnClickListener { listener.onCityClicked(journey) }
            tripNumber.text = itemView.context.getString(R.string.trip_number, journeyItem?.code)
//            fromCity.text = "asd"
//            toCity.text = "sdf"
//            tripType.text = itemView.context.getString(R.string.seat_type, journey.type)
            tripFreeSeats.text = itemView.context.getString(R.string.free_seats, journey.numberOfFreePlaces, journey.numberOfPlaces)
//            tripFerryman.text = journey.carrier
            tripPrice.text = journey.displayAmount()
        }
    }
}