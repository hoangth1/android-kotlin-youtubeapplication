package framgia.com.video.youtubevideo.screen.favorite

import framgia.com.video.youtubevideo.R
import framgia.com.video.youtubevideo.base.BaseFragment
import framgia.com.video.youtubevideo.databinding.FragmentFavoriteBinding
import framgia.com.video.youtubevideo.utils.initViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun initComponent(viewBinding: FragmentFavoriteBinding) {
        viewModel = initViewModel(FavoriteViewModel::class.java)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_favorite
}
