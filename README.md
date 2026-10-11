# Kitabu - Library Management App

# About the Project

Kitabu is an Android library management application that I developed using Kotlin and Jetpack Compose. The purpose of the app is to make it easier to browse books and manage reservations and rentals in one place.

I wanted to create an app where users can see which books are available, reserve a book, manage their rentals, and check their rental history.

# Features 

A few of Kitabu's features include: 

- Book Catalogue: Displays books with information such as the title, author and category.

- Search and Filter: Allows users to search for books by title or author and filter books by category.
  
- Book Reservations: Users can reserve available books and choose a reservation duration.

- Reservations Screen: Displays pending reservations, including reservation dates, deadlines and the remaining time.

- Cancel Reservations: Allows users to cancel pending reservations so the book becomes available again.

- Rentals: Displays active rentals and their deadlines.

- Renewals: Allows users to extend a rental deadline.

- Return Books: Allows users to return books and update their availability.

- Rental History: Displays previous returned and cancelled bookings.

# Technologies Used 

The main technologies used to create this Kitabu project include: 

- Kotlin
  
- Android Studio

- Jetpack Compose for the user interface

- Room database for local data storage

- ViewModel for managing screen data

- Repository pattern for handling data access

- Kotlin Flow for observing changes in data

- Navigation Compose for navigating between screens

# How the App Is Structured

I used the MVVM (Model-View-ViewModel) architecture to organise the application.

- The UI contains the screens that users interact with.
  
- The ViewModels handle screen-related data and connect the UI to the data layer.

- The repositories handle communication between the ViewModels and the database.

- The database contains the Room database, entities, DAOs, and related database functionality.

# Database 

The app uses Room to store book and booking information locally on the device.

The main database entities include:

- Book: Stores information about each book, including its title, author, category and availability.

- Booking: Stores reservation and rental information, including the associated book, booking date, return deadline, and status.

# Running the Project

To run the project:

- Download or clone the repository.
  
- Open the project in Android Studio.
  
- Allow Gradle to sync and download the required dependencies.
  
- Start an Android emulator or connect a compatible Android device.
  
- Run the application from Android Studio.

# What Was Learned

While working on Kitabu, I gained more experience with Kotlin and Android development. I also learned more about using Jetpack Compose to build interfaces, Room to store data, and ViewModels and repositories to organise an application. I had to work through build errors and make changes to get the different features working together. This helped me understand how the UI, business logic, and database connect in an Android application.
