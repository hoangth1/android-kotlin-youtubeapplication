package framgia.com.video.youtubevideo.screen.favorite

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentFavoriteBinding
import framgia.com.video.youtubevideo.screen.favorite.adapter.FavoriteAdapter
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.utils.initViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun initComponent(viewBinding: FragmentFavoriteBinding) {
        viewModel = initViewModel(FavoriteViewModel::class.java)
        viewModel.listFavorite.observe(this, Observer {
            if (it == null) return@Observer
            val favoriteAdapter = FavoriteAdapter(it, onItemClick = { playVideo(it) })
            viewBinding.recyclerFavorite.apply {
                adapter = favoriteAdapter
                layoutManager = LinearLayoutManager(context)
            }
        })
        viewModel.loadError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.loadFavoriteVideo()
    }

    fun playVideo(video: Video) {
        val mainActivity = activity as MainActivity
        mainActivity.addFragment(PlayVideoFragment.newInstance(video), R.id.container, "")
    }

    override fun getLayoutResource(): Int = R.layout.fragment_favorite
}
