package framgia.com.video.youtubevideo.screen.video

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.base.EndlessScrollListener
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentVideoBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.main.MainViewModel
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.video.adapter.ListVideoAdapter
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import framgia.com.video.youtubevideo.utils.initViewModel

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>(),
        OnItemVideoClick, SwipeRefreshLayout.OnRefreshListener {
    lateinit var activityViewModel: MainViewModel

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                activityViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            }
        }
    }

    override fun initComponent(viewBinding: FragmentVideoBinding) {
        viewModel = initViewModel(VideoViewModel::class.java)
        viewBinding.videoViewModel = viewModel
        activityViewModel.apply {
            isVisibleBackButton.value = false
            titleMain.value = getString(R.string.title_popular)
        }
        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore() }
        viewModel.apply { loadListVideo(firstPage) }
        viewBinding.apply {
            swipeRefresh.setOnRefreshListener(this@VideoFragment)
            recyclerVideo.addOnScrollListener(endlessScrollListener)
        }
        viewModel.apply {
            listVideo.observe(this@VideoFragment, Observer {
                val listVideoAdapter = ListVideoAdapter(it!!, this@VideoFragment)
                val lineaLayoutManager = LinearLayoutManager(context)

                viewBinding.recyclerVideo.apply {
                    adapter = listVideoAdapter
                    layoutManager = lineaLayoutManager
                }
            })
            loadError.observe(this@VideoFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
            isInsertSuccessful.observe(this@VideoFragment, Observer {
                Toast.makeText(context, context?.getString(R.string.msg_insert_successfully), Toast.LENGTH_SHORT).show()
            })
            isInserted.observe(this@VideoFragment, Observer {
                Toast.makeText(context, context?.getString(R.string.msg_video_exist), Toast.LENGTH_SHORT).show()
            })
            isRemoveSuccesfull.observe(this@VideoFragment, Observer {
                when {
                    it == true -> Toast.makeText(context, getString(R.string.msg_remove_successfully), Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, getString(R.string.msg_not_added), Toast.LENGTH_SHORT).show()
                }
            })
            isRefresh.observe(this@VideoFragment, Observer {
                viewBinding.swipeRefresh.apply { isRefreshing = it == true }
            })
            isLoadMore.observe(this@VideoFragment, Observer {
                if (it == null) return@Observer
                endlessScrollListener.isLoadding = it
                if (it) viewBinding.progressbarLoadmore.visibility = View.VISIBLE else viewBinding.progressbarLoadmore.visibility = View.GONE
            })
            listVideoAdd.observe(this@VideoFragment, Observer {
                (viewBinding.recyclerVideo.adapter as ListVideoAdapter).apply {
                    addData(it)
                }
            })
//            isLoadding.observe(this@VideoFragment, Observer {
//                if (it == true) viewBinding.progressbar.visibility = View.VISIBLE else viewBinding.progressbar.visibility = View.GONE
//            })
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_video
    override fun onVideoClick(video: Video) {
        val directions = VideoFragmentDirections.actionVideoFragmentToPlayVideoFragment().setVideo(video)
        Navigation.findNavController(view
                ?: return).navigate(directions)
        viewModel.currentPage = ""
    }

    override fun onPopupButtonClick(video: Video, view: View) {
        addPopupMenu(R.menu.popup_menu_video, view).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_addfavorite -> viewModel.addFavorite(video)
                R.id.item_removefavorite -> viewModel.removeFavorite(video)
            }
            true
        }
    }

    override fun onRefresh() = viewModel.refreshData()
}
