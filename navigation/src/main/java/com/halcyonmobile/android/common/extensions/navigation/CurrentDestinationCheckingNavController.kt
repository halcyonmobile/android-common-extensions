package com.halcyonmobile.android.common.extensions.navigation

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

/**
 * Wrapper around [NavController] stored in [underlyingNavController]
 *
 * Saves the currentDestinationId to [CurrentDestinationHoldingStore] on first navigation event.
 * For every other navigation event it check's the currentDestinationId against the stored one.
 *
 * Note: the CurrentDestinationHoldingStore stored data stays even if new [CurrentDestinationCheckingNavController] is created for the same fragment.
 */
class CurrentDestinationCheckingNavController internal constructor(
    private val underlyingNavController: NavController,
    private val currentDestinationHoldingStore: CurrentDestinationHoldingStore
) {

    fun navigate(@IdRes resId: Int) = safeNavigate { underlyingNavController.navigate(resId) }

    fun navigate(@IdRes resId: Int, args: Bundle?) = safeNavigate { underlyingNavController.navigate(resId, args) }

    fun navigate(@IdRes resId: Int, args: Bundle?, navOptions: NavOptions?) = safeNavigate { underlyingNavController.navigate(resId, args, navOptions) }

    fun navigate(@IdRes resId: Int, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?) =
        safeNavigate { underlyingNavController.navigate(resId, args, navOptions, navigatorExtras) }

    fun navigate(deepLink: Uri) = safeNavigate { underlyingNavController.navigate(deepLink) }

    fun navigate(deepLink: Uri, navOptions: NavOptions?) = safeNavigate { underlyingNavController.navigate(deepLink, navOptions) }

    fun navigate(deepLink: Uri, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?) =
        safeNavigate { underlyingNavController.navigate(deepLink, navOptions, navigatorExtras) }

    fun navigate(directions: NavDirections) = safeNavigate { underlyingNavController.navigate(directions) }

    fun navigate(directions: NavDirections, navOptions: NavOptions?) = safeNavigate { underlyingNavController.navigate(directions, navOptions) }

    fun navigate(directions: NavDirections, navigatorExtras: Navigator.Extras) = safeNavigate { underlyingNavController.navigate(directions, navigatorExtras) }

    private inline fun safeNavigate(crossinline navigate: () -> Unit) {
        val savedCurrentDestinationId = currentDestinationHoldingStore.savedCurrentDestinationId
        val currentDestinationId = underlyingNavController.currentDestination?.id
        if (savedCurrentDestinationId == null) {
            currentDestinationHoldingStore.savedCurrentDestinationId = currentDestinationId
            navigate()
        } else if (savedCurrentDestinationId == currentDestinationId) {
            navigate()
        }
    }
}