package com.example.tranquiljot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tranquiljot.ui.screens.detailsScreen.DetailsScreen
import com.example.tranquiljot.ui.screens.detailsScreen.DetailsScreenViewModel
import com.example.tranquiljot.ui.screens.entryScreen.EntryScreen
import com.example.tranquiljot.ui.screens.homeScreen.HomeScreen
import com.example.tranquiljot.ui.screens.notesApplication


enum class Screens {
    HOME, ENTRY, DETAILS
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HOME.name) {
        composable(route = Screens.HOME.name) {
            HomeScreen(
                navigateDetails = { id ->
                    navController.navigate("${Screens.DETAILS.name}/$id")
                },
                navigateEntry = {
                    navController.navigate(Screens.ENTRY.name)
                }
            )
        }
        composable(route = Screens.DETAILS.name+"/{id}") {
            DetailsScreen(navHostController = navController, viewModel = viewModel(factory = viewModelFactory {
                initializer {
                    DetailsScreenViewModel(
                        notesRepository = notesApplication().container.notesRepository,
                        id = it.arguments?.getString("id")?.toInt() ?: 0
                    )
                }
            }))
        }
        composable(route = Screens.ENTRY.name) {
            EntryScreen (navController = navController)
        }
    }
}