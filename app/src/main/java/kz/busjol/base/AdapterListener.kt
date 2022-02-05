package kz.busjol.base

import android.view.View

interface AdapterListener {

    fun onItemClick(position: Int) {}

    fun onItemActionClick(item: Any) {}

    fun onItemClickWithTransition(item: Any, v: View) {}
}