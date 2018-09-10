package framgia.com.video.youtubevideo.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerViewAdapter<Item,
        ItemViewDataBinding : ViewDataBinding,
        ViewHolder : BaseViewHolder<Item, ItemViewDataBinding>>(
        var mData: MutableList<Item>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = getViewHolder(parent, viewType)

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
}
