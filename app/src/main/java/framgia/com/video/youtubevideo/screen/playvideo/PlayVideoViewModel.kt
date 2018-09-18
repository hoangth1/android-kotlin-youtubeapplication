package framgia.com.video.youtubevideo.screen.playvideo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import framgia.com.video.youtubevideo.base.BaseViewModel
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.local.VideoLocalDataSource
import framgia.com.video.youtubevideo.data.source.local.database.VideoDatabase
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.network.Network
import framgia.com.video.youtubevideo.data.source.remote.VideoRemoteDataSource
import framgia.com.video.youtubevideo.data.source.repository.VideoRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class PlayVideoViewModel(aplication: Application) : BaseViewModel(aplication) {
    val videoPlay = MutableLiveData<Video>()
    val listVideo = MutableLiveData<List<Video>>()
    val isLoading = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource(VideoDatabase.newInstance(aplication).videoDAO()))

    fun setVideoData(video: Video) {
        videoPlay.value = video
    }

    fun loadRelatedVideo() {
        videoRepository.searchVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET,
                Api.PARAM_TYPE to Api.TYPE_VIDEO,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_RELATED_VIDEO_ID to videoPlay.value!!.mId
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading.value = true
                }.doAfterTerminate(Action {
                    isLoading.value = false
                }).subscribe({
                    listVideo.value = it.mListVideo
                }, {
                    loadError.value = it.message
                })
    }

    fun addFavorite(video: Video?) {
        if (video == null) return
        Observable.just(video).subscribeOn(Schedulers.io())
                .doAfterTerminate {
                    checkVideoAddedFavorite(video)
                }.subscribe({ videoRepository.insertVideo(video) }, { })
    }

    fun deleteFavorite(video: Video?) {
        if (video == null) return
        Observable.just(video).subscribeOn(Schedulers.io())
                .doAfterTerminate {
                    checkVideoAddedFavorite(video)
                }.subscribe({ videoRepository.deleteVideo(video) }, { })
    }

    fun checkVideoAddedFavorite(video: Video?) {
        if (video == null) return
        videoRepository.getVideo(video.mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isFavorite.value = true
                }, { isFavorite.value = false })
    }
}
