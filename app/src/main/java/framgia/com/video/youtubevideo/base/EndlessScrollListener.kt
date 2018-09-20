package framgia.com.video.youtubevideo.base

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    var isLoadding = false
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var pastItem = 0
        var visibleItem = 0
        var totalItem = 0
        when (recyclerView?.layoutManager) {
            is LinearLayoutManager -> {
                (recyclerView.layoutManager as LinearLayoutManager).apply {
                    pastItem = findFirstVisibleItemPosition()
                    visibleItem = childCount
                    totalItem = itemCount
                }
            }
            is GridLayoutManager -> {
                (recyclerView.layoutManager as GridLayoutManager).apply {
                    pastItem = findFirstVisibleItemPosition()
                    visibleItem = childCount
                    totalItem = itemCount
                }
            }
        }
        if (!isLoadding && pastItem + visibleItem >= totalItem) {
            onLoadMore.invoke()
            isLoadding = true
        }
    }
}
