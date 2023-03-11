package me.hirotask.loginformcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.hirotask.loginformcompose.ui.*
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.ui.viewmodel.AuthViewModel
import me.hirotask.loginformcompose.ui.viewmodel.TodoViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginFormComposeTheme {
                val authViewModel = hiltViewModel<AuthViewModel>()
                val todoViewModel = hiltViewModel<TodoViewModel>()

                MyApp(authViewModel, todoViewModel)
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyApp(
    authViewModel: AuthViewModel = viewModel(),
    todoViewModel: TodoViewModel = viewModel()
) {
    val navController = rememberNavController()
    val userState by authViewModel.userState.collectAsState()

    val startDestination =
        if (userState.isSignIn) Routing.Todo.destination else Routing.Welcome.destination


    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routing.Welcome.destination) {
            WelcomeScreen(
                onClickHandler = {
                    navController.navigate(Routing.Login.destination)
                },
            )

        }
        composable(Routing.Login.destination) {
            LoginScreen(
                onPreviousHandler = { navController.navigate(Routing.Welcome.destination) },
                onSignInHandler = {
                    navController.navigate(Routing.Todo.destination)
                },
                authViewModel
            )
        }
        composable(Routing.Todo.destination) {
            TodoScreen(
                toLogin = {
                    navController.navigate(Routing.Login.destination)
                },
                toSetting = {
                    navController.navigate(Routing.Settings.destination)
                },
                toAdd = {
                    navController.navigate(Routing.TodoAdd.destination)
                },
                todoViewModel,
                authViewModel
            )
        }
        composable(Routing.TodoAdd.destination) {
            TodoAddScreen(
                toTodo = {
                    navController.navigate(Routing.Todo.destination)
                },
                onAddTodo = {
                    navController.navigate(Routing.Todo.destination)
                },
                todoViewModel
            )
        }
        composable(Routing.Settings.destination) {
            SettingsScreen(
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