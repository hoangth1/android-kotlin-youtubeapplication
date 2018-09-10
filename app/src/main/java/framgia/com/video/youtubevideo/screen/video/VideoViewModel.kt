package framgia.com.video.youtubevideo.screen.video

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import framgia.com.video.youtubevideo.base.BaseViewModel
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.local.VideoLocalDataSource
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.network.Network
import framgia.com.video.youtubevideo.data.source.remote.VideoRemoteDataSource
import framgia.com.video.youtubevideo.data.source.repository.VideoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class VideoViewModel(application: Application) : BaseViewModel(application) {
    var isLoadding = MutableLiveData<Boolean>()
    var listVideo = MutableLiveData<List<Video>>()
    var loadError = MutableLiveData<String>()
    val videoRepository: VideoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource())

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadListVideo() {
        videoRepository.getListPopularVideo(Api.CHART_MOST_POPULAR,
                Api.PART_SNIPPET + "," + Api.PART_STATISTICS, Api.MAX_RESULT, Api.REGION_CODE_V)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(Consumer {
                    isLoadding.value = true
                })
                .subscribe({
                    isLoadding.value = false
                    listVideo.value = it.mListVideo
                }, {
                    isLoadding.value = false
                    loadError.value = it.message
                })
    }

}
