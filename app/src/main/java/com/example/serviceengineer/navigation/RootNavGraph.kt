package com.example.serviceengineer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.serviceengineer.screens.helpers.MainScreen
import com.google.firebase.auth.FirebaseAuth

private val startDestinationRoot = mutableStateOf(Graph.ROOT)

@Composable
fun CheckStatus() {
    val mAuth = FirebaseAuth.getInstance()
    val cUser = mAuth.currentUser
    if (cUser != null)
        startDestinationRoot.value = Graph.MAIN
    else
        startDestinationRoot.value = Graph.AUTHENTICATION
}

@Composable
fun RootNavigationGraph(navController: NavHostController){
    CheckStatus()
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestinationRoot.value
    ) {
        authenticationNavGraph(navController = navController)
        composable(route = Graph.MAIN){
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root"
    const val AUTHENTICATION = "authentication"
    const val MAIN = "main"
}
