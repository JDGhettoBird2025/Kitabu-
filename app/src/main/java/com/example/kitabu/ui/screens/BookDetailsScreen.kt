package com.example.kitabu.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookDetailsScreen(
    onReserveBookClick: (Int) -> Unit
) {
    var showReservationDialog by remember {mutableStateOf(false)}
    var selectedDuration by remember {mutableStateOf(7)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Book Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "The Great Gatsby",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "F. Scott Fitzgerald",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "The Great Gatsby is a classic American novel that explores " +
                    "wealth, ambition, love and the American Dream during the " +
                    "Jazz Age.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Availability",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Available",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {
                showReservationDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reserve Book")
        }

        if(showReservationDialog){
            AlertDialog(
                onDismissRequest = {
                    showReservationDialog = false
                },
                title = {
                    Text("Reserve Book")
                },
                text = {
                    Column{
                        Text("Choose rental duration:")

                        Spacer(modifier = Modifier.height(12.dp))

                        TextButton(
                            onClick = {
                                selectedDuration = 7
                            }
                        ) {
                            Text("7 Days")
                        }

                        TextButton(
                            onClick = {
                                selectedDuration = 14
                            }
                        ) {
                            Text("14 Days")
                        }

                        TextButton(
                            onClick = {
                                selectedDuration = 21
                            }
                        ) {
                            Text("21 Days")
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showReservationDialog = false
                            onReserveBookClick(selectedDuration)
                        }
                    ) {
                        Text("Reserve")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showReservationDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}