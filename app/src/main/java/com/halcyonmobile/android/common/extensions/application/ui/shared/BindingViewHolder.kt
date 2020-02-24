package com.halcyonmobile.android.common.extensions.application.ui.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<Binding : ViewDataBinding> constructor(val binding: Binding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup, @LayoutRes layoutRes: Int) : this(DataBindingUtil.inflate<Binding>(LayoutInflater.from(parent.context), layoutRes, parent, false))
}