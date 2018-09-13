package framgia.com.video.youtubevideo.screen.search

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import framgia.com.video.youtubevideo.base.BaseViewModel
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.local.VideoLocalDataSource
import framgia.com.video.youtubevideo.data.source.network.Api
import framgia.com.video.youtubevideo.data.source.network.Network
import framgia.com.video.youtubevideo.data.source.remote.VideoRemoteDataSource
import framgia.com.video.youtubevideo.data.source.repository.VideoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(aplication: Application) : BaseViewModel(aplication) {
    val searchResult = MutableLiveData<List<Video>>()
    val isLoadding = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<String>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource())

    fun searchVideo(textQuery: String) {
        videoRepository.searchVideo(hashMapOf(
                Api.PARAM_PART to Api.PART_SNIPPET,
                Api.PARAM_TYPE to Api.TYPE_VIDEO,
                Api.PARAM_MAX_RESULT to Api.MAX_RESULT.toString(),
                Api.PARAM_QUERY to textQuery
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadding.value = true
                }.doAfterTerminate {
                    isLoadding.value = false
                }.subscribe({
                    searchResult.value = it.mListVideo
                }, {
                    loadError.value = it.message
                })
    }
}
