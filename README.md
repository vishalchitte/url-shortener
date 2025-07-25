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
📧 vishalchitte9999@gmail.com

---

### 🔔 **Notes**

- CORS enabled for frontend integration  
- Simple in-memory H2 database used  
- No external frameworks for HttpServer

---

# url-shortener
This is a simple URL shortener application built using:

- Java HttpServer (com.sun.net.httpserver.HttpServer)
- H2 Database with JDBC
- Plain HTML, CSS, JavaScript frontend
- SLF4J for logging
- JUnit 5 and Mockito for testing

## Features

- Anonymous URL shortening
- User registration and login
- Custom URL shortening for logged-in users

## How to Run

> Steps will be updated after development.
 How to Run
Follow these steps to run the project locally:

Clone the repository

bash
Copy
Edit
git clone https://github.com/vishalchitte9999@gmail/url-shortener.git
cd url-shortener
Open the project in STS (Spring Tool Suite)

File → Import → Existing Maven Project → Select this folder

Build the project

Right-click on the project → Maven → Update Project (or mvn clean install in terminal)

Run the backend server

Go to Main.java

Right-click → Run As → Java Application

Console should show:

nginx
Copy
Edit
Server started on port 8080
Serve the frontend files

Using VS Code Live Server:

Open frontend folder

Right-click index.html → Open with Live Server

Access via http://localhost:5500/index.html

Or using Python HTTP server:

In terminal:

bash
Copy
Edit
cd src/frontend
python -m http.server 5500
Access via http://localhost:5500/index.html

Test APIs with Postman (Optional)

Send POST requests to:

http://localhost:8080/register

http://localhost:8080/login

http://localhost:8080/shorten

http://localhost:8080/custom-shorten

✔ Ensure backend server is running while testing.

 Final Notes
Default backend runs on port 8080

Frontend served via port 5500 (Live Server or Python)

H2 database used (no external DB setup needed)
