package kz.busjol.ui.bus_plan

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.data.BusPlan
import kz.busjol.databinding.ItemSeatBinding

class BusPlanViewHolder(private val binding: ItemSeatBinding, private val listener: BusPlanAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(seat: BusPlan) {
        binding.seatCb.text = seat.place.toString()
        binding.seatCb.setOnCheckedChangeListener { compoundButton, _ ->
            listener.onSeatClicked(seat)
            if (compoundButton.isChecked) binding.seatCb.setTextColor(Color.WHITE)
            else binding.seatCb.setTextColor(Color.BLACK)
        }
    }
}