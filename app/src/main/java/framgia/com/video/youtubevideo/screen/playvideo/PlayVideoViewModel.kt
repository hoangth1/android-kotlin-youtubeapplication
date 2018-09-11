package framgia.com.video.youtubevideo.screen.playvideo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import framgia.com.video.youtubevideo.base.BaseViewModel
import framgia.com.video.youtubevideo.data.model.Video

class PlayVideoViewModel(aplication: Application) : BaseViewModel(aplication) {
    val videoPlay = MutableLiveData<Video>()

    fun setVideoData(video: Video) {
        videoPlay.value = video
    }
}
