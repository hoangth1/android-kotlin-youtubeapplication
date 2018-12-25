package framgia.com.video.youtubevideo.screen.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.base.EndlessScrollListener
import framgia.com.video.youtubevideo.data.model.Video
import framgia.com.video.youtubevideo.databinding.FragmentSearchBinding
import framgia.com.video.youtubevideo.screen.main.MainActivity
import framgia.com.video.youtubevideo.screen.main.MainViewModel
import framgia.com.video.youtubevideo.screen.playvideo.PlayVideoFragment
import framgia.com.video.youtubevideo.screen.search.adapter.SearchResultAdapter
import framgia.com.video.youtubevideo.screen.video.VideoFragmentDirections
import framgia.com.video.youtubevideo.utils.FragmentBackstackConstant
import framgia.com.video.youtubevideo.utils.initViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    lateinit var activityViewModel: MainViewModel

    companion object {
        const val BUNDLE_QUERY = "query"
        fun newInstance(stringQuery: String) = SearchFragment().apply {
            arguments = Bundle().apply {
                putString(BUNDLE_QUERY, stringQuery)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                activityViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            }
        }
    }

    override fun initComponent(viewBinding: FragmentSearchBinding) {
        setHasOptionsMenu(true)
        val bundle: Bundle = arguments ?: return
        activityViewModel.apply {
            titleMain.value = bundle.getString(BUNDLE_QUERY)
            isVisibleBackButton.value = true
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
        val directions = SearchFragmentDirections.actionSearchFragmentToPlayVideoFragment4().setVideo(video)
        Navigation.findNavController(view
                ?: return).navigate(directions)
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
            android.R.id.home -> Navigation.findNavController(view ?: return false).navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_search

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("kiemtra", "runned")


    }
}
