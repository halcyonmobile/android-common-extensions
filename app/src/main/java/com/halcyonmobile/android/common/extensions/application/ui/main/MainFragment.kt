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

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<MainFragmentBinding>(view) ?: return

        binding.navigationTestCta.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationFragment())
        }
    }

}
