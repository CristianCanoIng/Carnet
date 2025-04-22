package com.example.carnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.carnet.Items.Screen.navigation
import com.example.carnet.ui.theme.CarnetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarnetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){
                    navigation()
                }
            }
        }
    }
}
