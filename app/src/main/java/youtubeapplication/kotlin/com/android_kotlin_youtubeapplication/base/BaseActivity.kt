package youtubeapplication.kotlin.com.android_kotlin_youtubeapplication.base

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
                    it.add(container, fragment)
                    it.addToBackStack(tag)
                    it.commit()
                }
    }

    open fun replaceFragment(fragment: Fragment, container: Int, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
                .let {
                    it.replace(container, fragment)
                    it.addToBackStack(tag)
                    it.commit()
                }

    }

}
