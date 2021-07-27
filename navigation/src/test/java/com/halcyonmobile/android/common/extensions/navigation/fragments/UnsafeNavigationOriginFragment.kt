package com.halcyonmobile.android.common.extensions.navigation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController
import com.halcyonmobile.android.common.extensions.navigation.test.R

class UnsafeNavigationOriginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_navigation_origin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.to_destination_with_res_id).setOnClickListener {
            findNavController().navigate(R.id.unsafe_origin_to_destination)
        }
        view.findViewById<TextView>(R.id.to_destination_with_uri).setOnClickListener {
            findNavController().navigate(Uri.parse("https://navigation-test/"))
        }
    }
}