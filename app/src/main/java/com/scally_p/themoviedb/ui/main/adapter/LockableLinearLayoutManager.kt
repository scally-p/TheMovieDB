package com.scally_p.themoviedb.ui.main.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LockableLinearLayoutManager : LinearLayoutManager {

    private val tag = LockableLinearLayoutManager::class.java.simpleName

    var isScrollable = true
        private set
    var overScrollListener: OverScrollListener? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    fun setScrollingEnabled(enabled: Boolean) {
        isScrollable = enabled
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return isScrollable
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val scrollRange = super.scrollVerticallyBy(dy, recycler, state)
        val overScroll = dy - scrollRange
        if (overScroll > 0) {
            overScrollListener!!.onBottomOverScroll()
        } else if (overScroll < 0) {
            overScrollListener!!.onTopOverScroll()
        }
        return scrollRange
    }

    fun setRecyclerViewOverScrollListener(overScrollListener: OverScrollListener) {
        this.overScrollListener = overScrollListener
    }

    interface OverScrollListener {
        fun onBottomOverScroll()
        fun onTopOverScroll()
    }
}