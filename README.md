<div align="center">

# 🏥 RAMS — Resource Allocation & Management System

### For Multi-Speciality Hospitals

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<br/>

A centralized web-based platform that automates and optimizes the allocation of critical healthcare resources — doctors, nurses, ICU beds, operation theatres, medical equipment, and ambulances — across hospital departments.

<br/>

[🚀 Getting Started](#-getting-started) · [📸 Screenshots](#-screenshots) · [🏗️ Architecture](#️-architecture) · [📋 Features](#-features) · [🗂️ Project Structure](#️-project-structure)

</div>

---

## 📋 Features

### 🔐 Authentication & Security
- Spring Security with BCrypt password encryption
- Form-based login with session management
- Role-based access control (RBAC)
- Protected endpoints — all pages require authentication

### 📊 Admin Dashboard
- Real-time hospital statistics at a glance
- Patient, doctor, nurse, resource counts
- Available vs allocated resource breakdown
- Confirmed vs pending appointment tracking
- Quick-action shortcuts

### 👨‍⚕️ Doctor Management
- Add, edit, delete doctor profiles
- Track specialization and department
- Busy/Available status with visual badges
- Mark doctors free after treatment completion
- Prevents double-booking during allocation

### 🏥 Patient Management
- Full CRUD — register, view, edit, delete patients
- Medical history tracking
- Treatment request logging
- Linked appointments and treatment history

### 👩‍⚕️ Nurse Management
- Register and manage nursing staff
- On-shift / off-shift status tracking
- Patient assignment and care tracking

### 🛏️ Resource Management
- Track ICU beds, general ward beds, operation theatres, ventilators, MRI machines, X-ray, ECG monitors, wheelchairs, ambulances
- **Statechart-driven lifecycle:** `AVAILABLE → REQUEST_LOGGED → VERIFYING → LOCATING → AWAITING_APPROVAL → ALLOCATED → RELEASED → AVAILABLE`
- Color-coded status badges (🟢 Available, 🔴 Allocated, 🟡 In-transition)
- One-click release to free allocated resources

### 📅 Appointment Management
- Automatic appointment creation during treatment allocation
- Confirmed / Pending status tracking
- Chronological appointment history (newest first)
- Linked patient and doctor information

### 💊 Treatment Allocation
- Full **sequence diagram** implementation — 13-step automated flow
- Patient requests treatment → Doctor checks availability → Resource allocated → Treatment applied → Appointment confirmed
- Alt/Else branching: graceful handling when resources unavailable
- Doctor auto-marked busy after successful allocation

### 🏢 Department Management
- Create and manage hospital departments
- Track doctor and resource counts per department
- Composition relationship — resources belong to departments

### 📈 Sample Data
- Pre-loaded realistic hospital data on every startup
- 1 hospital, 5 departments, 5 doctors, 5 nurses, 7 patients
- 16 resources across 8 types, 7 appointments, 5 treatments
- Ready for immediate demo — no manual setup needed

---

## 🏗️ Architecture

### Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 25, Spring Boot 3.3.4 |
| **Security** | Spring Security 6 (BCrypt, form login) |
| **Database** | MySQL 8.0 (H2 in-memory also supported) |
| **ORM** | Spring Data JPA / Hibernate |
| **Frontend** | Thymeleaf 3.1, Bootstrap 5.3.3 |
| **Build** | Apache Maven 3.9+ |
| **Testing** | JUnit 5, Spring Boot Test |

### UML Diagram Compliance

This project implements 5 UML diagrams from the Software Requirements Specification:

| Diagram | What it defines | Implementation |
|---------|----------------|----------------|
| **Class Diagram** | 10 entity classes, inheritance, interfaces | `entity/*.java` — `Staff` ← `Doctor`/`Nurse`, `Notifiable` interface |
| **Sequence Diagram** | 13-step treatment allocation flow | `TreatmentAllocationService.allocateTreatment()` |
| **Statechart Diagram** | Resource state lifecycle (11 states) | `ResourceStatus` enum + transition methods in `Resource.java` |
| **Use Case Diagram** | Actor-feature mapping | 11 controllers + Spring Security |
| **DFD (Level 0)** | Data stores and processes | Repositories = data stores, Services = processes |

### Entity Relationship Overview

```
Hospital 1──* Department 1──◆ Resource (composition)
                          1──○ Doctor  (aggregation)
                          1──○ Nurse   (aggregation)

Doctor  1──* Appointment *──1 Patient
Doctor  1──* Treatment   *──1 Patient
Nurse   *──1 Patient

Staff (abstract) ← Doctor, Nurse
Notifiable (interface) ← Doctor, Nurse
```

---

## 🚀 Getting Started

### Prerequisites

| Requirement | Version |
|------------|---------|
| ☕ Java JDK | 25 |
| 🐬 MySQL | 8.0+ |
| 📦 Maven | 3.9+ |

### 1️⃣ Clone the repository

```bash
git clone https://github.com/Tejas64ig/rams-app.git
cd rams-app
```

### 2️⃣ Configure MySQL

Create the database:
```sql
CREATE DATABASE IF NOT EXISTS ramsdb;
```

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ramsdb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3️⃣ Build and run

```bash
mvn spring-boot:run
```

### 4️⃣ Open in browser

```
http://localhost:8080
```

Login credentials:
| Field | Value |
|-------|-------|
| **Username** | `bro` |
| **Password** | `bro` |

> 💡 **Tip:** Sample data (patients, doctors, resources, etc.) is automatically loaded on startup.

---

## 🗂️ Project Structure

```
rams-app/
├── src/main/java/com/rams/
│   ├── RamsApplication.java              # Spring Boot entry point
│   ├── config/
│   │   ├── SecurityConfig.java           # Spring Security + login config
│   │   └── DataInitializer.java          # Sample data loader
│   ├── controller/
│   │   ├── LoginController.java          # /login
│   │   ├── DashboardController.java      # /dashboard
│   │   ├── PatientController.java        # /patients CRUD
│   │   ├── DoctorController.java         # /doctors CRUD
│   │   ├── NurseController.java          # /nurses management
│   │   ├── ResourceController.java       # /resources + release
│   │   ├── AppointmentController.java    # /appointments history
│   │   ├── TreatmentController.java      # /treatments history
│   │   ├── DepartmentController.java     # /departments management
│   │   ├── AllocationController.java     # /allocate treatment flow
│   │   └── SetupController.java          # /setup quick data entry
│   ├── entity/
│   │   ├── Hospital.java                 # Hospital entity
│   │   ├── Department.java               # Department (composition with Resource)
│   │   ├── Staff.java                    # Abstract base class (@MappedSuperclass)
│   │   ├── Doctor.java                   # Extends Staff, implements Notifiable
│   │   ├── Nurse.java                    # Extends Staff, implements Notifiable
│   │   ├── Notifiable.java               # Interface — sendNotification()
│   │   ├── Patient.java                  # Patient entity
│   │   ├── Appointment.java              # Appointment entity
│   │   ├── Treatment.java                # Treatment entity
│   │   ├── Resource.java                 # Resource + statechart transitions
│   │   └── ResourceStatus.java           # 11-state enum (statechart)
│   ├── repository/                       # Spring Data JPA repositories (8 files)
│   └── service/
│       ├── TreatmentAllocationService.java  # Sequence diagram implementation
│       └── AllocationResult.java            # Success/failure value object
├── src/main/resources/
│   ├── application.properties            # MySQL + JPA config
│   └── templates/                        # Thymeleaf HTML templates
│       ├── login.html                    # Login page
│       ├── dashboard.html                # Dashboard with stats
│       ├── patients.html                 # Patient list + add
│       ├── patient-edit.html             # Edit patient
│       ├── doctors.html                  # Doctor list + add
│       ├── doctor-edit.html              # Edit doctor
│       ├── resources.html                # Resource list + add
│       ├── resource-edit.html            # Edit resource
│       ├── appointments.html             # Appointment history
│       ├── treatments.html               # Treatment history
│       ├── nurses.html                   # Nurse list + add
│       ├── departments.html              # Department list + add
│       ├── setup.html                    # Quick data setup
│       ├── allocate.html                 # Treatment allocation
│       └── fragments/layout.html         # Shared navbar
├── src/test/java/com/rams/
│   └── TreatmentAllocationServiceTest.java  # Unit tests (2 tests)
├── pom.xml                               # Maven dependencies
└── .gitignore
```

---

## 📸 Screenshots

| Page | Description |
|------|------------|
| `/login` | 🔐 Secure login with gradient UI |
| `/dashboard` | 📊 Live hospital stats with color-coded cards |
| `/patients` | 👤 Patient list with add/edit/delete |
| `/doctors` | 👨‍⚕️ Doctor list with busy/available badges |
| `/resources` | 🛏️ Resource table with status badges and release button |
| `/allocate` | 💊 Treatment allocation — sequence diagram in action |
| `/appointments` | 📅 Appointment history with confirmed/pending status |

---

## 📚 SRS Compliance

This project implements requirements from the **Software Requirements Specification (IEEE 830)** for RAMS:

| SRS Section | Requirement | Status |
|-------------|------------|--------|
| REQ-1 to REQ-8 | Patient Registration & Management | ✅ Implemented |
| REQ-9 to REQ-14 | Appointment Management | ✅ Implemented |
| REQ-15 to REQ-20 | Doctor Allocation | ✅ Implemented |
| REQ-21 to REQ-24 | Nurse Allocation | ✅ Implemented |
| REQ-25 to REQ-30 | Bed Allocation | ✅ Implemented |
| REQ-36 to REQ-40 | Equipment Management | ✅ Implemented |
| NFR-12 to NFR-16 | Authentication & Authorization | ✅ Implemented |
| NFR-1 | Response Time < 2s | ✅ Achieved |
| Section 2.4 | MySQL 8.0 Backend | ✅ Implemented |
| Section 3.1 | Login Interface | ✅ Implemented |
| Section 3.1 | Administrator Dashboard | ✅ Implemented |

---

## 🧪 Testing

```bash
# Run all tests
mvn test

# Expected output:
# Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
# BUILD SUCCESS
```

| Test | What it verifies |
|------|-----------------|
| `allocatesSuccessfully_whenResourceAvailable` | Sequence diagram `[resource available]` branch |
| `returnsResourceNotFound_whenNoMatchingResourceExists` | Sequence diagram `[else]` branch |

---

## 🔧 Configuration

### Switch to H2 (in-memory, for quick testing)

Edit `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:ramsdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Change Login Credentials

Edit `SecurityConfig.java`:
```java
var admin = User.builder()
        .username("your-username")
        .password(encoder.encode("your-password"))
        .roles("ADMIN")
        .build();
```

---

## 👤 Author

**Tejas V.S Thakur**
- 🎓 Shri Ramdeobaba University (RBU)
- 📧 Department of Computer Science and Engineering

---

## 📄 License

This project is developed as part of academic coursework for Software Engineering.

---

<div align="center">

**Built with ❤️ using Spring Boot + MySQL + Thymeleaf**

</div>
