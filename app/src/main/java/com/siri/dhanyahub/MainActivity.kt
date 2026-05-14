package com.siri.dhanyahub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.siri.dhanyahub.data.repository.AppRepository
import com.siri.dhanyahub.ui.SiriDhanyaAppRoot
import com.siri.dhanyahub.ui.theme.SiriDhanyaTheme
import com.siri.dhanyahub.viewmodel.MainViewModel

class
MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as SiriDhanyaApp
        val factory = MainViewModel.Factory(app.repository)

        setContent {
            SiriDhanyaTheme {
                SiriDhanyaAppRoot(viewModel(factory = factory))
            }
        }
    }
}
