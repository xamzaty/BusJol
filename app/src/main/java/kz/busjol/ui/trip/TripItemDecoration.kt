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
        if (parent.getChildLayoutPosition(view) == 0) outRect.top = 50

        if (parent.getChildLayoutPosition(view) == state.itemCount - 1) outRect.bottom = 30
        else super.getItemOffsets(outRect, view, parent, state)
    }
}