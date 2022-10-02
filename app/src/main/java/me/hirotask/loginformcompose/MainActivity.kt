package me.hirotask.loginformcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.hirotask.loginformcompose.components.pages.CalendarPage
import me.hirotask.loginformcompose.components.pages.LoginPage
import me.hirotask.loginformcompose.components.pages.WelcomePage
import me.hirotask.loginformcompose.firebase.FirebaseConf
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme

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
                onSignInHandler = { navController.navigate(Routing.Calendar.destination) }
            )
        }
        composable(Routing.Calendar.destination) {
            CalendarPage(
                drawerContent1Action = { navController.navigate(Routing.Login.destination) },
                drawerContent2Action = {
                    navController.navigate(Routing.Login.destination)
                }

            )
        }
    }
}