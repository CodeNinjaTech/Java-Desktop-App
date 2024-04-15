# Java Exercise on GUI and Databases

Desktop App Project for the Java Course of the Department of Programmers-Analysts of the School of Computer Programmers of the Hellenic Armed Forces

## Description

A parking lot charges $2.00 minimum cost for parking up to 3 hours. For each hour or part of an hour over 3, it charges an additional $0.50. The maximum charge for a full 24-hour period is $10.00.

Write an application that calculates and displays the parking charge for each customer who parks in the parking lot. You should record the hours each customer parked each time and then display their charge and the current collections for that day. You will need to create a `calculateCharges` method which will calculate and return the charge amount for each customer.

Build the application using a database and extend the functionality according to the following minimum requirements:

- Record in the database the entry and exit of each car with the registration number, time, and date given by the user.
- For the case where a car is left inside the car park at the close of the day, it shall be considered to leave at that time at the maximum parking cost.
- Assume that the parking can have up to 50 cars at the same time.

### Main Screen Features
- Display information about the current status of the parking: the number of cars in the parking and the free spaces, the current total collection for the day.
- Include a `JTextArea` where all the history is kept, i.e., car entries and exits with hours of stay and corresponding costs in the order they came and left (see the append method of JTextArea for adding text).

### User Options
- Use menus and buttons for user interaction.
- Entering the parking lot:
  - Insert license plate.
  - Enter arrival time (date is automatically entered from the system date).
  - Store record in the database.
  - Update parking status.
- Exiting from the parking lot:
  - Enter license plate.
  - Check that a plate is present in the base.
  - Enter exit time.
  - Calculate the payment amount according to the hours of stay.
  - Remove the car from the car park.
  - Update parking status.

- Save all transactions of the day from JTextArea using JFileChooser and showSaveDialog.
- Start a new day where they empty/zero out any variables needed and any components needed (e.g., Textarea).
- Show a second window with the possibility to search for cars by license plate or by date: use JTable where license plate, date, entry time, exit time, and cost of stay for each car is displayed.
- Exit from the program.

Only the files with a java extension you created will be sent.
