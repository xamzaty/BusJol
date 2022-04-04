package kz.busjol.ui.trip

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TripItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)

        if (position == 1) parent.paddingTop
        if (position == state.itemCount - 1) outRect.bottom = 30
        else super.getItemOffsets(outRect, view, parent, state)
    }
}