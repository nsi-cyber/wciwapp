package com.nsicyber.wciwapp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen.MovieDetailScreen
import com.nsicyber.wciwapp.presentation.detailScreen.showDetailScreen.ShowDetailScreen
import com.nsicyber.wciwapp.presentation.exploreScreen.ExploreScreen
import com.nsicyber.wciwapp.presentation.exploreScreen.ExploreScreenViewModel
import com.nsicyber.wciwapp.presentation.personScreen.PersonDetailScreen
import com.nsicyber.wciwapp.presentation.searchScreen.SearchScreen
import com.nsicyber.wciwapp.presentation.searchScreen.SearchScreenViewModel
import com.nsicyber.wciwapp.presentation.splashScreen.SplashScreen


@Preview
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    exploreScreenViewModel: ExploreScreenViewModel = hiltViewModel<ExploreScreenViewModel>(),
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel<SearchScreenViewModel>(),
    startDestination: String = Constants.Routes.SPLASH_SCREEN,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    val isMenuShow = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val showBottomBar = currentDestination in listOf(
        Constants.Routes.EXPLORE_SCREEN,
        Constants.Routes.SEARCH_SCREEN,
    )

    Scaffold(
        bottomBar = {

            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            ) {
                BottomNavigation(
                    navigationActions = navActions,
                    currentDestination = currentDestination,
                    isMenuShow = isMenuShow,
                    onMenuChanged = { bool -> isMenuShow.value = bool }
                )
            }


        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                            if (isMenuShow.value) {
                                isMenuShow.value = false
                                focusManager.clearFocus()
                            }
                        }
                    }
                }
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier.padding(innerPadding),
            ) {


                composable(route = Constants.Routes.SPLASH_SCREEN) {
                    SplashScreen() {
                        navActions.navigateToExploreScreen()

                    }
                }
                composable(route = Constants.Routes.SEARCH_SCREEN) {
                    SearchScreen(
                        searchScreenViewModel = searchScreenViewModel,
                        onMovieDetailClicked = {
                            navActions.navigateToMovieDetailScreen(
                                it ?: 0
                            )
                        }, onShowDetailClicked = {
                            navActions.navigateToShowDetailScreen(
                                it ?: 0
                            )
                        },
                        onPersonDetailClicked = { navActions.navigateToPersonDetailScreen(it ?: 0) }


                    )
                }
                composable(route = Constants.Routes.EXPLORE_SCREEN) {
                    ExploreScreen(
                        exploreScreenViewModel = exploreScreenViewModel,
                        onMovieDetailClicked = { navActions.navigateToMovieDetailScreen(it) },
                        onSearchClicked = { navActions.navigateToSearchScreen() },
                        onShowDetailClicked = { navActions.navigateToShowDetailScreen(it) },
                    )
                }

                composable(
                    route = "${Constants.Routes.MOVIE_DETAIL_SCREEN}/{movieId}",
                    arguments = listOf(navArgument("movieId") {
                        type = NavType.IntType
                    })
                ) {
                    MovieDetailScreen(movieId = it.arguments?.getInt("movieId")
                        ?: 0,
                        onMovieDetailClicked = { navActions.navigateToMovieDetailScreen(it) },
                        onPersonDetailClicked = { navActions.navigateToPersonDetailScreen(it) })

                }



                composable(
                    route = "${Constants.Routes.PERSON_DETAIL_SCREEN}/{personId}",
                    arguments = listOf(navArgument("personId") {
                        type = NavType.IntType
                    })
                ) {
                    PersonDetailScreen(
                        personId = it.arguments?.getInt("personId")
                            ?: 0,
                        onMovieDetailClicked = { navActions.navigateToMovieDetailScreen(it) },
                        onShowDetailClicked = { navActions.navigateToShowDetailScreen(it) },
                    )

                }



                composable(
                    route = "${Constants.Routes.SHOW_DETAIL_SCREEN}/{showId}",
                    arguments = listOf(navArgument("showId") {
                        type = NavType.IntType
                    })
                ) {
                    ShowDetailScreen(showId = it.arguments?.getInt("showId")
                        ?: 0,
                        onShowDetailClicked = { navActions.navigateToShowDetailScreen(it) },
                        onPersonDetailClicked = { navActions.navigateToPersonDetailScreen(it) }
                    )

                }


            }
        }


    }
}