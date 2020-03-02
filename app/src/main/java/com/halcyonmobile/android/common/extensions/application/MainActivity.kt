package com.halcyonmobile.android.common.extensions.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halcyonmobile.android.common.extensions.application.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

}
