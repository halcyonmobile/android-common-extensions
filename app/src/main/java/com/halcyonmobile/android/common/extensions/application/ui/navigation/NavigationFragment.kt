package com.halcyonmobile.android.common.extensions.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.application.databinding.NavigationFragmentBinding
import com.halcyonmobile.android.common.extensions.application.ui.navigation.SimpleTextAdapter
import com.halcyonmobile.android.common.extensions.application.ui.shared.ItemClickListener
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController

class NavigationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.navigation_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<NavigationFragmentBinding>(view) ?: return


        val adapter = SimpleTextAdapter(object : ItemClickListener<String> {
            override fun onItemClicked(item: String) {
                findSafeNavController().navigate(NavigationFragmentDirections.actionNavigationFragmentToSimpleTextDetailFragment(item))
            }
        })
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(binding.recycler.context)
        adapter.submitList((0 until 100).map { "$it" })
    }

}
