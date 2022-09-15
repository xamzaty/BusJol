package kz.busjol.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kz.busjol.R
import kz.busjol.utils.Regex

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkEmail(str: String) = Regex.checkValidEmail(str, activity, R.string.enter_valid_email)

    fun onError() {
        val toast = Toast.makeText(activity, getString(R.string.error_server), Toast.LENGTH_SHORT)
        toast.show()
    }

    fun <T> onBackStackEntry(key: String, body: (T) -> Unit) {
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<T>(key)?.observe(viewLifecycleOwner) { body(it) }
    }
}