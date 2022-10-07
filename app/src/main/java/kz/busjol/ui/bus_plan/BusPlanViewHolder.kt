package kz.busjol.ui.bus_plan

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.data.network.model.BusPlan
import kz.busjol.databinding.ItemSeatBinding

class BusPlanViewHolder(
    private val binding: ItemSeatBinding,
    private val listener: BusPlanAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(seat: BusPlan) {
        binding.seatCb.text = seat.place.toString()

        if (seat.isSeatBusy) {
            binding.seatCb.setBackgroundResource(R.drawable.ic_busy_seat_layout)
        }

        binding.seatCb.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked && !seat.isSeatBusy) {
                listener.addSeat(seat)
                binding.seatCb.setTextColor(Color.WHITE)
            } else {
                listener.removeSeat(seat)
                binding.seatCb.setTextColor(Color.BLACK)
            }
        }
    }
}