package framgia.com.video.youtubevideo.screen.playvideo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.databinding.FragmentPlayVideoBinding
import framgia.com.video.youtubevideo.screen.playvideo.adapter.RelatedVideoAdapter
import framgia.com.video.youtubevideo.utils.initViewModel

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding, PlayVideoViewModel>(),
        YouTubePlayer.OnInitializedListener {

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

    override fun initComponent(viewBinding: FragmentPlayVideoBinding) {
        viewModel = initViewModel(PlayVideoViewModel::class.java)
        viewBinding.playVideoModel = viewModel
        val youtubePlayerFragment = YouTubePlayerSupportFragment()
        addFramgent(youtubePlayerFragment, R.id.container_video, "")
        youtubePlayerFragment.initialize(Api.KEY, this)
        viewModel.setVideoData(arguments?.get(BUNDLE_VIDEO) as Video)
        viewModel.listVideo.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            val relatedVideoAdapter = RelatedVideoAdapter(it, itemClickListener = { playSelectedVideo(it) })
            val linearLayoutManager = LinearLayoutManager(context)
            viewBinding.recyclerRelatedVideo.apply {
                adapter = relatedVideoAdapter
                layoutManager = linearLayoutManager
            }
        })
        viewModel.loadError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        viewModel.videoPlay.observe(this, Observer {
            if (!p2) p1?.cueVideo(it?.mId)
            viewModel.loadRelatedVideo()
        })
    }

    fun playSelectedVideo(video: Video) {
        viewModel.setVideoData(video)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_play_video
}
