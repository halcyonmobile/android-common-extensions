package com.halcyonmobile.android.common.extensions.application.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.application.databinding.FragmentNavigationBinding
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController

class NavigationFragment : Fragment(R.layout.fragment_navigation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentNavigationBinding>(view) ?: return

        val adapter = SimpleTextAdapter { item ->
            findSafeNavController().navigate(NavigationFragmentDirections.actionNavigationFragmentToSimpleTextDetailFragment(item))
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(binding.recycler.context)
        adapter.submitList((0 until 100).map { "$it" })
    }

}
