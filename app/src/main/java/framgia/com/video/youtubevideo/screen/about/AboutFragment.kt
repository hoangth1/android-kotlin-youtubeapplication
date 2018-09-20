package framgia.com.video.youtubevideo.screen.about

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.main.MainViewModel

class AboutFragment : Fragment() {
    lateinit var activityViewModel: MainViewModel

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                activityViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityViewModel.apply {
            isVisibleBackButton.value = false
            titleMain.value = getString(R.string.tile_about)
        }
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}
