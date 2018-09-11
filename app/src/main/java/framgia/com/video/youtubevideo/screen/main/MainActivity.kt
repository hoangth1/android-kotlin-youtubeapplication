package framgia.com.video.youtubevideo.screen.main

import android.os.Bundle
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseActivity
import framgia.com.video.youtubevideo.screen.video.VideoFragment

class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        addFragment(VideoFragment.newInstance(), R.id.container, "")
    }

    open fun hideActionBar() {
        supportActionBar?.hide()
    }
}
