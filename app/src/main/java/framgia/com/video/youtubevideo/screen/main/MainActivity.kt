package framgia.com.video.youtubevideo.screen.main

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseActivity
import framgia.com.video.youtubevideo.screen.search.SearchFragment
import framgia.com.video.youtubevideo.screen.video.VideoFragment

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        addFragment(VideoFragment.newInstance(), R.id.container, "")
    }

    open fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        menu?.findItem(R.id.item_search).apply {
            (this?.actionView as SearchView).apply {
                setOnQueryTextListener(this@MainActivity)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null || query.isEmpty()) return false
        addFragment(SearchFragment.newInstance(query), R.id.container, "")
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}
