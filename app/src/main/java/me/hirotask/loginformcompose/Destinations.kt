package me.hirotask.loginformcompose

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

enum class Destinations(val destination: String) {
    Welcome("welcomepage"),
    Login("loginpage"),
    Todo("Todopage"),
    TodoAdd("TodoAddpage"),
    Settings("Settingspage")
}

class TodoNavigationActions(private val navController: NavController) {
    fun navigateToWelcome() {
        navController.navigate(Destinations.Welcome.destination) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToLogin() {
        navController.navigate(Destinations.Login.destination) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToTodo() {
        navController.navigate(Destinations.Todo.destination) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToTodoAdd() {
        navController.navigate(Destinations.TodoAdd.destination)
    }

    fun navigateToSettings() {
        navController.navigate(Destinations.Settings.destination)
    }
}