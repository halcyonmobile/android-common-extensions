package com.halcyonmobile.android.common.extensions.navigation

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.halcyonmobile.android.common.extensions.navigation.fragments.UnsafeNavigationOriginFragment
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
class IsLibraryStillNeededTest {

    private lateinit var navController: TestNavHostController
    private lateinit var fragmentScenario: FragmentScenario<UnsafeNavigationOriginFragment>

    @Before
    fun setup() {
        fragmentScenario = FragmentScenario.launchInContainer(UnsafeNavigationOriginFragment::class.java)
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.navigation_test_graph)

        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.unsafeNavigationOriginFragment)
        }
    }

    // verify setup is proper
    @Test
    fun given_ProperResIdAs_when_navigationOnce_then_itNavigatesAsExpected_and_doesNotThrow() {
        onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())

        assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
    }

    // simulate multiple frequent clicks
    @Test(expected = IllegalArgumentException::class)
    fun given_ProperResIdAs_when_navigationMultipleTimes_then_itThrows() {
        try {
            onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())
            onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())
            onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())

            assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
        } catch (performException: PerformException) {
            performException.printStackTrace()
            throw performException.cause ?: performException
        }
    }

    // simulate two views clicks navigation
    @Test(expected = IllegalArgumentException::class)
    fun given_ProperResIdAs_andUrl_when_navigationViaBoth_then_itThrows() {
        try {
            onView(ViewMatchers.withId(R.id.to_destination_with_uri)).perform(ViewActions.click())
            onView(ViewMatchers.withId(R.id.to_destination_with_res_id)).perform(ViewActions.click())

            assertThat(navController.currentDestination?.id, IsEqual(R.id.navigationDestinationFragment))
        } catch (performException: PerformException) {
            performException.printStackTrace()
            throw performException.cause ?: performException
        }
    }
}