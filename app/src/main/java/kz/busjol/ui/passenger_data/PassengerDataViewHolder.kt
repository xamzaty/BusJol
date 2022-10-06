package kz.busjol.ui.passenger_data

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.databinding.ItemPassengerFormBinding
import kz.busjol.ui.passenger_quantity.Passenger

class PassengerDataViewHolder(
    private val binding: ItemPassengerFormBinding,
    private val activity: FragmentActivity,
    private val listener: PassengerDataAdapter.CheckFields
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(passenger: Passenger) {
        binding.apply {
            form.apply {
                setupFields(
                    activity, isChildType = passenger.isChild(), isDisabled = passenger.isDisabled(),
                    passengerNumber = adapterPosition + 1
                )
                listener.checkIfAllFieldsFilled(isAllFieldsFilled())
            }
        }
    }
}