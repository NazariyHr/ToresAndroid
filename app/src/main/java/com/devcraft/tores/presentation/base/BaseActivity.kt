package com.devcraft.tores.presentation.base

import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.devcraft.tores.R


abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity() {

    protected abstract val vm: BaseViewModel

    private var keyboardListenersAttached = false
    private var rootLayout: ViewGroup? = null

    private val keyboardLayoutListener = OnGlobalLayoutListener {
        val heightDiff = rootLayout!!.rootView.height - rootLayout!!.height
        val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).height
        if (heightDiff <= contentViewTop) {
            onHideKeyboard()
        } else {
            onShowKeyboard()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        initViews()
        initListeners()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (keyboardListenersAttached) {
            rootLayout!!.viewTreeObserver.removeGlobalOnLayoutListener(keyboardLayoutListener)
        }
    }

    open fun initViews() {}

    open fun initListeners() {}

    @CallSuper
    open fun initObservers() {
        vm.connectivityLiveData.observe(this, ::onNetworkConnectivityStatusChanged)
    }

    open fun setToolbarTitle(title: String) {}

    open fun showBackButton() {}

    open fun hideBackButton() {}

    open fun showBottomBar() {}

    open fun hideBottomBar() {}

    open fun showMainTopBar() {}

    open fun hideMainTopBar() {}

    protected open fun onShowKeyboard() {}

    protected open fun onHideKeyboard() {}

    fun attachKeyboardListener() {
        if (keyboardListenersAttached) {
            return
        }
        rootLayout = findViewById<View>(R.id.rootActivityLayout) as ViewGroup
        rootLayout!!.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)
        keyboardListenersAttached = true
    }

    fun detachKeyboardListener() {
        if (keyboardListenersAttached) {
            rootLayout!!.viewTreeObserver.removeGlobalOnLayoutListener(keyboardLayoutListener)
        }
    }

    //return true if handled
    open fun handleOpenFragment(
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean = true
    ): Boolean {
        return false
    }

    /**
     *  See [NetworkCapabilities.TRANSPORT_WIFI] and [NetworkCapabilities.TRANSPORT_CELLULAR].
     */
    protected open fun onNetworkConnectivityStatusChanged(isNetworkAvailable: Boolean) {
        //stub
    }

    fun showToast(txt: String, short: Boolean = true) {
        Toast.makeText(this, txt, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
            .show()
    }

    fun showToast(error: Error, short: Boolean = true) {
        Toast.makeText(
            this,
            error.message.orEmpty(),
            if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        )
            .show()
    }


    fun <T> openActivity(clazz: Class<T>, finishCurrent: Boolean) {
        val i = Intent(this, clazz)
        startActivity(i)
        if (finishCurrent) {
            this.finish()
        }
    }

    fun openActivity(i: Intent, finishCurrent: Boolean = true) {
        startActivity(i)
        if (finishCurrent) {
            finish()
        }
    }

    fun openFragment(container: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = this.supportFragmentManager.beginTransaction()
            .replace(container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}