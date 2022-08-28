package kz.busjol.ui.journey

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.databinding.ItemJourneyBinding
import kz.busjol.domain.model.Journey

class JourneyViewHolder(private val binding: ItemJourneyBinding, private val listener: JourneyListAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(journey: Journey) {
        binding.apply {
//            tripLayout.setOnClickListener { listener.onCityClicked(journeyResponse) }
//            tripNumber.text = itemView.context.getString(R.string.trip_number, journeyResponse.tripCount)
//            fromCity.text = journeyResponse.fromCity
//            toCity.text = journeyResponse.toCity
//            tripType.text = itemView.context.getString(R.string.seat_type, journeyResponse.type)
//            tripFreeSeats.text = itemView.context.getString(R.string.free_seats, journeyResponse.freeSeats, journeyResponse.allSeats)
//            tripFerryman.text = journeyResponse.carrier
//            tripPrice.text = journeyResponse.displayAmount()
        }
    }
}