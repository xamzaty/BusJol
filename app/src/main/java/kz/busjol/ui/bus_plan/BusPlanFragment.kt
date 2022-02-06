package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBusPlanBinding

class BusPlanFragment : BaseFragment<FragmentBusPlanBinding>(FragmentBusPlanBinding::inflate) {

    private lateinit var busPlanViewModel: BusPlanViewModel
}