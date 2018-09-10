package framgia.com.video.youtubevideo.screen.video

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.databinding.FragmentVideoBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.video.adapter.ListVideoAdapter
import framgia.com.video.youtubevideo.utils.initViewModel

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {
    override fun initComponent(viewBinding: FragmentVideoBinding) {
        val activity: MainActivity = getActivity() as MainActivity
        viewModel = initViewModel(VideoViewModel::class.java)
        viewModel.listVideo.observe(this, Observer {
            val adapter = ListVideoAdapter(it!!)
            val layoutManager = LinearLayoutManager(activity)
            viewBinding.recyclerVideo.adapter = adapter
            viewBinding.recyclerVideo.layoutManager = layoutManager

        })
        viewModel.loadError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

    }
    override fun getLayoutResource(): Int = R.layout.fragment_video
}
