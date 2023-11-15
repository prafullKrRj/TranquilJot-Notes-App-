package com.example.tranquiljot

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tranquiljot.ui.NavigationGraph

@Composable
fun NotesApp(navController: NavHostController) {
    NavigationGraph(navController = navController)
}