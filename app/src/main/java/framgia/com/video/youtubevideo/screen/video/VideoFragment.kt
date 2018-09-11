package framgia.com.video.youtubevideo.screen.video

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentVideoBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.video.adapter.ListVideoAdapter
import framgia.com.video.youtubevideo.utils.initViewModel

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>(), OnItemVideoClick {
    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun initComponent(viewBinding: FragmentVideoBinding) {
        viewModel = initViewModel(VideoViewModel::class.java)
        viewModel.listVideo.observe(this, Observer {
            val listVideoAdapter = ListVideoAdapter(it!!, this@VideoFragment)
            val lineaLayoutManager = LinearLayoutManager(context)

            viewBinding.recyclerVideo.apply {
                adapter = listVideoAdapter
                layoutManager = lineaLayoutManager
            }
        })
        viewModel.loadError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_video
    override fun onVideoClick(video: Video) {
        val mainActivity = activity as MainActivity
        mainActivity.addFragment(PlayVideoFragment.newInstance(video), R.id.container, "")
    }
}
