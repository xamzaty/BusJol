package kz.busjol.ui.trip

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.data.Trip
import kz.busjol.databinding.ItemTripBinding

class TripViewHolder(private val binding: ItemTripBinding, private val listener: TripListAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trip: Trip) {
        binding.apply {
            tripLayout.setOnClickListener { listener.onCityClicked(trip) }
            tripNumber.text = itemView.context.getString(R.string.trip_number, trip.tripCount)
            fromCity.text = trip.fromCity
            toCity.text = trip.toCity
            tripType.text = itemView.context.getString(R.string.seat_type, trip.type)
            tripFreeSeats.text = itemView.context.getString(R.string.free_seats, trip.freeSeats, trip.allSeats)
            tripFerryman.text = itemView.context.getString(R.string.carrier, trip.carrier)
            tripPrice.text = trip.displayAmount()
        }
    }
}