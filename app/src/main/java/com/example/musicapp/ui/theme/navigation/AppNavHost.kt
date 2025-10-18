package com.example.musicapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicapp.ui.theme.presentation.detail.DetailScreen
import com.example.musicapp.ui.theme.presentation.home.HomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onAlbumClick = { albumId ->
                navController.navigate("detail/$albumId")
            })
        }
        composable(
            "detail/{albumId}",
            arguments = listOf(
                navArgument("albumId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId") ?: return@composable
            DetailScreen(albumId = albumId)
        }
    }
}