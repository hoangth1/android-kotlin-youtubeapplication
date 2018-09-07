package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.screen.main

import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.R
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.base.BaseActivity
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.local.VideoLocalDataSource
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.network.Api
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.network.Network
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.remote.VideoRemoteDataSource
import youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.data.source.repository.VideoRepository

class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        val repository: VideoRepository = VideoRepository(VideoRemoteDataSource(Network.getApi()), VideoLocalDataSource())
        repository.getListPopularVideo(Api.CHART_MOST_POPULAR, Api.PART_SNIPPET, Api.MAX_RESULT, Api.REGION_CODE_V)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("kiemtra", it.mListVideo.size.toString())
                        },
                        onError = {
                            Log.d("kiemtra", it.message)
                        }
                )
    }
}
