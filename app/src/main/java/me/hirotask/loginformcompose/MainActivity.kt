package me.hirotask.loginformcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hirotask.loginformcompose.model.SpConf
import me.hirotask.loginformcompose.model.firebase.FirebaseAuthConf
import me.hirotask.loginformcompose.model.firebase.FirestoreConf
import me.hirotask.loginformcompose.ui.LoginPage
import me.hirotask.loginformcompose.ui.TodoAddPage
import me.hirotask.loginformcompose.ui.TodoPage
import me.hirotask.loginformcompose.ui.WelcomePage
import me.hirotask.loginformcompose.ui.theme.LoginFormComposeTheme
import me.hirotask.loginformcompose.util.Todo

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
fun MyApp() {
    val navController = rememberNavController()
    var startDestination = Routing.Welcome.destination
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val spConf = SpConf(context)
    val firebaseAuthConf = FirebaseAuthConf()
    if(spConf.isSignIn()) {
        val account = spConf.getSavedAccountSession()
        val email = account.first
        val pass = account.second

        scope.launch(Dispatchers.IO) {
            firebaseAuthConf.signin(email, pass, context)
        }
        startDestination = Routing.Todo.destination
    }

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
                },
                onAddTodo = {
                    navController.navigate(Routing.Todo.destination)
                }
            )
        }
    }
}