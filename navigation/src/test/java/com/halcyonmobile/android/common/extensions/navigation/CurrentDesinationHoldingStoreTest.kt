package com.halcyonmobile.android.common.extensions.navigation

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

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
        verifyNoInteractions(mockSavedStateHandle)
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