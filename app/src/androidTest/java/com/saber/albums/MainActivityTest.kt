package com.saber.albums

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.saber.albums.ui.MainActivity
import com.saber.albums.ui.adapter.AlbumViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityInjectionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun verifyInjection() {
        ActivityScenario.launch(MainActivity::class.java).use {
            it.moveToState(Lifecycle.State.CREATED)
            it.onActivity { activity ->
                assertThat(activity.viewModel).isNotNull()
            }
        }
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        ActivityScenario.launch(MainActivity::class.java).use {
            it.moveToState(Lifecycle.State.CREATED)
            it.moveToState(Lifecycle.State.STARTED)
            it.moveToState(Lifecycle.State.RESUMED)

            onView(withId(R.id.list))
                .perform(
                    RecyclerViewActions.scrollTo<AlbumViewHolder>(
                        hasDescendant(withText(""))
                    )
                )
        }
    }

}