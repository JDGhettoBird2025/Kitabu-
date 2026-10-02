package com.example.kitabu.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kitabu.ui.components.BottomNavigationBar
import com.example.kitabu.ui.screens.BookDetailsScreen
import com.example.kitabu.ui.screens.HomeScreen
import com.example.kitabu.ui.screens.ProfileScreen
import com.example.kitabu.ui.screens.RentalsScreen
import com.example.kitabu.ui.screens.ReservationsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var selectedDuration by remember { mutableStateOf(7) }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {

            if (
                currentRoute == "home" ||
                currentRoute == "reservations" ||
                currentRoute == "rentals" ||
                currentRoute == "profile"
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute ?: "home",
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo("home") {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") {

                HomeScreen(
                    onBrowseBooksClick = {
                        navController.navigate("book_details")
                    },

                    onFeaturedBookClick = {
                        navController.navigate("book_details")
                    },
                    onReservationsClick = {
                        navController.navigate("reservations")
                    },
                    onRentalsClick = {
                        navController.navigate("rentals")
                    },
                    onProfileClick = {
                        navController.navigate("profile")
                    }
                )
            }

            composable("book_details") {

                BookDetailsScreen(
                    onReserveBookClick = { duration ->
                        selectedDuration = duration
                        navController.navigate("reservations")
                    }
                )
            }

            composable("reservations") {
                ReservationsScreen(
                    selectedDuration = selectedDuration
                )
            }

            composable("rentals") {
                RentalsScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
