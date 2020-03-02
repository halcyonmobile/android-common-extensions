package com.halcyonmobile.android.common.extensions.navigation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.halcyonmobile.android.common.extensions.navigation.findSafeNavController
import com.halcyonmobile.android.common.extensions.navigation.test.R

class NavigationOriginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_navigation_origin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.to_invalid_destination).setOnClickListener {
            findSafeNavController().navigate(R.id.destination_to_wrong_destination)
        }
        view.findViewById<TextView>(R.id.to_destination_with_res_id).setOnClickListener {
            findSafeNavController().navigate(R.id.origin_to_destination)
        }

        view.findViewById<TextView>(R.id.to_destination_with_res_id_and_bundle).setOnClickListener {
            findSafeNavController().navigate(R.id.origin_to_destination, Bundle())
        }

        view.findViewById<TextView>(R.id.to_destination_with_res_id_and_bundle_and_nav_options).setOnClickListener {
            findSafeNavController().navigate(R.id.origin_to_destination, Bundle(), NavOptions.Builder().build())
        }

        view.findViewById<TextView>(R.id.to_destination_with_res_id_and_bundle_and_nav_options_and_extras).setOnClickListener {
            findSafeNavController().navigate(R.id.origin_to_destination, Bundle(), NavOptions.Builder().build(), FragmentNavigatorExtras())
        }

        view.findViewById<TextView>(R.id.to_destination_with_uri).setOnClickListener {
            findSafeNavController().navigate(Uri.parse("https://navigation-test/"))
        }

        view.findViewById<TextView>(R.id.to_destination_with_uri_and_nav_options).setOnClickListener {
            findSafeNavController().navigate(Uri.parse("https://navigation-test/"), NavOptions.Builder().build())
        }

        view.findViewById<TextView>(R.id.to_destination_with_uri_and_nav_options_and_extras).setOnClickListener {
            findSafeNavController().navigate(Uri.parse("https://navigation-test/"), NavOptions.Builder().build(), FragmentNavigatorExtras())
        }

        view.findViewById<TextView>(R.id.to_destination_with_directions).setOnClickListener {
            findSafeNavController().navigate(ActionOnlyNavDirections(R.id.origin_to_destination))
        }

        view.findViewById<TextView>(R.id.to_destination_with_directions_and_extras).setOnClickListener {
            findSafeNavController().navigate(ActionOnlyNavDirections(R.id.origin_to_destination), FragmentNavigatorExtras())
        }

        view.findViewById<TextView>(R.id.to_destination_with_directions_and_nav_options).setOnClickListener {
            findSafeNavController().navigate(ActionOnlyNavDirections(R.id.origin_to_destination), NavOptions.Builder().build())
        }
    }
}