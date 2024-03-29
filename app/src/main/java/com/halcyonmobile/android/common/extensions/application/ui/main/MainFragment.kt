package com.halcyonmobile.android.common.extensions.application.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.application.databinding.MainFragmentBinding
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<MainFragmentBinding>(view) ?: return

        binding.navigationTestCta.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.mainFragment) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationFragment())
            }
        }
        binding.keyboardTestCta.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToKeyboardExtensionFragment())
        }

        binding.networkTestCta.setOnClickListener {
            findSafeNavController().navigate(MainFragmentDirections.actionMainFragmentToNetworkTestFragment())
        }
    }

}
