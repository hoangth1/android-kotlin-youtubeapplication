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
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.search.SearchFragment
import framgia.com.video.youtubevideo.screen.video.VideoFragment
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    var menuItem: MenuItem? = null
    override fun getLayout(): Int = R.layout.activity_main
    override fun initComponent(savedInstanceState: Bundle?) {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        replaceFragmentNotBackstack(VideoFragment.newInstance(), R.id.container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        menu?.findItem(R.id.item_search)?.apply {
            menuItem = this
            (actionView as SearchView).apply {
                setOnQueryTextListener(this@MainActivity)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null || query.isEmpty()) return false
        menuItem?.collapseActionView()
        replaceFragment(SearchFragment.newInstance(query), R.id.container, FragmentBackstackConstant.TAG_SEARCH_FRAGMENT)
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

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        if (fragments.size <= 2) {
            finish()
        }
        for (fragment in fragments) {
            when (fragment) {
                is SearchFragment -> fragment.onBackPress()
                is PlayVideoFragment -> fragment.onBackPress()
            }
        }
    }

}
