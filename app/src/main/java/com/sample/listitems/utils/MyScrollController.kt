package com.sample.listitems.utils

import androidx.recyclerview.widget.RecyclerView

abstract class MyScrollController : RecyclerView.OnScrollListener() {
    val MINIMUM = 25;
    var ScrollDist = 0;
    var isVisible = true;

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isVisible && ScrollDist > MINIMUM) {
            show()
            ScrollDist = 0
            isVisible = false
        } else if (!isVisible && ScrollDist < -MINIMUM) {
            hide()
            ScrollDist = 0; isVisible = true;
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            ScrollDist += dy
        }
    }

    abstract fun hide()

    abstract fun show()
}