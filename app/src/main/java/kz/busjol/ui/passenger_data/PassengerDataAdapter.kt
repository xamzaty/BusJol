package kz.busjol.ui.passenger_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import kz.busjol.databinding.ItemPassengerFormBinding
import kz.busjol.ui.passenger_quantity.Passenger

class PassengerDataAdapter(
    private val listener: CheckFields
) : ListAdapter<Passenger, PassengerDataViewHolder>(PassengerComparator()) {

    private lateinit var activity: FragmentActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerDataViewHolder {
        val binding = ItemPassengerFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerDataViewHolder(binding, activity, listener)
    }

    override fun onBindViewHolder(holder: PassengerDataViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    fun getActivity(value: FragmentActivity) {
        activity = value
    }

    class PassengerComparator : DiffUtil.ItemCallback<Passenger>() {
        override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger) =
            oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger) =
            oldItem.type == newItem.type
    }

    interface CheckFields {
        fun checkIfAllFieldsFilled(isAllFilled: Boolean)
    }
}