package com.devcraft.tores.presentation.base

import android.app.Activity
import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.devcraft.tores.presentation.common.DatePickerDialogWrapper

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    protected abstract val vm: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObservers()
        arguments?.let { parseArguments(it) }
    }

    open fun initViews() {}

    open fun initListeners() {}

    @CallSuper
    open fun initObservers() {
        vm.connectivityLiveData.observe(viewLifecycleOwner, ::onNetworkConnectivityStatusChanged)
        vm.onFailure.observe(viewLifecycleOwner, {
            showToast(it.message.orEmpty())
        })
    }

    open fun parseArguments(arguments: Bundle) {}

    /**
     *  See [NetworkCapabilities.TRANSPORT_WIFI] and [NetworkCapabilities.TRANSPORT_CELLULAR].
     */
    protected open fun onNetworkConnectivityStatusChanged(isNetworkAvailable: Boolean) {
        //stub
    }

    protected fun getBaseActivity(): BaseActivity? {
        return if (activity is BaseActivity) activity as BaseActivity else null
    }

    protected fun setBaseActivityToolbarTitle(title: String) {
        getBaseActivity()?.setToolbarTitle(title)
    }

    protected fun showBaseActivityBackButton() {
        getBaseActivity()?.showBackButton()
    }

    protected fun hideBaseActivityBackButton() {
        getBaseActivity()?.hideBackButton()
    }

    protected fun attachKeyboardListener(){
        getBaseActivity()?.attachKeyboardListener()
    }

    protected fun detachKeyboardListener(){
        getBaseActivity()?.detachKeyboardListener()
    }

    fun showToast(txt: String, short: Boolean = true) {
        if (context != null) {
            Toast.makeText(context, txt, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
                .show()
        }
    }

    fun showToast(error: Error, short: Boolean = true) {
        if (context != null) {
            Toast.makeText(
                context,
                error.message.orEmpty(),
                if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            )
                .show()
        }
    }

    fun showDataPickerDialogWithCurrentDate(callback: (year: Int, month: Int, day: Int) -> Unit) {
        context?.let {
            DatePickerDialogWrapper.createWithCurrentDate(it, callback).show()
        }
    }

    fun <T> openActivity(clazz: Class<T>, finishCurrent: Boolean = true) {
        val i = Intent(requireActivity(), clazz)
        startActivity(i)
        if (finishCurrent) {
            requireActivity().finish()
        }
    }

    fun openFragment(container: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        if (getBaseActivity()?.handleOpenFragment(container, fragment, addToBackStack) == false) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                .replace(container, fragment)

            if (addToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }
    }

    fun openDialog(fragment: DialogFragment) {
        fragment.show(
            requireActivity().supportFragmentManager,
            fragment.javaClass.name
        )
    }

    fun onBackPressed() {
        requireActivity().onBackPressed()
    }

    fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}