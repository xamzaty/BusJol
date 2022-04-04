package kz.busjol.ui.bus_plan

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BusPlanItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        addPadding(true, outRect, view, parent, state, 0, 50, 0)
        addPadding(false, outRect, view, parent, state, 4, 15, 0)
        addPadding(true, outRect, view, parent, state, 1, 50, -20)
        addPadding(false, outRect, view, parent, state, 5, 15, -20)
        addPadding(true, outRect, view, parent, state, 2, 50, 40)
        addPadding(false, outRect, view, parent, state, 6, 15, 40)
        addPadding(true, outRect, view, parent, state, 3, 50, 20)
        addPadding(false, outRect, view, parent, state, 7, 15, 20)
    }

    private fun addPadding(
        firstRow: Boolean, outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State,
        positionNumber: Int, paddingTop: Int, paddingLeft: Int
    ) {
        val position = parent.getChildLayoutPosition(view)
        val stepNumber = 4

        if (!firstRow) {
            for (i in positionNumber..state.itemCount step stepNumber) {
                if (position == i) {
                    outRect.top = paddingTop
                    outRect.left = paddingLeft
                } else if (position == state.itemCount - 1) {
                    outRect.bottom = 50
                }
            }
        } else {
            if (position == positionNumber) {
                outRect.top = paddingTop
                outRect.left = paddingLeft
            }  else if (position == state.itemCount - 1) {
                outRect.bottom = 50
            }
        }
    }
}