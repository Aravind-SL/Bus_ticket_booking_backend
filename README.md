---

# Bus Ticket Booking System - Backend

Welcome to the Bus Ticket Booking System backend repository. This project provides a comprehensive solution for bus ticket booking, including user authentication, bus management, route management, seat selection, and booking functionality.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Components](#components)
- [Error Handling](#error-handling)
- [Installation](#installation)
- [License](#license)

## Overview

The Bus Ticket Booking System backend is built using Java and Spring Boot. It includes various services and components to handle user registration, authentication, bus and route management, seat selection, and booking processes. This project is designed to be scalable, secure, and easy to maintain.

## Architecture

The system is divided into several components:

1. **User Component**: Handles user registration, login, and JWT token management.
  2. **Booking Component**: Manages the booking process, including bus search, seat selection, and Date selection.
3. **Admin Component**: Allows administrators to manage buses and routes.
4. **Bus Component**: Handles bus-related operations and seat management.
5. **Route Component**: Manages routes between different stations.
6. **Station Component**: Manages station details.

## Components

### User Component

- **Registration**: Handles user registration with form validation.
- **Login**: Handles user authentication and JWT token management.
- **Authentication Service**: Manages authentication logic including OTP for password reset.

### Booking Component

- **Search Buses**: Allows users to search for buses based on routes and dates.
- **Select Seats**: Allows users to select available seats.

### Admin Component

- **Manage Buses**: Allows admins to add, update, and delete buses.
- **Manage Routes**: Allows admins to add, update, and delete routes.

### Bus Component

- **Bus Management**: Handles bus-related operations including CRUD operations for buses.
- **Seat Management**: Manages seat availability and assignment.

### Route Component

- **Route Management**: Handles CRUD operations for routes.

### Station Component

- **Station Management**: Manages station details including CRUD operations.

## Error Handling

The system includes a comprehensive error-handling mechanism to manage various exceptions:

1. **UserAlreadyExistException**: Thrown during registration if the email is already registered.
2. **PasswordMismatchException**: Thrown during password reset if the passwords do not match.
3. **UserNotFoundException**: Thrown during login or password reset if the user does not exist.

## Installation

To run this project locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/Aravind-SL/bus-ticket-booking-backend.git
    cd bus-ticket-booking-backend
    ```

2. **Install dependencies**:
    Make sure you have Java and Maven installed. Then run:
    ```bash
    mvn clean install
    ```

3. **Set up the database**:
    - Install and run PostgreSQL. Ensure it is running on your local machine.
    - Create a database for the project.
    - Configure your database settings in `application.properties`:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
      spring.datasource.username=your-username
      spring.datasource.password=your-password
      ```

4. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

## License

This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/license/mit) file for details.

---
