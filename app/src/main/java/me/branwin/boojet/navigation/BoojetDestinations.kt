package me.branwin.boojet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import me.branwin.boojet.R

interface BoojetDestination {
    val route: String
    val label: Int
}

interface BoojetNavbarDestination : BoojetDestination {
    val icon: ImageVector
}

object TipCalculator : BoojetNavbarDestination {
    override val route = "tip_calculator"
    override val label = R.string.tip_calculator_screen_label
    override val icon = Icons.Filled.Dining
}

object Entry : BoojetNavbarDestination {
    override val route = "entry"
    override val label = R.string.entry_screen_label
    override val icon = Icons.Filled.Add
}

object List : BoojetNavbarDestination {
    override val route = "list"
    override val label = R.string.list_screen_label
    override val icon = Icons.Filled.List
}

val boojetTabRowScreens = listOf(Entry, List, TipCalculator)