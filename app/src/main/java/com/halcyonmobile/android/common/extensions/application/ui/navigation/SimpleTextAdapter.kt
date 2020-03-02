package com.halcyonmobile.android.common.extensions.application.ui.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.application.ui.shared.ItemClickListener

class SimpleTextAdapter(private val itemClickListener: ItemClickListener<String>) : ListAdapter<String, SimpleTextAdapter.SimpleTextItemViewHolder>(StringDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextItemViewHolder = SimpleTextItemViewHolder(parent, itemClickListener)

    override fun onBindViewHolder(holder: SimpleTextItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = 1

    class SimpleTextItemViewHolder private constructor(val textView: TextView, itemClickListener: ItemClickListener<String>) : RecyclerView.ViewHolder(textView) {

        constructor(parent: ViewGroup, itemClickListener: ItemClickListener<String>) : this(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_text, parent, false) as TextView, itemClickListener)

        init {
            textView.setOnClickListener {
                itemClickListener.onItemClicked(textView.text?.toString().orEmpty())
            }
        }

        fun bind(text: String) {
            textView.text = text
        }
    }

    class StringDiffUtilItemCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = true

    }
}