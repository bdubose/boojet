package me.branwin.boojet.navigation

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
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TipCalculator.route,
        modifier = modifier
    ) {
        composable(Entry.route) {
            EntryScreen(viewModel)
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