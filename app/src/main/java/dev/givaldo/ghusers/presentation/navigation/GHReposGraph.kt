package dev.givaldo.ghusers.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.givaldo.ghusers.presentation.features.detail.UserDetailScreen
import dev.givaldo.ghusers.presentation.features.repos.ReposScreen
import dev.givaldo.ghusers.presentation.features.users.UserListScreen

private const val APP_ROUTE = "ghrepos_graph"
private const val USER_LIST_ROUTE = "user_list"
private const val USER_DETAIL_ROUTE = "user_detail"
private const val REPOS_ROUTE = "repos"

private const val USERNAME_ARG = "username"

data class UsernameArgs(val username: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull<String>(savedStateHandle[USERNAME_ARG]))
}

@Composable
fun GHReposNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = APP_ROUTE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        appGraph(navController)
    }
}

fun NavController.navigateToUserDetail(
    username: String,
    navOptions: NavOptions? = null
) {
    navigate("$USER_DETAIL_ROUTE/$username", navOptions)
}

fun NavController.navigateToRepos(
    username: String,
    navOptions: NavOptions? = null
) {
    navigate("$REPOS_ROUTE/$username", navOptions)
}

fun NavGraphBuilder.appGraph(
    navController: NavController,
) {
    navigation(
        route = APP_ROUTE,
        startDestination = USER_LIST_ROUTE,
    ) {
        composable(USER_LIST_ROUTE) {
            UserListScreen(
                navigateToUserDetail = {
                    navController.navigateToUserDetail(it)
                },
            )
        }
        composable(
            route = "$USER_DETAIL_ROUTE/{$USERNAME_ARG}"
        ) {
            UserDetailScreen(
                navigateToRepos = {
                    navController.navigateToRepos(it)
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "$REPOS_ROUTE/{$USERNAME_ARG}"
        ) {
            ReposScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
