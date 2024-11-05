package com.nsicyber.wciwapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.nsicyber.wciwapp.common.Constants


fun NavOptionsBuilder.popUpToTop(navController: NavController, inclusive: Boolean = false) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        this.inclusive = inclusive
    }
}

class NavigationActions(private val navController: NavHostController) {

    fun navigateToExploreScreen() {
        navController.navigate(Constants.Routes.EXPLORE_SCREEN) {
            popUpToTop(navController)
        }
    }

    fun navigateToSearchScreen() {
        navController.navigate(Constants.Routes.SEARCH_SCREEN) {
            popUpToTop(navController)
        }
    }

    fun navigateToMovieDetailScreen(movieId: Int) {
        navController.navigate(
            "${Constants.Routes.MOVIE_DETAIL_SCREEN}/${movieId}"
        ) {
            popUpToTop(navController)
        }
    }

    fun navigateToShowDetailScreen(showId: Int) {
        navController.navigate(
            "${Constants.Routes.SHOW_DETAIL_SCREEN}/${showId}"
        ) {
            popUpToTop(navController)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }


}
