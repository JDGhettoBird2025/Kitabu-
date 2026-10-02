package com.example.kitabu.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                onNavigate("home")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "reservations",
            onClick = {
                onNavigate("reservations")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = "Reservations"
                )
            },
            label = {
                Text("Reservations")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "rentals",
            onClick = {
                onNavigate("rentals")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Rentals"
                )
            },
            label = {
                Text("Rentals")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                onNavigate("profile")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }
}
