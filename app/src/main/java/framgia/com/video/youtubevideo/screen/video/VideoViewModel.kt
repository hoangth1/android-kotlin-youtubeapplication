package framgia.com.video.youtubevideo.screen.video

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
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
import io.reactivex.schedulers.Schedulers

class VideoViewModel(application: Application) : BaseViewModel(application) {
    val isLoadding = MutableLiveData<Boolean>()
    val listVideo = MutableLiveData<List<Video>>()
    val loadError = MutableLiveData<String>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource(VideoDatabase.newInstance(application).videoDAO()))
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val isInserted = MutableLiveData<Boolean>()
    val isRemoveSuccesfull = MutableLiveData<Boolean>()
    val isRefresh = MutableLiveData<Boolean>()
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadListVideo() {
        videoRepository.getListPopularVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET + "," + Api.PART_STATISTICS,
                Api.PARAM_CHART to Api.CHART_MOST_POPULAR,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_REGION_CODE to Api.REGION_CODE_V
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadding.value = true
                }
                .doAfterTerminate {
                    isLoadding.value = false
                }
                .subscribe({
                    listVideo.value = it.mListVideo
                }, {
                    loadError.value = it.message
                })
    }

    fun refreshData() {
        videoRepository.getListPopularVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET + "," + Api.PART_STATISTICS,
                Api.PARAM_CHART to Api.CHART_MOST_POPULAR,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_REGION_CODE to Api.REGION_CODE_V
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isRefresh.value = true
                }
                .doAfterTerminate {
                    isRefresh.value = false
                }
                .subscribe({
                    listVideo.value = it.mListVideo
                }, {
                    loadError.value = it.message
                })
    }

    fun addFavorite(video: Video) {
        Observable.create<Long> { emitter ->
            emitter.onNext(videoRepository.insertVideo(video))

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isInsertSuccessful.value = true
                }, {
                    isInserted.value = true
                })
    }

    fun removeFavorite(video: Video?) {
        if (video == null) return
        Observable.create<Int> { emitter ->
            emitter.onNext(videoRepository.deleteVideo(video))

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map {
                    it != 0
                }.subscribe({
                    isRemoveSuccesfull.value = it
                }, {})
    }
}
