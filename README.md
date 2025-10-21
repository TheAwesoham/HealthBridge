# 🏥 HealthBridge

**HealthBridge** is a telemedicine platform designed to bridge the gap between doctors and patients in **rural and underserved areas**. It enables patients to find nearby doctors, book appointments, and conduct **real-time online consultations**, all while maintaining **security, scalability, and reliability**.

---

## 🚀 Tech Stack

- **Backend Framework:** Spring Boot  
- **Security:** Spring Security, JWT Authentication  
- **Database:** MySQL  
- **Communication:** WebSockets   
- **API Type:** RESTful APIs

---

## ✨ Key Features

- 🔒 **JWT Authentication** — Secure login and token-based access control  
- 👩‍⚕️ **Patient & Doctor Management** — Manage profiles, specializations, and appointments  
- 💬 **Real-time Chat** — WebSocket-based live communication between doctor and patient  
- 💾 **MySQL Database Integration** — Reliable and persistent data storage  
- ⚙️ **Stateless Architecture** — Ensures scalability using Spring Security and JWT  
- 🌐 **RESTful APIs** — Structured, maintainable, and scalable backend endpoints  

---

## 🏥 Healthcare Services

- 🕒 **Real-Time Appointments**  
  Patients can **book appointments instantly** and receive confirmation, ensuring a smooth and responsive booking experience.  

- 🧩 **Fault-Tolerant Booking**  
  Implements `@Transactional` to **prevent double-booking** and ensure **database consistency** under concurrent requests.  

- 🧹 **Expired Appointment Cleanup**  
  Uses **Spring Scheduler** jobs to automatically remove expired appointments and **free up database space**.  

- 💬 **Real-Time Chat**  
  Enables **WebSocket-based doctor-patient communication**, providing a seamless and secure messaging experience.  

---

## 🧠 System Overview

**The platform is built around a stateless, secure, and scalable architecture** ->
1. **Authentication Service** – Issues JWT tokens after validating credentials.  
2. **Doctor & Patient Modules** – Handle profile data and consultation logic.  
3. **Chat Service** – Enables secure WebSocket-based real-time communication.  
4. **Appointment Service** – Handles scheduling, booking, and cleanup jobs.  
5. **Database Layer** – Stores appointments, users, and session data in MySQL.  
