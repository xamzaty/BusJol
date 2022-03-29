package kz.busjol.ui.bus_plan

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.data.BusPlan
import kz.busjol.databinding.ItemSeatBinding

class BusPlanViewHolder(private val binding: ItemSeatBinding, private val listener: BusPlanAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(seat: BusPlan) {
        binding.seatCb.text = seat.place.toString()
        binding.seatCb.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                binding.seatCb.setTextColor(Color.WHITE)
                listener.onSeatClicked(seat)
            }
            else binding.seatCb.setTextColor(Color.BLACK)
        }
    }
}