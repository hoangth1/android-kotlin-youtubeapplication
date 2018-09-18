package framgia.com.video.youtubevideo.screen.search.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseRecyclerViewAdapter
import framgia.com.video.youtubevideo.base.BaseViewHolder
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.ItemSearchResultBinding

class SearchResultAdapter(mData: List<Video>,
                          val onItemClick: (Video) -> Unit,
                          val onPopupClick: (Video, View) -> Unit) : BaseRecyclerViewAdapter<Video,
        ItemSearchResultBinding, SearchResultAdapter.ViewHolder>(mData) {
    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemResultBinding: ItemSearchResultBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.item_search_result, parent, false)
        return ViewHolder(itemResultBinding.apply { bindFirstTime(this) })
    }

    override fun bindFirstTime(itemViewDataBinding: ItemSearchResultBinding) {
        super.bindFirstTime(itemViewDataBinding)
        itemViewDataBinding.apply {
            root.setOnClickListener {
                video?.apply {
                    onItemClick.invoke(this)
                }
            }
            imagePopupMenu.setOnClickListener {
                video?.apply {
                    onPopupClick.invoke(this, it)
                }
            }
        }
    }

    inner class ViewHolder(itemBinding: ItemSearchResultBinding) : BaseViewHolder<Video, ItemSearchResultBinding>(itemBinding) {
        override fun bindData(item: Video) {
            itemBinding.video = item
        }
    }
}
