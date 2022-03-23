package kz.busjol.ui.bus_plan

import androidx.recyclerview.widget.RecyclerView
import kz.busjol.data.BusPlan
import kz.busjol.databinding.ItemSeatBinding

class BusPlanViewHolder(private val binding: ItemSeatBinding, private val listener: BusPlanAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(seat: BusPlan) {
        binding.seatCb.text = seat.place.toString()
        binding.seatItem.setOnClickListener {
            listener.onSeatClicked(seat)
        }
    }
}