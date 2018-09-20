package framgia.com.video.youtubevideo.screen.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.base.EndlessScrollListener
import framgia.com.video.youtubevideo.base.OnBackPressed
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentSearchBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.search.adapter.SearchResultAdapter
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import framgia.com.video.youtubevideo.utils.initViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
        OnBackPressed {

    companion object {
        const val BUNDLE_QUERY = "query"
        fun newInstance(stringQuery: String) = SearchFragment().apply {
            arguments = Bundle().apply {
                putString(BUNDLE_QUERY, stringQuery)
            }
        }
    }

    override fun initComponent(viewBinding: FragmentSearchBinding) {
        setHasOptionsMenu(true)
        val bundle: Bundle = arguments ?: return
        (activity as MainActivity).apply {
            title = bundle.getString(BUNDLE_QUERY)
            showArrowBackButton()
        }
        val endlessScrollListener = EndlessScrollListener {
            viewModel.onLoadMore(bundle.getString(BUNDLE_QUERY))
        }
        viewModel = initViewModel(SearchViewModel::class.java)
        viewBinding.recyclerSearchResult.addOnScrollListener(endlessScrollListener)
        viewBinding.searchViewModel = viewModel
        viewModel.searchResult.observe(this, Observer {
            if (it == null) return@Observer
            val searchAdapter = SearchResultAdapter(it, onItemClick = { playVideo(it) }, onPopupClick = ::showPopupMenu)
            val lineaLayoutManager = LinearLayoutManager(context)
            viewBinding.recyclerSearchResult.apply {
                layoutManager = lineaLayoutManager
                adapter = searchAdapter
            }
        })
        bundle.let { viewModel.apply { searchVideo(bundle.getString(BUNDLE_QUERY), firstPage) } }
        viewModel.listVideoAdd.observe(this, Observer {
            (viewBinding.recyclerSearchResult.adapter as SearchResultAdapter).apply {
                addData(it)
            }
        })
        viewModel.isLoadMore.observe(this, Observer {
            it?.apply {
                endlessScrollListener.isLoadding = it
            }
        })
    }

    fun playVideo(video: Video) {
        val mainActivity = activity as MainActivity
        mainActivity.replaceFragment(PlayVideoFragment.newInstance(video), R.id.container,
                FragmentBackstackConstant.TAG_PLAY_VIDEO_FRAGMENT)
    }

    fun showPopupMenu(video: Video, view: View) {
        addPopupMenu(R.menu.popup_menu_video, view).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_addfavorite -> viewModel.addFavorite(video)
                R.id.item_removefavorite -> viewModel.removeFavorite(video)
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> handleBack()
        }
        return super.onOptionsItemSelected(item)
    }

    fun handleBack() {
        (activity as MainActivity).apply {
            supportFragmentManager.popBackStack()
            title = getString(R.string.app_name)
            hideArrowBackButton()
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_search
    override fun onBackPress() = handleBack()
}
