package com.example.kitabu.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onBrowseBooksClick: () -> Unit,
    onReservationsClick: () -> Unit,
    onRentalsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onFeaturedBookClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "KITABU",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Your Digital Library",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onBrowseBooksClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Browse Books")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Featured Book",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    onFeaturedBookClick()
                }
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "The Great Gatsby",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "F. Scott Fitzgerald",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "A classic American novel about wealth, ambition and the American Dream.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Available to reserve",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Quick Access",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = onReservationsClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Reservations")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onRentalsClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Rentals")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onProfileClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Profile")
        }
    }
}