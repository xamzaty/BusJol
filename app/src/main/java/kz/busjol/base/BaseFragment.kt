package kz.busjol.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import kz.busjol.R
import kz.busjol.ext.FragmentExt.hideKeyboard
import kz.busjol.ui.trip.TripViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    @MenuRes
    open var toolbarMenuId: Int? = null
    open var menuItemClickLister: Toolbar.OnMenuItemClickListener? = null
    protected var toolbarParent: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initToolbar() {
//        view?.findViewById<Toolbar>(R.id.toolbar_parent)?.apply {
//            toolbarParent = this
//
//            val navController = findNavController()
//            val appBarConfiguration = AppBarConfiguration(navController.graph)
//            setupWithNavController(navController, appBarConfiguration)
//
//            toolbarMenuId?.let { inflateMenu(it) }
//            menuItemClickLister?.let { setOnMenuItemClickListener(menuItemClickLister) }
//            setNavigationOnClickListener {
//                hideKeyboard()
//                activity?.onBackPressed()
//            }
//        }
    }
}