package framgia.com.video.youtubevideo.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver

abstract class BaseViewModel(application: Application) :
        LifecycleObserver, AndroidViewModel(application)
