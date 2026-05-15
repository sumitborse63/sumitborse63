package com.sktech.messmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sktech.messmate.ui.navigation.MessMateNavGraph
import com.sktech.messmate.ui.theme.MessMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessMateNavGraph()
                }
            }
        }
    }
}
