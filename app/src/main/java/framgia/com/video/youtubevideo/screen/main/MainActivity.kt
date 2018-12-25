package framgia.com.video.youtubevideo.screen.main

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseActivity
import framgia.com.video.youtubevideo.screen.about.AboutFragment
import framgia.com.video.youtubevideo.screen.favorite.FavoriteFragment
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.search.SearchFragment
import framgia.com.video.youtubevideo.screen.video.VideoFragment
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(), SearchView.OnQueryTextListener {
    var menuItem: MenuItem? = null
    override fun getLayout(): Int = R.layout.activity_main
    override fun initComponent(savedInstanceState: Bundle?) {
        val host = nav_host as NavHostFragment
        val navController = host.navController
        setUpBottemNavigationMenu(navController)
        bottom_navigation.let { bottomNavView -> NavigationUI.setupWithNavController(bottomNavView, navController) }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.checkInternetConnection(Context.CONNECTIVITY_SERVICE)
        viewModel.titleMain.observe(this, Observer {
            title = it
        })
        viewModel.isVisibleBackButton.observe(this, Observer {
            if (it == true) showArrowBackButton() else hideArrowBackButton()
        })
    }

    private fun setUpBottemNavigationMenu(navController: NavController) {
        NavigationUI.setupWithNavController(bottom_navigation, navController)
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

    private fun showInformationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.title_oops))
            setMessage(getString(R.string.msg_connect_internet_failure))
            setCancelable(false)
            setPositiveButton(getString(R.string.title_try_again)) { dialog, which ->
                viewModel.checkInternetConnection(Context.CONNECTIVITY_SERVICE)
                dialog.dismiss()
            }
            create().show()
        }
    }
}
