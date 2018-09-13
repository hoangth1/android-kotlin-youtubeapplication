package framgia.com.video.youtubevideo.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initComponent(savedInstanceState)
    }

    abstract fun initComponent(savedInstanceState: Bundle?)
    abstract fun getLayout(): Int
    open fun addFragment(fragment: Fragment, container: Int, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
                .let {
                    it.apply {
                        add(container, fragment)
                        addToBackStack(tag)
                        commit()
                    }
                }
    }

    open fun replaceFragment(fragment: Fragment, container: Int, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
                .let {
                    it.apply {
                        replace(container, fragment)
                        addToBackStack(tag)
                        commit()
                    }
                }
    }
}
