package framgia.com.video.youtubevideo.screen.favorite

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import framgia.com.video.youtubevideo.base.BaseViewModel
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.data.source.local.VideoLocalDataSource
import framgia.com.video.youtubevideo.data.source.local.database.VideoDatabase
import framgia.com.video.youtubevideo.data.source.network.Network
import framgia.com.video.youtubevideo.data.source.remote.VideoRemoteDataSource
import framgia.com.video.youtubevideo.data.source.repository.VideoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(application: Application) : BaseViewModel(application) {
    val listFavorite = MutableLiveData<List<Video>>()
    val isLoadding = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<String>()
    val isRefresh = MutableLiveData<Boolean>()
    val videoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()),
            VideoLocalDataSource(VideoDatabase.newInstance(application).videoDAO()))

    fun loadFavoriteVideo() {
        videoRepository.getVideos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoadding.value = true }
                .doAfterTerminate({ isLoadding.value = false })
                .subscribe({ listFavorite.value = it }, {
                    loadError.value = it.message
                })
    }

    fun refreshData() {
        videoRepository.getVideos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isRefresh.value = true }
                .doAfterTerminate { isRefresh.value = false }
                .subscribe({ listFavorite.value = it }, {
                    loadError.value = it.message
                })
    }
}
