package framgia.com.video.youtubevideo.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    var isLoadding = false
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView?.layoutManager is LinearLayoutManager) {
            (recyclerView.layoutManager as LinearLayoutManager).apply {
                val pastItem = findFirstVisibleItemPosition()
                val visibleItem = childCount
                val totalItem = itemCount
                if (!isLoadding && pastItem + visibleItem >= totalItem) {
                    onLoadMore.invoke()
                    isLoadding = true
                }
            }
        }
    }
}
