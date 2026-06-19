package com.xiaoyv.fuck.scroll.top

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.xiaoyv.fuck.scroll.top.ui.theme.FuckScrollTopTheme
import io.github.libxposed.service.XposedService

class MainActivity : ComponentActivity(), App.ServiceStateListener {
    private val viewModel by viewModels<MainViewModel>()

    override fun onServiceStateChanged(service: XposedService?) {
        viewModel.updateService(service)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuckScrollTopTheme {
                MainScreen(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        App.addServiceStateListener(this, true)
    }

    override fun onStop() {
        App.removeServiceStateListener(this)
        super.onStop()
    }
}


