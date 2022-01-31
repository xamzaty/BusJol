package kz.busjol.base

import android.view.View

interface AdapterListener {

    fun onItemClick(item: Any) {}

    fun onItemActionClick(item: Any) {}

    fun onItemClickWithTransition(item: Any, v: View) {}
}