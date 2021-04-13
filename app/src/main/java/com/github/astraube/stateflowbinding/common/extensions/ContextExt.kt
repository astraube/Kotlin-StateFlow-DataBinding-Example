package com.github.astraube.stateflowbinding.common.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created on 12/04/2021
 * @author André Straube
 */
fun Context.hideKeyboard(view: View, flags: Int = InputMethodManager.HIDE_NOT_ALWAYS) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, flags)
}
fun Context.hideKeyboard(view: Activity, flags: Int = InputMethodManager.HIDE_NOT_ALWAYS) {
    view.currentFocus?.let {
        this.hideKeyboard(it, flags)
    }
}