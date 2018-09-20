package framgia.com.video.youtubevideo.screen.playvideo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.base.EndlessScrollListener
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.databinding.FragmentPlayVideoBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.main.MainViewModel
import framgia.com.video.youtubevideo.screen.playvideo.adapter.RelatedVideoAdapter
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import framgia.com.video.youtubevideo.utils.initViewModel

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding, PlayVideoViewModel>(),
        YouTubePlayer.OnInitializedListener {
    lateinit var activityViewModel: MainViewModel
    var youtubePlayer: YouTubePlayer? = null

    companion object {
        private const val BUNDLE_VIDEO = "video"
        fun newInstance(video: Video): PlayVideoFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_VIDEO, video)
            val playVideoFragment = PlayVideoFragment().apply {
                arguments = bundle
            }
            return playVideoFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                activityViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            }
        }
    }

    override fun initComponent(viewBinding: FragmentPlayVideoBinding) {
        setHasOptionsMenu(true)
        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore() }
        viewBinding.recyclerRelatedVideo.addOnScrollListener(endlessScrollListener)
        viewModel = initViewModel(PlayVideoViewModel::class.java)
        viewBinding.playVideoModel = viewModel
        val youtubePlayerFragment = YouTubePlayerSupportFragment()
        replaceFragmentNotBackstack(youtubePlayerFragment, R.id.container_video)
        youtubePlayerFragment.initialize(Api.KEY, this)
        viewModel.setVideoData(arguments?.get(BUNDLE_VIDEO) as Video)
        viewModel.checkVideoAddedFavorite(arguments?.get(BUNDLE_VIDEO) as Video)
        viewModel.listVideo.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            val relatedVideoAdapter = RelatedVideoAdapter(it, itemClickListener = { playSelectedVideo(it) },
                    onPopupClick = ::showPopupMenu)
            val linearLayoutManager = LinearLayoutManager(context)
            viewBinding.recyclerRelatedVideo.apply {
                adapter = relatedVideoAdapter
                layoutManager = linearLayoutManager
            }
        })
        viewModel.loadError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.listVideoAdd.observe(this, Observer {
            (viewBinding.recyclerRelatedVideo.adapter as RelatedVideoAdapter).apply {
                addData(it)
            }
        })
        viewModel.isLoadMore.observe(this, Observer {
            it?.apply {
                endlessScrollListener.isLoadding = it
            }
        })

    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        youtubePlayer = p1
        viewModel.videoPlay.observe(this, Observer {
            if (!p2) p1?.cueVideo(it?.mId)
            viewModel.apply { loadRelatedVideo(firstPage) }
            activityViewModel.apply {
                isVisibleBackButton.value = true
                titleMain.value = it?.mSnipper?.mTitle
            }

        })
    }

    fun playSelectedVideo(video: Video) {
        viewModel.setVideoData(video)
    }

    fun showPopupMenu(video: Video, view: View) {
        addPopupMenu(R.menu.popup_menu_video, view).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_addfavorite -> viewModel.addFavorite(video)
                R.id.item_removefavorite -> viewModel.deleteFavorite(video)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.apply {
            clear()
            inflater?.inflate(R.menu.fragment_play_video, this)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        viewModel.isFavorite.observe(this, Observer {
            menu?.findItem(R.id.item_favorite).apply {
                this?.setIcon(if (it == true) R.drawable.ic_favorite_fill_red else R.drawable.ic_favorite)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_favorite -> {
                viewModel.isFavorite.value.apply {
                    if (this == null) return false
                    when {
                        this -> viewModel.apply {
                            deleteFavorite(videoPlay.value)
                        }
                        else -> viewModel.apply {
                            addFavorite(videoPlay.value)
                        }
                    }
                }
            }
            android.R.id.home -> fragmentManager?.popBackStack(FragmentBackstackConstant.TAG_PLAY_VIDEO_FRAGMENT,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_play_video

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) youtubePlayer?.setFullscreen(true)
    }
}
