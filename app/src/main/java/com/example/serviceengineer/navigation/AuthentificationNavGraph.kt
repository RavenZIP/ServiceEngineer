package com.example.serviceengineer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.serviceengineer.screens.registerAndLogin.*

fun NavGraphBuilder.authenticationNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = WelcomeScreen.Welcome.route
    ){
        composable(route = WelcomeScreen.Welcome.route){
            WelcomeScreen(
                registrationClick = {
                    navController.navigate(WelcomeScreen.Registration.route)
                },
                signInClick = {
                    navController.navigate(WelcomeScreen.Login.route)
                }
            )
        }
        composable(route = WelcomeScreen.Login.route){
            SignInScreen(
                signInCompleteClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                forgotPasswordClick = {
                    navController.navigate(WelcomeScreen.ForgotPassword.route)
                }
            )
        }
        composable(route = WelcomeScreen.ForgotPassword.route){
            ForgotPasswordScreen(

            )
        }
        composable(route = WelcomeScreen.Registration.route){
            RegistrationScreen(
                registrationCompleteClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                }
            )
        }
    }
}

sealed class WelcomeScreen(val route: String){
    object Welcome: WelcomeScreen(route = "Welcome")
    object Login: WelcomeScreen(route = "Login")
    object ForgotPassword: WelcomeScreen(route = "ForgotPassword")
    object Registration: WelcomeScreen(route = "Registration")
}