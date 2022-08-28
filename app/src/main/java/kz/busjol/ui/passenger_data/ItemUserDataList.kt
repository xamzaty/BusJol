package kz.busjol.ui.passenger_data

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import kz.busjol.databinding.ItemUserDataListBinding

class ItemUserDataList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), UserDataListAdapter.TextFieldListener {

    private var _binding: ItemUserDataListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter = UserDataListAdapter(this)

    init {
        _binding = ItemUserDataListBinding.inflate(LayoutInflater.from(context), this, true)
        initRv()
    }

    override fun checkFields(isAnyFieldEmpty: Boolean) {
//        TODO("Not yet implemented")
    }

    private fun initRv() {
        binding.rv.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    fun initData(viewModel: PassengerDataViewModel, fragment: PassengerDataFragment) {
        fragment.apply {
            viewModel.userList.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
            }
        }
    }
}