# 🎬 BookMyShow Backend (Spring Boot)

A **custom-built Spring Boot backend project** inspired by BookMyShow, focused on clean REST API design for managing users, movies, theatres, shows, seats, and ticket bookings.

This repository is created for **learning, practice, and interview preparation**, with emphasis on DTO-based architecture and real-world workflows.

---

## 🚀 Key Features

### 👤 User Module

* Create and manage users
* Fetch user details via REST APIs

### 🎥 Movie Module

* Add and view movies
* Fetch movie listings

### 🏢 Theatre & Screen Module

* Manage theatres and screens
* Seat layout support

### 🎟️ Booking Module

* Ticket booking flow
* Seat selection support
* Booking details & history

### 💳 Payment (Structure Ready)

* Payment DTOs and extendable flow

---

## 🛠️ Tech Stack

* **Java (8+)**
* **Spring Boot**
* **Spring MVC**
* **Spring Data JPA**
* **Hibernate**
* **MySQL**
* **Maven**
* **Lombok**
* **Swagger / OpenAPI**

---

## 📂 Project Structure

```
BookMyShow
 └── src/main/java
     └── com.cfs.BookMyShow
         ├── controller
         ├── dto
         ├── service
         ├── repository
         └── entity
```

---

## 🔗 Sample API Endpoints

### User APIs

* `POST /user/addNew`
* `GET /user/{id}`

### Movie APIs

* `POST /movie/add`
* `GET /movie/all`

### Booking APIs

* `POST /booking/create`
* `GET /booking/{id}`

---

## 📑 Swagger API Documentation

After starting the application, open:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## ⚙️ Getting Started

### Clone the Repository

```bash
git clone https://github.com/<your-username>/BookMyShow.git
cd BookMyShow
```

### Configure Database

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Application runs at: **[http://localhost:8080](http://localhost:8080)**

---

## 🎯 Learning Outcomes

* RESTful API development with Spring Boot
* DTO-based clean architecture
* JPA & Hibernate mappings
* Git & GitHub workflow

---

## 🚧 Future Enhancements

* JWT-based authentication
* Role-based authorization
* Real-time seat locking
* Payment gateway integration

---

## 👨‍💻 Author

**Dev Pratap Singh Chauhan**

---

⭐ If you like this project, feel free to star the r
