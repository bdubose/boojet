package me.branwin.boojet.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.branwin.boojet.ui.entry.EntryScreen
import me.branwin.boojet.ui.list.ListScreen
import me.branwin.boojet.ui.tipcalculator.TipCalculatorScreen
import me.branwin.boojet.viewmodels.MainViewModel

@Composable
fun BoojetNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Entry.route,
        modifier = modifier
    ) {
        composable(Entry.route) {
            EntryScreen(viewModel, snackbarHostState)
        }
        composable(List.route) {
            ListScreen(viewModel)
        }
        composable(TipCalculator.route) {
            TipCalculatorScreen()
        }
    }
}

fun NavHostController.navSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }