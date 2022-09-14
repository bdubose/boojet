package me.branwin.boojet.navigation

interface BoojetDestination {
    val route: String
}

object TipCalculator : BoojetDestination {
    override val route = "tip_calculator"
}

object TipCalculator2 : BoojetDestination {
    override val route = "tip_calctwo"
}

val boojetTabRowScreens = listOf(TipCalculator, TipCalculator2)