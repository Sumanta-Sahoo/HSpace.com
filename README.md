# HSpace.com

HSpace.com is a full-featured, web-based hotel booking application designed to provide users with an easy and intuitive way to find, book, and manage hotel stays. The platform follows a **role-based access control** model, ensuring secure access for various types of users, such as guests, hotel administrators, and system managers.

---

## Table of Contents

1. [Features](#features)
2. [Technology Stack](#technology-stack)
3. [Installation](#installation)
4. [Usage](#usage)
5. [API Endpoints](#api-endpoints)
6. [Contributing](#contributing)
7. [License](#license)
8. [Contact](#contact)

---

## Features

### **Authentication & Authorization**
- **JWT-based Authentication**: Secure login, registration, and role-based authorization.
- **Role-Based Access Control (RBAC)**: Different roles with permissions (guest, admin, hotel manager).
- **Profile Management**: Users can update personal information, view booking history, and manage payment details.

### **Hotel Search and Booking**
- **Search Functionality**: Search for hotels based on location, check-in/check-out dates, and number of guests.
- **Advanced Filtering**: Filter search results by price, star ratings, amenities, and distance from landmarks.
- **Hotel Details**: Each hotel has a dedicated page with images, room details, reviews, and nearby attractions.
- **Booking**: Users can book rooms and receive a confirmation with payment receipt.

### **Admin Features**
- **Hotel Management**: Admins and hotel managers can add and manage hotels, rooms, and pricing.
- **Booking Management**: Admins can monitor all bookings, cancellations, and payments.
- **User Reviews**: Admins can moderate user reviews and respond to feedback.

### **Additional Features**
- **Discounts & Coupons**: Admins can offer special promotions to users.
- **Interactive Map Integration**: Hotels are displayed on a map for better location insight.
- **Multilingual Support**: Available in multiple languages to cater to international users.
- **Mobile Responsiveness**: Fully optimized for mobile devices.

---

## Technology Stack

### **Backend**
- **Java**: Core backend language.
- **Spring Boot**: Backend framework for building the RESTful API.
- **MySQL**: Relational database for storing user data, bookings, and hotel information.
- **Maven**: Dependency and project management.

### **Frontend**
- **ReactJS**: Dynamic and responsive user interface.
- **HTML5/CSS3**: Structure and styling of the web pages.
- **JavaScript**: For adding interactive elements to the frontend.

### **Cloud & Storage**
- **AWS S3**: Used for file and image storage for hotels and user profiles.
- **AWS EC2**: For hosting the application in the cloud.

### **Version Control & Collaboration**
- **GitHub**: For version control, collaboration, and issue tracking.

---

## Installation

Follow the steps below to install and run the project locally.

### Prerequisites
- **Java 11+**
- **Maven**
- **MySQL**
- **Node.js & npm**

### Backend Setup
1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/HSpace.com.git
    cd HSpace.com/backend
    ```

2. **Configure MySQL Database**:
    - Create a new MySQL database:
    ```sql
    CREATE DATABASE hspace_db;
    ```

    - Update the `application.properties` file with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hspace_db
    spring.datasource.username=yourUsername
    spring.datasource.password=yourPassword
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build and Run the Backend**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Frontend Setup
1. **Navigate to the frontend folder**:
    ```bash
    cd ../frontend
    ```

2. **Install dependencies**:
    ```bash
    npm install
    ```

3. **Start the React Development Server**:
    ```bash
    npm start
    ```

---

## Usage

### Access the Application:
- **Frontend**: Visit `http://localhost:3000/` to access the React frontend.
- **Backend API**: The Spring Boot server will run on `http://localhost:8080/`.

### Create User Account:
- Navigate to the signup page and create an account.
- Once signed in, you can search for hotels, view details, and make a booking.

### Admin Access:
- Access admin features like managing hotels and users by signing in with an admin account.

---

## API Endpoints

### Authentication
- **POST /api/auth/signup** - Register a new user
- **POST /api/auth/login** - Login a user and generate JWT

### Hotel Search & Booking
- **GET /api/hotels** - List all hotels based on filters
- **POST /api/bookings** - Create a new booking
- **GET /api/bookings/{userId}** - View all bookings by a user

### Admin Routes
- **POST /api/hotels** - Add a new hotel (admin only)
- **PUT /api/hotels/{hotelId}** - Update hotel details (admin only)
- **DELETE /api/hotels/{hotelId}** - Remove a hotel (admin only)

For a complete list of endpoints, refer to the API documentation.

---

## Contributing

We welcome contributions! Please follow the steps below to contribute to HSpace.com:

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-name`).
5. Open a Pull Request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

## Contact

If you have any questions or issues, feel free to contact the project maintainers.

- **Sumanta Sahoo**: [LinkedIn](https://www.linkedin.com/in/sumanta-s14/)
- **Email**: sumantasahoo138@gmail.com

---
