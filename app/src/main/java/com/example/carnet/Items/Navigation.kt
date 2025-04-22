package com.example.carnet.Items.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carnet.Items.Mascota
import com.example.carnet.Items.ScreenA
import com.example.carnet.Items.ScreenB

@Preview(showBackground = true)
@Composable
fun navigation() {
    val navController = rememberNavController()
    val mascotaList = remember { mutableStateListOf<Mascota>() }

    NavHost(navController = navController, startDestination = "ScreenA") {
        composable("ScreenA") {
            ScreenA(navController, mascotaList)
        }
        composable("ScreenB") {
            ScreenB(navController, mascotaList)
        }
    }
}

