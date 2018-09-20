package framgia.com.video.youtubevideo.screen.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import framgia.com.video.youtubevideo.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    val titleMain = MutableLiveData<String>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
}
