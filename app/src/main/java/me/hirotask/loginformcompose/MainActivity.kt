package me.hirotask.loginformcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.hirotask.loginformcompose.ui.LoginPage
import me.hirotask.loginformcompose.ui.TodoAddPage
import me.hirotask.loginformcompose.ui.TodoPage
import me.hirotask.loginformcompose.ui.WelcomePage
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginFormComposeTheme {

                MyApp()

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routing.Welcome.destination) {
        composable(Routing.Welcome.destination) {
            WelcomePage {
                navController.navigate(Routing.Login.destination)
            }
        }
        composable(Routing.Login.destination) {
            LoginPage(
                onPreviousHandler = { navController.navigate(Routing.Welcome.destination) },
                onSignInHandler = { navController.navigate(Routing.Todo.destination) }
            )
        }
        composable(Routing.Todo.destination) {
            TodoPage(
                toSetting = {
                    navController.navigate(Routing.Login.destination)
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
                }
            )
        }
    }
}