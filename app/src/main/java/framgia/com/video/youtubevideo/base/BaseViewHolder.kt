package framgia.com.video.youtubevideo.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<Item,
        ItemViewDataBinding : ViewDataBinding>(
        itemBinding: ItemViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    abstract fun bindData(item: Item);
}
