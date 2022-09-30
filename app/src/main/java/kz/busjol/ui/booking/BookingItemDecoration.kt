package kz.busjol.ui.booking

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BookingItemDecoration  : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = 25
    }
}