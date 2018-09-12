package framgia.com.video.youtubevideo.screen.playvideo.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseRecyclerViewAdapter
import framgia.com.video.youtubevideo.base.BaseViewHolder
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.ItemRelatedVideoBinding

class RelatedVideoAdapter(mData: List<Video>,
                          val itemClickListener: (Video) -> Unit) : BaseRecyclerViewAdapter<Video,
        ItemRelatedVideoBinding, RelatedVideoAdapter.ViewHolder>(mData) {


    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemVideoBinding: ItemRelatedVideoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_related_video,
                parent, false
        )
        return ViewHolder(itemVideoBinding.apply { bindFirstTime(this) })
    }

    override fun bindFirstTime(itemViewDataBinding: ItemRelatedVideoBinding) {
        itemViewDataBinding.apply {
            root.setOnClickListener(View.OnClickListener {
                itemClickListener.invoke(video!!)
            })
        }
    }

    inner class ViewHolder(itemVideoBinding: ItemRelatedVideoBinding) : BaseViewHolder<Video,
            ItemRelatedVideoBinding>(itemVideoBinding) {
        override fun bindData(item: Video) {
            itemBinding.video = item
        }
    }
}
