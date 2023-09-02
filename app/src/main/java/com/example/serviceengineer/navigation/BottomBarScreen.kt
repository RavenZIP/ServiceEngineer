package com.example.serviceengineer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
){
    object Home: BottomBarScreen(
        route = "Home",
        icon = Icons.Outlined.Cottage,
    )

    object Clients: BottomBarScreen(
        route = "Clients",
        icon = Icons.Outlined.People,
    )

/*    object Search: BottomBarScreen(
        route = "Search",
        icon = Icons.Outlined.Search,
    )*/

    object Book: BottomBarScreen(
        route = "Book",
        icon = Icons.Outlined.Book,
    )

    object Work: BottomBarScreen(
        route = "Work",
        icon = Icons.Outlined.Handyman,
    )

/*    object More: BottomBarScreen(
        route = "More",
        icon = Icons.Outlined.MenuOpen,
    )*/
    object UserProfile: BottomBarScreen(
        route = "UserProfile",
        icon = Icons.Outlined.AccountCircle,
    )
}
