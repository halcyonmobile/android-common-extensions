package com.halcyonmobile.android.common.extensions.view

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

/**
 * Reliable shows the keyboard for the view by taking into consideration Window focus.
 *
 * Credit to Helios Alonso, described this solution in https://developer.squareup.com/blog/showing-the-android-keyboard-reliably/
 */
fun View.focusAndShowKeyboard() {
    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        // It’s very important to remove this listener once we are done.
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}

/**
 * This is to be called when the window already has focus.
 */
private fun View.showTheKeyboardNow() {
    if (isFocused) {
        post {
            // We still post the call, just in case we are being notified of the windows focus
            // but InputMethodManager didn't get properly setup yet.
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}