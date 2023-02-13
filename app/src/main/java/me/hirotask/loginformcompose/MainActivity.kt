package me.hirotask.loginformcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.hirotask.loginformcompose.ui.*
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginFormComposeTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyApp(
    authViewModel: AuthViewModel = viewModel()
) {
    val navController = rememberNavController()
    val userState by authViewModel.userState.collectAsState()

    val startDestination =
        if (userState.isSignIn) Routing.Todo.destination else Routing.Welcome.destination


    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routing.Welcome.destination) {
            WelcomePage(
                onClickHandler = {
                    navController.navigate(Routing.Login.destination)
                },
            )

        }
        composable(Routing.Login.destination) {
            LoginPage(
                onPreviousHandler = { navController.navigate(Routing.Welcome.destination) },
                onSignInHandler = {
                    navController.navigate(Routing.Todo.destination)
                }
            )
        }
        composable(Routing.Todo.destination) {
            TodoPage(
                toSetting = {
                    navController.navigate(Routing.Settings.destination)
                },
                toLogin = {
                    navController.navigate(Routing.Login.destination)
                },
                toAdd = {
                    navController.navigate(Routing.TodoAdd.destination)
                }

            )
        }
        composable(Routing.TodoAdd.destination) {
            TodoAddPage(
                toTodo = {
                    navController.navigate(Routing.Todo.destination)
                },
                onAddTodo = {
                    navController.navigate(Routing.Todo.destination)
                }
            )
        }
        composable(Routing.Settings.destination) {
            SettingsPage(
                toTodo = {
                    navController.navigate(Routing.Todo.destination)
                },
                toLogin = {
                    navController.navigate(Routing.Login.destination)

                }
            )
        }
    }
}