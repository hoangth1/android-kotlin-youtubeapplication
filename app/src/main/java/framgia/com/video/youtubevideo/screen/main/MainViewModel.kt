package framgia.com.video.youtubevideo.screen.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import framgia.com.video.youtubevideo.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    val titleMain = MutableLiveData<String>()
    val isVisibleBackButton = MutableLiveData<Boolean>()
    val localApplication = application
    val isInternetConnected = MutableLiveData<Boolean>()
    fun checkInternetConnection() {
        val connectivityManager = localApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networInfor: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (networInfor == null) isInternetConnected.value = false else networInfor.apply { isInternetConnected.value = isConnected }
    }
}
