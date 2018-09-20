package framgia.com.video.youtubevideo.screen.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentFavoriteBinding
import framgia.com.video.youtubevideo.screen.favorite.adapter.FavoriteAdapter
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.main.MainViewModel
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import framgia.com.video.youtubevideo.utils.initViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(),
        SwipeRefreshLayout.OnRefreshListener {
    lateinit var activityViewModel: MainViewModel

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                activityViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            }
        }
    }

    override fun initComponent(viewBinding: FragmentFavoriteBinding) {
        activityViewModel.apply {
            isVisibleBackButton.value = false
            titleMain.value = getString(R.string.title_favorite)
        }
        viewModel = initViewModel(FavoriteViewModel::class.java)
        viewBinding.swipeRefresh.setOnRefreshListener(this)
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
        viewModel.isRefresh.observe(this, Observer {
            viewBinding.swipeRefresh.apply { isRefreshing = it == true }
        })
    }

    fun playVideo(video: Video) {
        val mainActivity = activity as MainActivity
        mainActivity.replaceFragment(PlayVideoFragment.newInstance(video), R.id.container,
                FragmentBackstackConstant.TAG_PLAY_VIDEO_FRAGMENT)
    }

    override fun onRefresh() = viewModel.refreshData()

    override fun getLayoutResource(): Int = R.layout.fragment_favorite
}
