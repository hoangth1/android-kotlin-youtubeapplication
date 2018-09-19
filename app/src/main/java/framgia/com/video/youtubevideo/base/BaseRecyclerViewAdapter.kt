package framgia.com.video.youtubevideo.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerViewAdapter<Item,
        ItemViewDataBinding : ViewDataBinding,
        ViewHolder : BaseViewHolder<Item, ItemViewDataBinding>>(
        mData: List<Item>) : RecyclerView.Adapter<ViewHolder>() {
    val listData = arrayListOf<Item>()

    init {
        listData.addAll(mData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            getViewHolder(parent, viewType)

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    open fun bindFirstTime(itemViewDataBinding: ItemViewDataBinding) {}
    fun addData(data: List<Item>?) {
        data?.apply {
            listData.addAll(this)
            notifyItemRangeChanged(listData.size, data.size)
        }

    }
}
