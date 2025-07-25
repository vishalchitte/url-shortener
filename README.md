# URL Shortener Assignment

## ✅ Project Overview

This is a simple **URL Shortener application** developed as part of Merito assignment.

### 🔧 **Tech Stack**

- Java (HttpServer)
- JDBC + H2 Database
- HTML, CSS, JS (Frontend)

---

## ✅ Features

✔ Shorten URLs anonymously  
✔ User Registration and Login  
✔ Custom short URLs for logged-in users  
✔ Redirect to original URL  
✔ Simple REST API endpoints

---

## ✅ API Endpoints

| Endpoint           | Method | Description                     |
|---------------------|--------|---------------------------------|
| `/shorten`         | POST   | Anonymous URL shortening       |
| `/register`        | POST   | Register new user              |
| `/login`           | POST   | User login                     |
| `/custom-shorten`  | POST   | Custom URL shortening (user)   |
| `/{shortCode}`     | GET    | Redirect to original URL       |

---

## ✅ How to Run

1. Clone the repository  
2. Import as Maven project in STS  
3. Run **Main.java**  
4. Access frontend via Live Server or Python http.server

---

## ✅ Testing

- JUnit tests for **DB connection and server start**
- Manual testing via **Postman and frontend pages**

---

## ✅ Developer

👤 **Your Name**  
📧 your.email@example.com

---

### 🔔 **Notes**

- CORS enabled for frontend integration  
- Simple in-memory H2 database used  
- No external frameworks for HttpServer

---
