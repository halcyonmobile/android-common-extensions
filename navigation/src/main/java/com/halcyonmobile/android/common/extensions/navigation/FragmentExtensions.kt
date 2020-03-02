package com.halcyonmobile.android.common.extensions.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

/**
 * Returns a [CurrentDestinationCheckingNavController] which before delegating to [androidx.navigation.NavController.navigate] checks the currentDestination.
 *
 * Note: The saved DestinationId to check against is gathered from the first [CurrentDestinationCheckingNavController.navigate] call,
 * if it wasn't correct then the app will crash as normally.
 */
fun Fragment.findSafeNavController() : CurrentDestinationCheckingNavController =
    CurrentDestinationCheckingNavController(findNavController(), ViewModelProvider(this)[CurrentDestinationHoldingStore::class.java])

