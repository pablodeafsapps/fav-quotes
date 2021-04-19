package org.deafsapps.android.favquotes.presentationlayer.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val UPDATE_TIME_THRESHOLD = 500

/**
 *
 */
fun rvOnEndOfScrollListener(lManager: LinearLayoutManager, callback: () -> Unit) =
    object : RecyclerView.OnScrollListener() {

        var lastUpdate: Long = System.currentTimeMillis()

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                lastUpdate = System.currentTimeMillis()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!hasUpdatedRecently(lastUpdate) &&
                isAtTheEndOfTheScroll(lManager) &&
                isScrollingDown(dy)
            ) {
                lastUpdate = System.currentTimeMillis()
                callback.invoke()
            }
        }

        private fun hasUpdatedRecently(lastUpdate: Long) =
            System.currentTimeMillis() - lastUpdate < UPDATE_TIME_THRESHOLD

        private fun isAtTheEndOfTheScroll(lManager: LinearLayoutManager) =
            lManager.findLastVisibleItemPosition() == lManager.itemCount - 1

        private fun isScrollingDown(yShift: Int) = yShift > 0

    }
