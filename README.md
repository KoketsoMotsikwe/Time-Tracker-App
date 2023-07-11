TimeUp Time Tracking App
The TimeUp Time Tracking App is a mobile application designed to help users track their time spent on various activities and tasks. The app allows users to log in, create categories for timesheet entries, record timesheet entries with details such as date, start and end times, description, and category. Users can also optionally attach photographs to each entry. The app enables users to set minimum and maximum daily goals for hours worked. It provides features to view a list of timesheet entries, access photos associated with entries, view total hours spent on each category during a selected period, and visualize the total hours worked each day over a user-selectable period.
This README file provides an overview of the app's features, technologies used, and instructions for setting up and running the app.

Features
The TimeUp Time Tracking App offers the following features:

User Authentication: Users can log in to the app using their username and password.
Category Management: Users can create categories to organize their timesheet entries.
Timesheet Entry Creation: Users can create timesheet entries by providing information such as date, start and end times, description, and category.
Photo Attachment (Optional): Users can optionally add a photograph to each timesheet entry.
Goal Setting: Users can set a minimum and maximum daily goal for hours worked.
Timesheet Entry Viewing: Users can view a list of all timesheet entries created during a user-selectable period. If a photo was stored for an entry, users can access it from the entry list.
Category Hours Summary: Users can view the total number of hours spent on each category during a user-selectable period.
Hours Worked Graph: Users can view a graph showing the total hours worked each day over a user-selectable period. The graph also displays the minimum and maximum goals.
Goal Achievement Visualization (Monthly Overview): Users can visualize their performance in staying between their minimum and maximum hour goals over the past month.
Online Database Storage (Firebase): The app stores data in an online database using Firebase.


Technologies Used

The TimeUp Time Tracking App is built using the following technologies:

Android Studio: The integrated development environment (IDE) used to develop the Android application.
Kotlin: The programming language used for app development.
Firebase: An online database service provided by Google, used for storing and retrieving app data.
Canva: A graphic design tool used for designing the app's user interface (UI).

Setup Instructions
To set up and run the TimeUp Time Tracking App, please follow these steps:

Install Android Studio:

Download and install Android Studio from the official website: https://developer.android.com/studio
Clone the Repository:

Clone the TimeUp Time Tracking App repository to your local machine using Git or download the source code as a ZIP file.
Repository URL: https://github.com/Koketso1992/Time-Tracker-App
Open the Project in Android Studio:

Launch Android Studio.
Select "Open an existing Android Studio project" or "Import project (Gradle, Eclipse ADT, etc.)" option.
Navigate to the cloned/downloaded repository folder and select the project.
Set Up Firebase:

Create a Firebase project in the Firebase Console: https://console.firebase.google.com
Enable the Firebase Realtime Database service for the project.
Download the google-services.json configuration file from the Firebase Console.
Place the google-services.json file in the app module directory: /app of the project.
Build and Run the App:

Build the project using Android Studio's build tools.
Connect an Android device or start an emulator.
Run the app on the connected device/emulator using the "Run" button in Android Studio.
References
During the development of the TimeUp Time Tracking App, the following time tracking apps were researched for inspiration:

Clockify: https://clockify.me/
Toggl: https://toggl.com/
RescueTime: https://www.rescuetime.com/



