# BookPublishing-Android

# Book Publishing Project

## Overview
This project entails the development of an Android application for managing book publishing information. It incorporates various functionalities including displaying publisher details, adding, updating, and deleting records, managing books and chapters, and more.

## Project Components

### a) Creating Android Project
- Create an Android Project named BookPublishingProject to add widgets to layout resource defining UI activity.
- Add TextView and Button widgets accordingly to allow mobile users to read and interact with publisher information stored in an ArrayList and display each element within TextView widget.
- Utilize SQLiteDatabase to store all publishing objects into the database.

### b) SQLiteDatabase Publishing
- Implement a system where a given publisher publishes a set of books and each book contains a number of chapters.
- Implement all SQLiteDatabase constraints shown in the Entity Relationship Diagram (ERD).

### c) Model Classes
- Create Publisher, Book, and Chapter classes representing Model classes of the MVC pattern.
- Define data attributes of every publishing record.
- Include default constructor, constructor with parameters, setters, and getters.
- Implement a method within the Book class called `calculate_Price_Euro()` to convert book price from CAN$ to Euro.

### d) MainActivity and Fragments
- Create MainActivity class to host fragment PublisherDisplayFragment class.
- Add buttons to skip through each element of Publisher ArrayList and display publisher info within TextView widgets.
- Create PublisherActivity class to host fragment PublisherUpdateFragment class.
- Add PublisherUpdateFragment to PublisherActivity.
- Instantiate Intent object carrying extra parameters between fragments.

### e) Publisher Update
- Implement UI for updating publisher information.
- Add button to MainActivity to inflate UI for PublisherUpdateFragment.
- Handle updating of publisher information.

### f) Book Management
- Implement UI for managing books.
- Create BookActivity class to host fragment BookFragment class.
- Add BookFragment to BookActivity.
- Handle adding book records, managing book types, and adding chapters to books.

### g) Chapter Management
- Implement UI for managing chapters.
- Create ChapterActivity class to host fragment ChapterFragment class.
- Add ChapterFragment to ChapterActivity.
- Handle adding chapter records.

### h) Toolbar Menu
- Add Toolbar menu with sub-menu in all activities of the Book Publishing project.
- Include Vector Asset images within the toolbar menu.

 
