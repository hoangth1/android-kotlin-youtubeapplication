package framgia.com.video.youtubevideo.screen.search

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

class SearchViewModel(aplication: Application) : BaseViewModel(aplication) {
    val firstPage = "-1"
    var nextPage = ""
    val searchResult = MutableLiveData<List<Video>>()
    val isLoadding = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<String>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource(VideoDatabase.newInstance(aplication).videoDAO()))
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val isInserted = MutableLiveData<Boolean>()
    val isRemoveSuccesfull = MutableLiveData<Boolean>()
    val listVideoAdd = MutableLiveData<List<Video>>()
    val isLoadMore = MutableLiveData<Boolean>()
    fun searchVideo(textQuery: String, page: String) {
        videoRepository.searchVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET,
                Api.PARAM_TYPE to Api.TYPE_VIDEO,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_QUERY to textQuery,
                Api.PARAM_PAGE_TOKEN to page
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    when {
                        page == firstPage -> isLoadding.value = true
                        else -> isLoadMore.value = true
                    }
                }
                .doAfterTerminate {
                    when {
                        page == firstPage -> isLoadding.value = false
                        else -> isLoadMore.value = false
                    }
                }
                .subscribe({
                    it.apply {
                        nextPage = nextPageToken
                        if (isLoadMore.value == true) {
                            listVideoAdd.value = mListVideo
                            return@apply
                        }
                        searchResult.value = mListVideo
                    }
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

    fun onLoadMore(textQuery: String) {
        searchVideo(textQuery, nextPage)
    }
}
