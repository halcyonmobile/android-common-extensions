package com.halcyonmobile.android.common.extensions.application.ui.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.application.databinding.FragmentKeyboardExtensionBinding
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController
import com.halcyonmobile.android.common.extensions.view.focusAndShowKeyboard

class KeyboardExtensionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_keyboard_extension, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentKeyboardExtensionBinding>(view) ?: return

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.navigateToMain.setOnClickListener { findSafeNavController().navigate(KeyboardExtensionFragmentDirections.actionKeyboardExtensionFragmentToMainFragment()) }


        binding.editText.focusAndShowKeyboard()
    }
}