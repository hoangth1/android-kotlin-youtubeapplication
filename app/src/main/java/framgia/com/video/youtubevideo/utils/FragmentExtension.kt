package framgia.com.video.youtubevideo.utils

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

fun <V : AndroidViewModel> Fragment.initViewModel(viewModel: Class<V>) =
        ViewModelProviders.of(this).get(viewModel)
