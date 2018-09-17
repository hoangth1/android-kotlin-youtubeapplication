package framgia.com.video.youtubevideo.screen.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseActivity
import framgia.com.video.youtubevideo.screen.about.AboutFragment
import framgia.com.video.youtubevideo.screen.favorite.FavoriteFragment
import framgia.com.video.youtubevideo.screen.search.SearchFragment
import framgia.com.video.youtubevideo.screen.video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_popular -> replaceFragmentNotBackstack(VideoFragment.newInstance(),
                    R.id.container)
            R.id.item_favorite -> replaceFragmentNotBackstack(FavoriteFragment.newInstance(),
                    R.id.container)
            R.id.item_about_us -> replaceFragmentNotBackstack(AboutFragment.newInstance(),
                    R.id.container)
        }
        return true
    }
}
