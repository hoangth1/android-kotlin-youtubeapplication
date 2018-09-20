package framgia.com.video.youtubevideo.base

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {
    lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initComponent(savedInstanceState)
    }

    abstract fun initComponent(savedInstanceState: Bundle?)
    abstract fun getLayout(): Int
    open fun addFragment(fragment: Fragment, container: Int, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            add(container, fragment)
            addToBackStack(tag)
            commit()
        }
    }

    open fun replaceFragment(fragment: Fragment, container: Int, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment)
            addToBackStack(tag)
            commit()
        }
    }

    open fun replaceFragmentNotBackstack(fragment: Fragment, container: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment)
            commit()
        }
    }

    fun showArrowBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

    }

    fun hideArrowBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
}
