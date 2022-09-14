package me.branwin.boojet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.branwin.boojet.ui.tipcalculator.TipCalculatorScreen

@Composable
fun BoojetNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TipCalculator.route,
        modifier = modifier
    ) {
        composable(TipCalculator.route) {
            TipCalculatorScreen()
        }
        composable(TipCalculator2.route) {
            TipCalculatorScreen()
        }
    }
}

fun NavHostController.navSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }