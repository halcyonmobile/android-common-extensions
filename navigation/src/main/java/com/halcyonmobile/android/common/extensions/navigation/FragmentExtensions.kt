package com.halcyonmobile.android.common.extensions.navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

fun Fragment.findSafeNavController() : CurrentDestinationCheckingNavController =
    CurrentDestinationCheckingNavController(findNavController(), ViewModelProvider(this)[CurrentDestinationHoldingStore::class.java])

