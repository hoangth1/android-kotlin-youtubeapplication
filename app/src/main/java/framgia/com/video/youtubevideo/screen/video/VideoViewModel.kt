package framgia.com.video.youtubevideo.screen.video

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
import io.reactivex.schedulers.Schedulers

class VideoViewModel(application: Application) : BaseViewModel(application) {
    val firstPage = "-1"
    var nextPage = ""
    var currentPage = ""
    val isLoadding = MutableLiveData<Boolean>()
    val listVideo = MutableLiveData<List<Video>>()
    val loadError = MutableLiveData<String>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource(VideoDatabase.newInstance(application).videoDAO()))
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val isInserted = MutableLiveData<Boolean>()
    val isRemoveSuccesfull = MutableLiveData<Boolean>()
    val isRefresh = MutableLiveData<Boolean>()
    val isLoadMore = MutableLiveData<Boolean>()
    val listVideoAdd = MutableLiveData<List<Video>>()

    fun loadListVideo(page: String) {
        videoRepository.getListPopularVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET + "," + Api.PART_STATISTICS,
                Api.PARAM_CHART to Api.CHART_MOST_POPULAR,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_REGION_CODE to Api.REGION_CODE_V,
                Api.PARAM_PAGE_TOKEN to page
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    when {
                        page == firstPage && !(page == currentPage) -> isLoadding.value = true
                        page == firstPage && page == currentPage -> isRefresh.value = true
                        else -> isLoadMore.value = true
                    }
                }
                .doAfterTerminate {

                    when {
                        page == firstPage && !(page == currentPage) -> isLoadding.value = false
                        page == firstPage && page == currentPage -> isRefresh.value = false
                        else -> isLoadMore.value = false
                    }
                    currentPage = page

                }
                .subscribe({
                    it.apply {
                        nextPage = nextPageToken
                        if (isLoadMore.value == true) {
                            listVideoAdd.value = mListVideo
                            return@apply
                        }
                        listVideo.value = mListVideo
                    }
                }, {
                    loadError.value = it.message
                })
    }

    fun refreshData() {
        currentPage = firstPage
        loadListVideo(firstPage)
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


    fun onLoadMore() {
        loadListVideo(nextPage)
    }
}
