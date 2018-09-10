package framgia.com.video.youtubevideo.screen.video.adapter

import android.databinding.DataBindingUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseRecyclerViewAdapter
import framgia.com.video.youtubevideo.base.BaseViewHolder
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.ItemVideoBinding

class ListVideoAdapter(mData: List<Video>) : BaseRecyclerViewAdapter<Video,
        ItemVideoBinding, ListVideoAdapter.ViewHolder>(mData) {
    init {


    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("kiemtra", mData[0].mSnipper.mTitle)
        val itemVideoBinding: ItemVideoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video,
                parent,
                false
        )
        return ViewHolder(itemVideoBinding)
    }

    class ViewHolder(itemVideoBinding: ItemVideoBinding) : BaseViewHolder<Video,
            ItemVideoBinding>(itemVideoBinding) {
        override fun bindData(item: Video) {
            itemBinding.video = item
        }

    }
}
