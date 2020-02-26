package com.halcyonmobile.android.common.extensions.navigation

import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrentDesinationHoldingStoreTest {

    private lateinit var mockSavedStateHandle: SavedStateHandle
    private lateinit var sut: CurrentDestinationHoldingStore

    @Before
    fun setup(){
        mockSavedStateHandle = mock()
        sut = CurrentDestinationHoldingStore(mockSavedStateHandle)
    }

    @Test
    fun onCreationNoInteractionHappens(){
        verifyZeroInteractions(mockSavedStateHandle)
    }

    @Test
    fun when_RequestingTheDestination_then_itIsGetFromSavedState(){
        whenever(mockSavedStateHandle.get<Int>(SAVED_STATE_KEY)).doReturn(152)
        val value = sut.savedCurrentDestinationId

        Assert.assertEquals(152, value)
        verify(mockSavedStateHandle, times(1)).get<Int>(SAVED_STATE_KEY)
        verifyNoMoreInteractions(mockSavedStateHandle)
    }

    companion object {
        private val SAVED_STATE_KEY = "current_destination_holding_store_current_destination_id_state"
    }
}