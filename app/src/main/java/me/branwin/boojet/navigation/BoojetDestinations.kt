package me.branwin.boojet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

interface BoojetDestination {
    val route: String
    val label: String
}

interface BoojetNavbarDestination : BoojetDestination {
    val icon: ImageVector
}

object TipCalculator : BoojetNavbarDestination {
    override val route = "tip_calculator"
    override val label = "Tip Calculator"
    override val icon = Icons.Filled.Dining
}

object Entry : BoojetNavbarDestination {
    override val route = "entry"
    override val label = "Entry"
    override val icon = Icons.Filled.Edit
}

object List : BoojetNavbarDestination {
    override val route = "list"
    override val label = "List"
    override val icon = Icons.Filled.List
}

val boojetTabRowScreens = listOf(Entry, List, TipCalculator)