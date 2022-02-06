package kz.busjol.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentContactsBinding
import kz.busjol.ui.user.UserViewModel

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val contactsViewModel: ContactsViewModel by viewModels()
}