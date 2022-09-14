package me.branwin.boojet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.branwin.boojet.navigation.*
import me.branwin.boojet.ui.theme.BoojetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoojetApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoojetApp() {
    BoojetTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = boojetTabRowScreens.find { it.route == currentDestination?.route } ?: TipCalculator
        Scaffold(
            bottomBar = {
                BoojetNavigationBar(
                    currentScreen = currentScreen,
                    onTabSelected = {
                        navController.navSingleTopTo(it.route)
                    })
            }
        ) { innerPadding ->
            BoojetNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun BoojetNavigationBar(
    allScreens: List<BoojetDestination> = boojetTabRowScreens,
    currentScreen: BoojetDestination,
    onTabSelected: (BoojetDestination) -> Unit
) {
    NavigationBar {
        allScreens.forEach {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = it.route) },
                label = { Text(it.route) },
                selected = currentScreen == it,
                onClick = { onTabSelected(it) }
            )
        }
    }
}