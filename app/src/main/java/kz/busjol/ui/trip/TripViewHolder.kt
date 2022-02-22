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
            tripTime.text = trip.time
            tripDate.text = trip.date
            tripNumber.text = itemView.context.getString(R.string.trip_number, trip.tripCount)
            tripDirection.text = trip.cities
            tripType.text = itemView.context.getString(R.string.seat_type, trip.type)
            tripFreeSeats.text = itemView.context.getString(R.string.free_seats, trip.freeSeats, trip.allSeats)
            tripFerryman.text = itemView.context.getString(R.string.carrier, trip.carrier)
            tripPrice.text = trip.displayAmount()
        }
    }
}