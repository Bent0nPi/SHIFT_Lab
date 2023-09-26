# SHIFT_Lab
This repository contains a test task for the SHIFT lab in the direction of Mobile development.

In my work, I developed an application with two screens: registration and the main window. 
Registration consists of several fields and a register button. There is a button on the main screen, when pressed, a modal window appears with a greeting. 

The project is executed according to the MVVM pattern. Sqlite is used to cache user and session data, so that in the future it would be possible 
to log in by name and password (there are tools for this), but since the task said to implement only registrations, there is no login window. 
Passwords are also hashed when writing to the database.
