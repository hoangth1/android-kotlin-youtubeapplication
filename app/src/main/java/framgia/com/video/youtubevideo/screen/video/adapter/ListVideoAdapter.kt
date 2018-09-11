package framgia.com.video.youtubevideo.screen.video.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseRecyclerViewAdapter
import framgia.com.video.youtubevideo.base.BaseViewHolder
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.ItemVideoBinding
import framgia.com.video.youtubevideo.screen.video.HandlerClick
import framgia.com.video.youtubevideo.screen.video.OnItemVideoClick

class ListVideoAdapter(mData: List<Video>, val listener: OnItemVideoClick) : BaseRecyclerViewAdapter<Video,
        ItemVideoBinding, ListVideoAdapter.ViewHolder>(mData) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemVideoBinding: ItemVideoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video,
                parent,
                false
        )
        itemVideoBinding.handleClick = HandlerClick(listener)
        return ViewHolder(itemVideoBinding)
    }

    class ViewHolder(itemVideoBinding: ItemVideoBinding) : BaseViewHolder<Video,
            ItemVideoBinding>(itemVideoBinding) {
        override fun bindData(item: Video) {
            itemBinding.video = item
        }

    }
}
