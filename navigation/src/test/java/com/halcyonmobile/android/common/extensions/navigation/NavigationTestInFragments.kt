package com.halcyonmobile.android.common.extensions.navigation

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.halcyonmobile.android.common.extensions.navigation.fragments.NavigationOriginFragment
import com.halcyonmobile.android.common.extensions.navigation.test.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(
    sdk = [28],
    // required to access final members on androidx.loader.content.ModernAsyncTask
    // open issue: https://github.com/robolectric/robolectric/issues/6593
    instrumentedPackages = [
        "androidx.loader.content"
    ]
)
@RunWith(AndroidJUnit4::class)
class NavigationTestInFragments {

    private lateinit var navController: TestNavHostController
    private lateinit var fragmentScenario: FragmentScenario<NavigationOriginFragment>

    @Before
    fun setup() {
        fragmentScenario = FragmentScenario.launchInContainer(NavigationOriginFragment::class.java)
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.navigation_test_graph)

        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun given_InvalidDestinationResIdAs_when_navigation_then_itThrowsAsExpected() {
        try {
            onView(ViewMatchers.withId(R.id.to_invalid_destination)).perform(ViewActions.click())
        } catch (throwable: Throwable) {
            // unwrap the espresso exception
            throwable.cause?.let { throw it }
        }
    }

    // region resId
    // simulate basic behaviour is still present
    @Test
    fun given_ProperResIdAs_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    // simulate multiple frequent clicks
    @Test
    fun given_ProperResIdAs_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperResIdAsWithBundle_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperResIdAsWithBundle_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }


    @Test
    fun given_ProperResIdAsWithBundleWithNavOptions_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperResIdAsWithBundleWithNavOptions_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperResIdAsWithBundleWithNavOptionsWithExtras_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperResIdAsWithBundleWithNavOptionsWithExtras_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id_and_bundle_and_nav_options_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }
    // endregion

    // region uri
    @Test
    fun given_ProperUri_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperUri_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperUriWithNavOptions_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperUriWithNavOptions_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperUriWithNavOptionsWithExtra_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperUriWithNavOptionsWithExtra_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_uri_and_nav_options_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }
    // endregion

    //region nav_directions
    @Test
    fun given_ProperNavDirections_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperNavDirections_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperNavDirectionsWithExtras_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperNavDirectionsWithExtras_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_extras)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_extras)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperNavDirectionsWithNavOptions_when_navigation_then_itNavigatesAsExpected() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    @Test
    fun given_ProperNavDirectionsWithNavOptions_when_navigationMultipleTimes_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_nav_options)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.to_destination_with_directions_and_nav_options)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }
    // endregion
}