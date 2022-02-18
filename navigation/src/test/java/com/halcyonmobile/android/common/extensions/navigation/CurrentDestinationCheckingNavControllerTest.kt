package com.halcyonmobile.android.common.extensions.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.KArgumentCaptor
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argumentCaptor

@RunWith(Parameterized::class)
class CurrentDestinationCheckingNavControllerTest(testName: String, val navigateParameter: NavigateParameters) {

    private lateinit var sut: CurrentDestinationCheckingNavController
    private lateinit var mockNavController: NavController
    private lateinit var mockCurrentDestinationHoldingStore: CurrentDestinationHoldingStore

    @Before
    fun setup() {
        mockNavController = mock()
        mockCurrentDestinationHoldingStore = mock()
        sut = CurrentDestinationCheckingNavController(mockNavController, mockCurrentDestinationHoldingStore)
    }

    @Test
    fun when_Created_then_noInteraction() {
        verifyNoInteractions(mockCurrentDestinationHoldingStore)
        verifyNoInteractions(mockNavController)
    }

    @Test
    fun given_NoSavedCurrentId_when_NavigateIsCalledThenItsDelegatedToNavControllerAndTheCurrentDestinationIsSaved() {
        var currentDestinationId = 5
        val mockCurrentDestination = mock<NavDestination>()
        whenever(mockCurrentDestinationHoldingStore.savedCurrentDestinationId).doReturn(null)
        whenever(mockCurrentDestination.id).then { currentDestinationId }
        whenever(mockNavController.currentDestination).doReturn(mockCurrentDestination)
        val actualArguments = navigateParameter.setupMockNavControllerNavigateMethodAndGetArgumentCaptors(mockNavController) {
            currentDestinationId = 6
        }

        val arguments = navigateParameter.callNavigateAndGetArguments(sut)

        verify(mockCurrentDestinationHoldingStore, times(1)).savedCurrentDestinationId
        verify(mockCurrentDestinationHoldingStore, times(1)).savedCurrentDestinationId = 5
        verifyNoMoreInteractions(mockCurrentDestinationHoldingStore)
        navigateParameter.verifyNavigationMethod(verify(mockNavController, times(1)))
        verify(mockNavController, times(1)).currentDestination
        verifyNoMoreInteractions(mockNavController)
        verify(mockCurrentDestination, times(1)).id
        verifyNoMoreInteractions(mockCurrentDestination)

        Assert.assertEquals(arguments.size, actualArguments.size)
        actualArguments.forEachIndexed { index, argumentCaptor ->
            Assert.assertSame(arguments[index], argumentCaptor.firstValue)
        }
    }

    @Test
    fun given_AlreadySavedCurrentId_when_NavigateIsCalledWithSameCurrentId_then_ItsDelegatedToNavController() {
        val currentDestinationId = 6
        val mockCurrentDestination = mock<NavDestination>()
        whenever(mockCurrentDestinationHoldingStore.savedCurrentDestinationId).doReturn(currentDestinationId)
        whenever(mockCurrentDestination.id).doReturn(currentDestinationId)
        whenever(mockNavController.currentDestination).doReturn(mockCurrentDestination)
        val actualArguments = navigateParameter.setupMockNavControllerNavigateMethodAndGetArgumentCaptors(mockNavController) {}

        val arguments = navigateParameter.callNavigateAndGetArguments(sut)

        verify(mockCurrentDestinationHoldingStore, times(1)).savedCurrentDestinationId
        verifyNoMoreInteractions(mockCurrentDestinationHoldingStore)
        verify(mockNavController, times(1)).currentDestination
        navigateParameter.verifyNavigationMethod(verify(mockNavController, times(1)))
        verifyNoMoreInteractions(mockNavController)
        verify(mockCurrentDestination, times(1)).id
        verifyNoMoreInteractions(mockCurrentDestination)

        Assert.assertEquals(arguments.size, actualArguments.size)
        actualArguments.forEachIndexed { index, argumentCaptor ->
            Assert.assertSame(arguments[index], argumentCaptor.firstValue)
        }
    }

    @Test
    fun given_AlreadySavedCurrentId_when_NavigateIsCalledWithDifferentCurrentId_then_ItsNotDelegatedToNAvController() {
        val savedCurrentDestinationId = 5
        val currentDestinationId = 6
        val mockCurrentDestination = mock<NavDestination>()
        whenever(mockCurrentDestinationHoldingStore.savedCurrentDestinationId).doReturn(savedCurrentDestinationId)
        whenever(mockCurrentDestination.id).doReturn(currentDestinationId)
        whenever(mockNavController.currentDestination).doReturn(mockCurrentDestination)
        navigateParameter.setupMockNavControllerNavigateMethodAndGetArgumentCaptors(mockNavController) {}

        navigateParameter.callNavigateAndGetArguments(sut)

        verify(mockCurrentDestinationHoldingStore, times(1)).savedCurrentDestinationId
        verifyNoMoreInteractions(mockCurrentDestinationHoldingStore)
        verify(mockNavController, times(1)).currentDestination
        verifyNoMoreInteractions(mockNavController)
        verify(mockCurrentDestination, times(1)).id
        verifyNoMoreInteractions(mockCurrentDestination)
    }

    @Test(expected = IllegalArgumentException::class)
    fun given_navControllerThrowing_when_NavigateIsCalled_then_TheExceptionIsThrown() {
        val mockCurrentDestination = mock<NavDestination>()
        whenever(mockCurrentDestinationHoldingStore.savedCurrentDestinationId).doReturn(null)
        whenever(mockNavController.currentDestination).doReturn(mockCurrentDestination)
        whenever(mockCurrentDestination.id).doReturn(5)
        navigateParameter.setupMockNavControllerNavigateMethodAndGetArgumentCaptors(mockNavController) {
            throw IllegalArgumentException()
        }

        navigateParameter.callNavigateAndGetArguments(sut)
    }

    companion object {

        @get:Parameterized.Parameters(name = "argument type: {0}")
        @get:JvmStatic
        val parameters
            get() = listOf(
                arrayOf("idRes", createIdNavigateParameters()),
                arrayOf("idRes, Bundle", createIdWithBundleNavigateParameters()),
                arrayOf("idRes, Bundle, NavOptions", createIdWithBundleAndNavOptionsNavigateParameters()),
                arrayOf("idRes, Bundle, NavOptions, NavigatorExtras", createIdWithBundleNavOptionsAndExtrasNavigateParameters()),
                arrayOf("Uri", createUriNavigateParameters()),
                arrayOf("Uri, NavOptions", createUriWithNavOptionsNavigateParameters()),
                arrayOf("Uri, NavOptions, NavigatorExtras", createUriWithNavOptionsAndExtrasNavigateParameters()),
                arrayOf("NavDirections", createNavDirectionsNavigateParameters()),
                arrayOf("NavDirections, NavOptions", createNavDirectionsAndOptionsNavigateParameters()),
                arrayOf("NavDirections, NavigatorExtras", createNavDirectionsAndExtrasNavigateParameters())
            )

        class NavigateParameters(
            val setupMockNavControllerNavigateMethodAndGetArgumentCaptors: (navController: NavController, onNavigation: () -> Unit) -> List<KArgumentCaptor<Any>>,
            val callNavigateAndGetArguments: (CurrentDestinationCheckingNavController) -> List<Any>,
            val verifyNavigationMethod: (NavController) -> Unit
        )

        private fun createIdNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val resIdArgumentCaptor = argumentCaptor<Int>()
                    whenever(navController.navigate(resIdArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(resIdArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    it.navigate(5)
                    listOf(5)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Int>()) }
            )

        private fun createIdWithBundleNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val resIdArgumentCaptor = argumentCaptor<Int>()
                    val bundleArgumentCaptor = argumentCaptor<Bundle>()
                    whenever(navController.navigate(resIdArgumentCaptor.capture(), bundleArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(resIdArgumentCaptor, bundleArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val bundle = mock<Bundle>()
                    it.navigate(5, bundle)
                    listOf(5, bundle)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Int>(), anyOrNull<Bundle>()) }
            )

        private fun createIdWithBundleAndNavOptionsNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val resIdArgumentCaptor = argumentCaptor<Int>()
                    val bundleArgumentCaptor = argumentCaptor<Bundle>()
                    val navOptionsArgumentCaptor = argumentCaptor<NavOptions>()
                    whenever(navController.navigate(resIdArgumentCaptor.capture(), bundleArgumentCaptor.capture(), navOptionsArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(resIdArgumentCaptor, bundleArgumentCaptor, navOptionsArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val bundle = mock<Bundle>()
                    val navOptions = mock<NavOptions>()
                    it.navigate(5, bundle, navOptions)
                    listOf(5, bundle, navOptions)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Int>(), anyOrNull<Bundle>(), anyOrNull<NavOptions>()) }
            )

        private fun createIdWithBundleNavOptionsAndExtrasNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val resIdArgumentCaptor = argumentCaptor<Int>()
                    val bundleArgumentCaptor = argumentCaptor<Bundle>()
                    val navOptionsArgumentCaptor = argumentCaptor<NavOptions>()
                    val navExtrasArgumentCaptor = argumentCaptor<Navigator.Extras>()
                    whenever(
                        navController.navigate(
                            resIdArgumentCaptor.capture(),
                            bundleArgumentCaptor.capture(),
                            navOptionsArgumentCaptor.capture(),
                            navExtrasArgumentCaptor.capture()
                        )
                    ).then {
                        onNavigation()
                        Unit
                    }
                    listOf(resIdArgumentCaptor, bundleArgumentCaptor, navOptionsArgumentCaptor, navExtrasArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val bundle = mock<Bundle>()
                    val navOptions = mock<NavOptions>()
                    val navExtras = mock<Navigator.Extras>()
                    it.navigate(5, bundle, navOptions, navExtras)
                    listOf(5, bundle, navOptions, navExtras)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Int>(), anyOrNull<Bundle>(), anyOrNull<NavOptions>(), anyOrNull<Navigator.Extras>()) }
            )

        private fun createUriNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val uriArgumentCaptor = argumentCaptor<Uri>()
                    whenever(navController.navigate(uriArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(uriArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val uri = mock<Uri>()
                    it.navigate(uri)
                    listOf(uri)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Uri>()) }
            )

        private fun createUriWithNavOptionsNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val uriArgumentCaptor = argumentCaptor<Uri>()
                    val navOptionsArgumentCaptor = argumentCaptor<NavOptions>()
                    whenever(navController.navigate(uriArgumentCaptor.capture(), navOptionsArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(uriArgumentCaptor, navOptionsArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val uri = mock<Uri>()
                    val navOptions = mock<NavOptions>()
                    it.navigate(uri, navOptions)
                    listOf(uri, navOptions)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Uri>(), anyOrNull<NavOptions>()) }
            )

        private fun createUriWithNavOptionsAndExtrasNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val uriArgumentCaptor = argumentCaptor<Uri>()
                    val navOptionsArgumentCaptor = argumentCaptor<NavOptions>()
                    val navExtrasArgumentCaptor = argumentCaptor<Navigator.Extras>()
                    whenever(navController.navigate(uriArgumentCaptor.capture(), navOptionsArgumentCaptor.capture(), navExtrasArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(uriArgumentCaptor, navOptionsArgumentCaptor, navExtrasArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val uri = mock<Uri>()
                    val navOptions = mock<NavOptions>()
                    val extras = mock<Navigator.Extras>()
                    it.navigate(uri, navOptions, extras)
                    listOf(uri, navOptions, extras)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<Uri>(), anyOrNull<NavOptions>(), anyOrNull<Navigator.Extras>()) }
            )

        private fun createNavDirectionsNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val navDirectionsArgumentCaptor = argumentCaptor<NavDirections>()
                    whenever(navController.navigate(navDirectionsArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(navDirectionsArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val navDirections = mock<NavDirections>()
                    it.navigate(navDirections)
                    listOf(navDirections)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<NavDirections>()) }
            )

        private fun createNavDirectionsAndOptionsNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val navDirectionsArgumentCaptor = argumentCaptor<NavDirections>()
                    val navOptionsArgumentCaptor = argumentCaptor<NavOptions>()
                    whenever(navController.navigate(navDirectionsArgumentCaptor.capture(), navOptionsArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(navDirectionsArgumentCaptor, navOptionsArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val navDirections = mock<NavDirections>()
                    val navOptions = mock<NavOptions>()
                    it.navigate(navDirections, navOptions)
                    listOf(navDirections, navOptions)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<NavDirections>(), anyOrNull<NavOptions>()) }
            )

        private fun createNavDirectionsAndExtrasNavigateParameters(): NavigateParameters =
            NavigateParameters(
                setupMockNavControllerNavigateMethodAndGetArgumentCaptors = { navController, onNavigation ->
                    val navDirectionsArgumentCaptor = argumentCaptor<NavDirections>()
                    val navExtrasArgumentCaptor = argumentCaptor<Navigator.Extras>()
                    whenever(navController.navigate(navDirectionsArgumentCaptor.capture(), navExtrasArgumentCaptor.capture())).then {
                        onNavigation()
                        Unit
                    }
                    listOf(navDirectionsArgumentCaptor, navExtrasArgumentCaptor)
                },
                callNavigateAndGetArguments = {
                    val navDirections = mock<NavDirections>()
                    val extras = mock<Navigator.Extras>()
                    it.navigate(navDirections, extras)
                    listOf(navDirections, extras)
                },
                verifyNavigationMethod = { it.navigate(anyOrNull<NavDirections>(), anyOrNull<Navigator.Extras>()) }
            )
    }
}