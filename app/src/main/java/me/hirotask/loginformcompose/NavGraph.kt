package me.hirotask.loginformcompose

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.ui.*
import me.hirotask.loginformcompose.ui.components.AppModalDrawer

@Composable
fun TodoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestinations: String = Destinations.Welcome.destination,
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestinations

    NavHost(
        navController = navController,
        startDestination = startDestinations,
        modifier = modifier,
    ) {
        composable(
            Destinations.Welcome.destination
        ) {
            WelcomeScreen(
                onClickHandler = {
                    navActions.navigateToLogin()
                },
            )
        }

        composable(Destinations.Login.destination) {
            LoginScreen(
                onPreviousHandler = { navController.popBackStack() },
                onSignInHandler = {
                    navActions.navigateToTodo()
                }
            )
        }

        composable(Destinations.Todo.destination) {
            AppModalDrawer(drawerState = drawerState, currentRoute = currentRoute , navigationActions = navActions) {
                TodoScreen(
                    toAdd = {
                        navActions.navigateToTodoAdd()
                    },
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }

        composable(Destinations.TodoAdd.destination) {
            AppModalDrawer(drawerState = drawerState, currentRoute = currentRoute , navigationActions = navActions) {
                TodoAddScreen(
                    toTodo = {
                        navActions.navigateToTodo()
                    },
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }

        composable(Destinations.Settings.destination) {
            AppModalDrawer(drawerState = drawerState, currentRoute = currentRoute , navigationActions = navActions) {
                SettingsScreen(
                    toTodo = {
                        navActions.navigateToTodo()
                    },
                    toLogin = {
                        navActions.navigateToLogin()
                    },
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }

    }
}