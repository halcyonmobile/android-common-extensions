package com.halcyonmobile.android.common.extensions.application.ui.shared

fun interface ItemClickListener<T> {
    fun onItemClicked(item: T)
}