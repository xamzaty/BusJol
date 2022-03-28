package kz.busjol.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = 50
        }
        if (parent.getChildLayoutPosition(view) == 4 || parent.getChildLayoutPosition(view) == 8 || parent.getChildLayoutPosition(view) == 12 ||
            parent.getChildLayoutPosition(view) == 16 || parent.getChildLayoutPosition(view) == 20 || parent.getChildLayoutPosition(view) == 24 ||
            parent.getChildLayoutPosition(view) == 28 || parent.getChildLayoutPosition(view) == 32 || parent.getChildLayoutPosition(view) == 36 ||
            parent.getChildLayoutPosition(view) == 40 || parent.getChildLayoutPosition(view) == 44 || parent.getChildLayoutPosition(view) == 48 ||
            parent.getChildLayoutPosition(view) == 52 || parent.getChildLayoutPosition(view) == 56 || parent.getChildLayoutPosition(view) == 60 ||
            parent.getChildLayoutPosition(view) == 64 || parent.getChildLayoutPosition(view) == 68 || parent.getChildLayoutPosition(view) == 72 ||
            parent.getChildLayoutPosition(view) == 76 || parent.getChildLayoutPosition(view) == 80 || parent.getChildLayoutPosition(view) == 84 ||
            parent.getChildLayoutPosition(view) == 88 || parent.getChildLayoutPosition(view) == 92 || parent.getChildLayoutPosition(view) == 96 ||
            parent.getChildLayoutPosition(view) == 100 || parent.getChildLayoutPosition(view) == 104 || parent.getChildLayoutPosition(view) == 108 ||
            parent.getChildLayoutPosition(view) == 112 || parent.getChildLayoutPosition(view) == 116 || parent.getChildLayoutPosition(view) == 120 ||
            parent.getChildLayoutPosition(view) == 124 || parent.getChildLayoutPosition(view) == 128 || parent.getChildLayoutPosition(view) == 132 ||
            parent.getChildLayoutPosition(view) == 136 || parent.getChildLayoutPosition(view) == 140 || parent.getChildLayoutPosition(view) == 144) {
                outRect.top = 15
        }

        if (parent.getChildLayoutPosition(view) == 1) {
            outRect.top = 50
            outRect.left = -20
        }
        if (parent.getChildLayoutPosition(view) == 5 || parent.getChildLayoutPosition(view) == 9 || parent.getChildLayoutPosition(view) == 13 ||
            parent.getChildLayoutPosition(view) == 17 || parent.getChildLayoutPosition(view) == 21 || parent.getChildLayoutPosition(view) == 25 ||
            parent.getChildLayoutPosition(view) == 29 || parent.getChildLayoutPosition(view) == 33 || parent.getChildLayoutPosition(view) == 37 ||
            parent.getChildLayoutPosition(view) == 41 || parent.getChildLayoutPosition(view) == 45 || parent.getChildLayoutPosition(view) == 49 ||
            parent.getChildLayoutPosition(view) == 53 || parent.getChildLayoutPosition(view) == 57 || parent.getChildLayoutPosition(view) == 61 ||
            parent.getChildLayoutPosition(view) == 65 || parent.getChildLayoutPosition(view) == 69 || parent.getChildLayoutPosition(view) == 73 ||
            parent.getChildLayoutPosition(view) == 77 || parent.getChildLayoutPosition(view) == 81 || parent.getChildLayoutPosition(view) == 85 ||
            parent.getChildLayoutPosition(view) == 89 || parent.getChildLayoutPosition(view) == 93 || parent.getChildLayoutPosition(view) == 97 ||
            parent.getChildLayoutPosition(view) == 101 || parent.getChildLayoutPosition(view) == 105 || parent.getChildLayoutPosition(view) == 109 ||
            parent.getChildLayoutPosition(view) == 113 || parent.getChildLayoutPosition(view) == 117 || parent.getChildLayoutPosition(view) == 121 ||
            parent.getChildLayoutPosition(view) == 125 || parent.getChildLayoutPosition(view) == 129 || parent.getChildLayoutPosition(view) == 133 ||
            parent.getChildLayoutPosition(view) == 137) {
            outRect.top = 15
            outRect.left = -20
        }

        if (parent.getChildLayoutPosition(view) == 2) {
            outRect.left = 40
            outRect.top = 50
        }
        if (parent.getChildLayoutPosition(view) == 6 || parent.getChildLayoutPosition(view) == 10 || parent.getChildLayoutPosition(view) == 14 ||
            parent.getChildLayoutPosition(view) == 18 || parent.getChildLayoutPosition(view) == 22 || parent.getChildLayoutPosition(view) == 26 ||
            parent.getChildLayoutPosition(view) == 30 || parent.getChildLayoutPosition(view) == 34 || parent.getChildLayoutPosition(view) == 38 ||
            parent.getChildLayoutPosition(view) == 42 || parent.getChildLayoutPosition(view) == 46 || parent.getChildLayoutPosition(view) == 50 ||
            parent.getChildLayoutPosition(view) == 54 || parent.getChildLayoutPosition(view) == 58 || parent.getChildLayoutPosition(view) == 62 ||
            parent.getChildLayoutPosition(view) == 66 || parent.getChildLayoutPosition(view) == 70 || parent.getChildLayoutPosition(view) == 74 ||
            parent.getChildLayoutPosition(view) == 78 || parent.getChildLayoutPosition(view) == 82 || parent.getChildLayoutPosition(view) == 86 ||
            parent.getChildLayoutPosition(view) == 90 || parent.getChildLayoutPosition(view) == 94 || parent.getChildLayoutPosition(view) == 98 ||
            parent.getChildLayoutPosition(view) == 102 || parent.getChildLayoutPosition(view) == 106 || parent.getChildLayoutPosition(view) == 110 ||
            parent.getChildLayoutPosition(view) == 114 || parent.getChildLayoutPosition(view) == 118 || parent.getChildLayoutPosition(view) == 122 ||
            parent.getChildLayoutPosition(view) == 126 || parent.getChildLayoutPosition(view) == 130 || parent.getChildLayoutPosition(view) == 134 ||
            parent.getChildLayoutPosition(view) == 138 || parent.getChildLayoutPosition(view) == 142) {
            outRect.left = 40
            outRect.top = 15
        }

        if (parent.getChildLayoutPosition(view) == 3) {
            outRect.top = 50
            outRect.left = 20
        }
        if (parent.getChildLayoutPosition(view) == 7 || parent.getChildLayoutPosition(view) == 11 || parent.getChildLayoutPosition(view) == 15 ||
            parent.getChildLayoutPosition(view) == 19 || parent.getChildLayoutPosition(view) == 23 || parent.getChildLayoutPosition(view) == 27 ||
            parent.getChildLayoutPosition(view) == 31 || parent.getChildLayoutPosition(view) == 35 || parent.getChildLayoutPosition(view) == 39 ||
            parent.getChildLayoutPosition(view) == 43 || parent.getChildLayoutPosition(view) == 47 || parent.getChildLayoutPosition(view) == 51 ||
            parent.getChildLayoutPosition(view) == 55 || parent.getChildLayoutPosition(view) == 59 || parent.getChildLayoutPosition(view) == 63 ||
            parent.getChildLayoutPosition(view) == 67 || parent.getChildLayoutPosition(view) == 71 || parent.getChildLayoutPosition(view) == 75 ||
            parent.getChildLayoutPosition(view) == 79 || parent.getChildLayoutPosition(view) == 83 || parent.getChildLayoutPosition(view) == 87 ||
            parent.getChildLayoutPosition(view) == 91 || parent.getChildLayoutPosition(view) == 95 || parent.getChildLayoutPosition(view) == 99 ||
            parent.getChildLayoutPosition(view) == 103 || parent.getChildLayoutPosition(view) == 107 || parent.getChildLayoutPosition(view) == 111 ||
            parent.getChildLayoutPosition(view) == 115 || parent.getChildLayoutPosition(view) == 119 || parent.getChildLayoutPosition(view) == 123 ||
            parent.getChildLayoutPosition(view) == 127 || parent.getChildLayoutPosition(view) == 131 || parent.getChildLayoutPosition(view) == 135 ||
            parent.getChildLayoutPosition(view) == 139 || parent.getChildLayoutPosition(view) == 143 || parent.getChildLayoutPosition(view) == 147) {
            outRect.top = 15
            outRect.left = 20
        }
    }
}