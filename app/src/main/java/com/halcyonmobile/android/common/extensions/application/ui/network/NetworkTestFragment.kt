package com.halcyonmobile.android.common.extensions.application.ui.network

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import com.halcyonmobile.android.common.extensions.application.R
import com.halcyonmobile.android.common.extensions.network.isNetworkActiveCompat
import kotlinx.coroutines.awaitCancellation

class NetworkTestFragment : Fragment(R.layout.fragment_network_test) {

    // checks whenever the fragment becomes at least onStart
    private val isNetworkActive = liveData(timeoutInMs = 1L) {
        emit(requireContext().isNetworkActiveCompat)
        awaitCancellation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.is_active_text)
        isNetworkActive.observe(viewLifecycleOwner) {
            val resId = if (it == true) R.string.network_is_active else R.string.network_is_inactive
            textView.setText(resId)
        }
    }
}
