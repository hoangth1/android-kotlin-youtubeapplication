package framgia.com.video.youtubevideo.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<ViewBinding : ViewDataBinding,
        ViewModel : BaseViewModel> : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: ViewBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        val view = viewBinding.root
        viewBinding.setLifecycleOwner(this)
        initComponent(viewBinding);
        return view
    }

    abstract fun initComponent(viewBinding: ViewBinding)

    abstract fun getLayoutResource(): Int
    open fun addFramgent(fragment: Fragment, container: Int, tag: String) {
        val transaction = childFragmentManager.beginTransaction().let {
            it.add(container, fragment)
            it.addToBackStack(tag)
            it.commit()
        }
    }

    open fun replaceFragment(fragment: Fragment, container: Int, tag: String) {
        val transaction = childFragmentManager.beginTransaction().let {
            it.replace(container, fragment)
            it.addToBackStack(tag)
            it.commit()
        }

    }
}
