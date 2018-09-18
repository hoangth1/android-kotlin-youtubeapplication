package framgia.com.video.youtubevideo.screen.favorite.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseRecyclerViewAdapter
import framgia.com.video.youtubevideo.base.BaseViewHolder
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.ItemFavoriteBinding

class FavoriteAdapter(mData: List<Video>,
                      val onItemClick: (Video) -> Unit) : BaseRecyclerViewAdapter<Video,
        ItemFavoriteBinding, FavoriteAdapter.ViewHolder>(mData) {
    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemResultBinding: ItemFavoriteBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.item_favorite, parent, false)
        return ViewHolder(itemResultBinding.apply { bindFirstTime(this) })
    }

    override fun bindFirstTime(itemViewDataBinding: ItemFavoriteBinding) {
        super.bindFirstTime(itemViewDataBinding)
        itemViewDataBinding.apply {
            root.setOnClickListener {
                video.apply {
                    this?.let { onItemClick.invoke(this@apply) }
                }
            }
        }
    }

    inner class ViewHolder(itemBinding: ItemFavoriteBinding) : BaseViewHolder<Video, ItemFavoriteBinding>(itemBinding) {
        override fun bindData(item: Video) {
            itemBinding.video = item
        }
    }
}
