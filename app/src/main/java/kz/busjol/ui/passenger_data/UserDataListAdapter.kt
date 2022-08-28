package kz.busjol.ui.passenger_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R
import kz.busjol.data.network.model.UserForm
import kz.busjol.databinding.CustomFormBinding

class UserDataListAdapter(
    private val listener: TextFieldListener
) : ListAdapter<UserForm, UserDataListAdapter.UserDataListViewHolder>(UserDataListComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataListViewHolder {
        val binding = CustomFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDataListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserDataListViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class UserDataListComparator : DiffUtil.ItemCallback<UserForm>(){
        override fun areItemsTheSame(oldItem: UserForm, newItem: UserForm) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UserForm, newItem: UserForm) =
            oldItem == newItem
    }

    interface TextFieldListener {
        fun checkFields(isAnyFieldEmpty: Boolean)
    }

    inner class UserDataListViewHolder(
        private val binding: CustomFormBinding, private val listener: TextFieldListener
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userForm: UserForm) {
            binding.apply {
                if (userForm.isChild) binding.datePicker.visibility = View.VISIBLE

                binding.apply {
                    if (iinEt.isEmpty() || lastnameEt.isEmpty() || firstnameEt.isEmpty() || datePicker.isEmpty()) {
                        listener.checkFields(false)
                    } else {
                        listener.checkFields(true)
                    }

                    passengerNumberText.text = itemView.context.getString(R.string.passenger, layoutPosition + 1)
                }
            }
        }
    }
}

