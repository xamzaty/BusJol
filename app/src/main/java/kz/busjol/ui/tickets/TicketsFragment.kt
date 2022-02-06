package kz.busjol.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentTicketsBinding

class TicketsFragment : BaseFragment<FragmentTicketsBinding>(FragmentTicketsBinding::inflate) {

    private lateinit var ticketsViewModel: TicketsViewModel
}