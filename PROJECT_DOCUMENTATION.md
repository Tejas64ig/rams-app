# RAMS - Resource Allocation & Management System
## Complete Project Documentation

---

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Technology Stack](#technology-stack)
4. [Project Structure](#project-structure)
5. [Core Components](#core-components)
6. [Data Models](#data-models)
7. [Business Logic](#business-logic)
8. [Security](#security)
9. [Database Design](#database-design)
10. [API Endpoints](#api-endpoints)
11. [Workflow Diagrams](#workflow-diagrams)

---

## 🎯 Project Overview

### Purpose
RAMS is a centralized web-based platform designed to automate and optimize the allocation of critical healthcare resources in multi-specialty hospitals.

### Key Features
- **Patient Management** - Register, view, edit, and track patients
- **Doctor Management** - Manage doctors, specializations, and availability
- **Nurse Management** - Track nursing staff and shift management
- **Resource Management** - Manage hospital resources (beds, equipment, ambulances)
- **Appointment System** - Schedule and track patient appointments
- **Treatment Allocation** - Automated 13-step treatment allocation workflow
- **Department Management** - Organize hospital by departments
- **Dashboard Analytics** - Real-time hospital statistics

### Target Users
- Hospital Administrators
- Medical Staff
- Resource Managers

---

## 🏗️ Architecture

### 3-Layer Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│  Thymeleaf Templates (HTML + Bootstrap + JavaScript)        │
│  - login.html, dashboard.html, patients.html, etc.          │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP Requests/Responses
┌────────────────────────▼────────────────────────────────────┐
│                   BUSINESS LOGIC LAYER                       │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Controllers (11 total)                              │   │
│  │  - PatientController, DoctorController, etc.         │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Services                                            │   │
│  │  - TreatmentAllocationService (Sequence Diagram)     │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Configuration                                       │   │
│  │  - SecurityConfig, DataInitializer                   │   │
│  └─────────────────────────────────────────────────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │ Method Calls
┌────────────────────────▼────────────────────────────────────┐
│                  DATA ACCESS LAYER                           │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Repositories (8 total) - Spring Data JPA            │   │
│  │  - PatientRepository, DoctorRepository, etc.         │   │
│  └─────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Entities (11 total) - JPA Entities                  │   │
│  │  - Patient, Doctor, Nurse, Resource, etc.            │   │
│  └─────────────────────────────────────────────────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │ SQL Queries (Hibernate)
┌────────────────────────▼────────────────────────────────────┐
│                     DATABASE LAYER                           │
│  MySQL 8.0 - Database: ramsdb                               │
│  - Tables auto-generated from JPA entities                  │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 Technology Stack

### Backend
| Component | Technology | Version |
|-----------|------------|---------|
| Programming Language | Java | 17 |
| Framework | Spring Boot | 3.3.4 |
| Web MVC | Spring Web | 6.1.13 |
| Security | Spring Security | 6.3.3 |
| ORM | Hibernate (JPA) | 6.5.3 |
| Database | MySQL | 8.0+ |
| Build Tool | Maven | 3.9+ |
| Validation | Bean Validation | 3.0.2 |

### Frontend
| Component | Technology | Version |
|-----------|------------|---------|
| Template Engine | Thymeleaf | 3.1.2 |
| CSS Framework | Bootstrap | 5.3.3 |
| JavaScript | Vanilla JS | ES6+ |

### Additional Libraries
- **Lombok** - Reduces boilerplate code
- **HikariCP** - Database connection pooling
- **SLF4J + Logback** - Logging

---

## 📁 Project Structure

```
rams-app/
│
├── src/main/java/com/rams/
│   ├── RamsApplication.java                    # Main entry point
│   │
│   ├── config/                                 # Configuration classes
│   │   ├── SecurityConfig.java                 # Spring Security setup
│   │   └── DataInitializer.java                # Sample data loader
│   │
│   ├── controller/                             # MVC Controllers (11 files)
│   │   ├── LoginController.java                # Login & root redirect
│   │   ├── DashboardController.java            # Dashboard with stats
│   │   ├── PatientController.java              # Patient CRUD
│   │   ├── DoctorController.java               # Doctor CRUD
│   │   ├── NurseController.java                # Nurse management
│   │   ├── ResourceController.java             # Resource CRUD + release
│   │   ├── AppointmentController.java          # Appointment history
│   │   ├── TreatmentController.java            # Treatment history
│   │   ├── DepartmentController.java           # Department management
│   │   ├── AllocationController.java           # Treatment allocation
│   │   └── SetupController.java                # Quick data entry
│   │
│   ├── entity/                                 # JPA Entities (11 files)
│   │   ├── Hospital.java                       # Hospital entity
│   │   ├── Department.java                     # Department (composition)
│   │   ├── Staff.java                          # Abstract base class
│   │   ├── Doctor.java                         # Doctor (extends Staff)
│   │   ├── Nurse.java                          # Nurse (extends Staff)
│   │   ├── Notifiable.java                     # Interface
│   │   ├── Patient.java                        # Patient entity
│   │   ├── Appointment.java                    # Appointment entity
│   │   ├── Treatment.java                      # Treatment entity
│   │   ├── Resource.java                       # Resource + statechart
│   │   └── ResourceStatus.java                 # 11-state enum
│   │
│   ├── repository/                             # Spring Data JPA (8 files)
│   │   ├── HospitalRepository.java
│   │   ├── DepartmentRepository.java
│   │   ├── PatientRepository.java
│   │   ├── DoctorRepository.java
│   │   ├── NurseRepository.java
│   │   ├── ResourceRepository.java
│   │   ├── AppointmentRepository.java
│   │   └── TreatmentRepository.java
│   │
│   └── service/                                # Business logic services
│       ├── TreatmentAllocationService.java     # Sequence diagram logic
│       └── AllocationResult.java               # Value object
│
├── src/main/resources/
│   ├── application.properties                  # Configuration
│   └── templates/                              # Thymeleaf HTML (15 files)
│       ├── login.html                          # Login page
│       ├── dashboard.html                      # Dashboard with stats
│       ├── patients.html                       # Patient list + add
│       ├── patient-edit.html                   # Edit patient
│       ├── doctors.html                        # Doctor list + add
│       ├── doctor-edit.html                    # Edit doctor
│       ├── nurses.html                         # Nurse list + add
│       ├── resources.html                      # Resource list + add
│       ├── resource-edit.html                  # Edit resource
│       ├── appointments.html                   # Appointment history
│       ├── treatments.html                     # Treatment history
│       ├── departments.html                    # Department list + add
│       ├── setup.html                          # Quick setup
│       ├── allocate.html                       # Treatment allocation
│       └── fragments/layout.html               # Shared navbar
│
├── src/test/java/com/rams/
│   └── TreatmentAllocationServiceTest.java     # Unit tests
│
├── pom.xml                                     # Maven dependencies
├── README.md                                   # Project README
└── PROJECT_DOCUMENTATION.md                    # This file
```

---

## 🔧 Core Components

### 1. Controllers (Business Logic Layer)

#### **LoginController**
- **Route:** `/`, `/login`
- **Purpose:** Handles authentication pages
- **Methods:**
  - `root()` - Redirects to dashboard
  - `loginPage()` - Shows login form

#### **DashboardController**
- **Route:** `/dashboard`
- **Purpose:** Shows real-time hospital statistics
- **Displays:**
  - Total patients, doctors, nurses
  - Available vs allocated resources
  - Confirmed vs pending appointments
  - Quick action shortcuts

#### **PatientController**
- **Route:** `/patients`
- **Purpose:** Complete patient management
- **Operations:**
  - `GET /patients` - List all patients
  - `POST /patients` - Add new patient
  - `GET /patients/{id}/edit` - Edit form
  - `POST /patients/{id}` - Update patient
  - `POST /patients/{id}/delete` - Delete patient

#### **DoctorController**
- **Route:** `/doctors`
- **Purpose:** Doctor management with availability tracking
- **Operations:**
  - List, add, edit, delete doctors
  - Track busy/available status
  - Mark doctor free after treatment
  - Prevent double-booking

#### **ResourceController**
- **Route:** `/resources`
- **Purpose:** Manage hospital resources
- **Resource Types:**
  - ICU Beds, General Ward Beds
  - Operation Theatres
  - Medical Equipment (Ventilator, MRI, X-Ray, ECG)
  - Wheelchairs, Ambulances
- **Operations:**
  - List, add, edit, delete resources
  - Release allocated resources

#### **AllocationController**
- **Route:** `/allocate`
- **Purpose:** Implements 13-step treatment allocation
- **Process:**
  1. Patient requests treatment
  2. Check doctor availability
  3. Search for resource
  4. Allocate resource
  5. Create appointment
  6. Mark doctor busy
  7. Apply treatment

#### **AppointmentController**
- **Route:** `/appointments`
- **Purpose:** Track all appointments
- **Features:**
  - Chronological listing
  - Confirmed/Pending status
  - Patient-Doctor linkage

#### **TreatmentController**
- **Route:** `/treatments`
- **Purpose:** Treatment history
- **Displays:**
  - Treatment descriptions
  - Associated doctor and patient
  - Applied status

#### **DepartmentController**
- **Route:** `/departments`
- **Purpose:** Department organization
- **Operations:**
  - List, add, edit, delete departments
  - Track doctor and resource counts

---

### 2. Entities (Data Models)

#### **Hospital**
```java
@Entity
class Hospital {
    Long hospitalId;           // Primary key
    String name;               // Hospital name
    List<Department> departments; // One-to-many
}
```

#### **Department**
```java
@Entity
class Department {
    Long departmentId;         // Primary key
    String name;               // e.g., "Cardiology"
    Hospital hospital;         // Many-to-one
    List<Resource> resources;  // One-to-many (composition)
}
```

#### **Staff (Abstract)**
```java
@MappedSuperclass
abstract class Staff {
    Long staffId;              // Primary key
    String name;               // Staff name
    Department department;     // Many-to-one
    abstract boolean reportAvailability();
}
```

#### **Doctor (extends Staff, implements Notifiable)**
```java
@Entity
class Doctor extends Staff implements Notifiable {
    String specialization;     // e.g., "Cardiologist"
    boolean busy;              // Availability status
    List<Appointment> appointments;
    List<Treatment> treatments;
}
```

#### **Nurse (extends Staff, implements Notifiable)**
```java
@Entity
class Nurse extends Staff implements Notifiable {
    boolean onShift;           // Shift status
    Patient patient;           // Assigned patient
}
```

#### **Patient**
```java
@Entity
class Patient {
    Long patientId;            // Primary key
    String name;               // Patient name
    String medicalHistory;     // Medical history
    String lastRequestedTreatment; // Latest request
    List<Appointment> appointments;
    List<Treatment> treatments;
}
```

#### **Resource**
```java
@Entity
class Resource {
    Long resourceId;           // Primary key
    String type;               // e.g., "ICU Bed"
    ResourceStatus status;     // State machine
    Department department;     // Many-to-one (composition)
}
```

#### **ResourceStatus (Enum - Statechart)**
```java
enum ResourceStatus {
    AVAILABLE,
    REQUEST_LOGGED,
    VERIFYING,
    LOCATING,
    AWAITING_APPROVAL,
    ALLOCATED,
    RELEASED,
    MISMATCH_DETECTED,
    UNAVAILABLE,
    MAINTENANCE,
    RETIRED
}
```

#### **Appointment**
```java
@Entity
class Appointment {
    Long appointmentId;        // Primary key
    Patient patient;           // Many-to-one
    Doctor doctor;             // Many-to-one
    LocalDateTime date;        // Appointment date/time
    boolean confirmed;         // Confirmation status
}
```

#### **Treatment**
```java
@Entity
class Treatment {
    Long treatmentId;          // Primary key
    String description;        // Treatment details
    Patient patient;           // Many-to-one
    Doctor doctor;             // Many-to-one
    boolean applied;           // Application status
}
```

---

### 3. Repositories (Data Access Layer)

All repositories extend `JpaRepository<Entity, ID>` and provide:
- **CRUD operations** - `save()`, `findById()`, `findAll()`, `delete()`
- **Custom queries** - Optional custom methods
- **Automatic implementation** - Spring Data JPA magic

```java
@Repository
interface PatientRepository extends JpaRepository<Patient, Long> {}

@Repository
interface DoctorRepository extends JpaRepository<Doctor, Long> {}

// ... 6 more repositories
```

---

### 4. Services (Business Logic)

#### **TreatmentAllocationService**
Implements the **13-step sequence diagram** from SRS:

```java
public AllocationResult allocateTreatment(
    Long patientId, 
    String treatmentDescription, 
    String resourceType
) {
    // Step 1-3: Patient requests treatment
    Patient patient = findPatient(patientId);
    patient.requestTreatment(treatmentDescription);
    
    // Step 4-5: Check doctor availability
    Doctor availableDoctor = findAvailableDoctor();
    if (availableDoctor == null) {
        return AllocationResult.failure("No doctor available");
    }
    
    // Step 6-8: Locate resource
    Resource resource = findAvailableResource(resourceType);
    if (resource == null) {
        return AllocationResult.failure("Resource not found");
    }
    
    // Step 9-10: Allocate resource
    resource.setStatus(ResourceStatus.ALLOCATED);
    
    // Step 11: Mark doctor busy
    availableDoctor.setBusy(true);
    
    // Step 12: Create appointment
    Appointment appointment = new Appointment();
    appointment.schedule(patient, availableDoctor);
    appointment.confirm();
    
    // Step 13: Apply treatment
    Treatment treatment = new Treatment();
    treatment.setDescription(treatmentDescription);
    treatment.setApplied(true);
    
    // Save all changes
    saveAll();
    
    return AllocationResult.success(appointment, treatment);
}
```

---

## 🔒 Security (Spring Security)

### Configuration
- **Form-based authentication** - Username/password login
- **BCrypt password encoding** - Secure password hashing
- **In-memory user store** - Simple user management
- **Session management** - HTTP session tracking

### Security Rules
```java
http.authorizeHttpRequests(auth -> auth
    .requestMatchers("/login", "/css/**", "/js/**").permitAll()
    .anyRequest().authenticated()
);
```

### Default Credentials
- **Username:** `bro`
- **Password:** `bro`
- **Role:** `ADMIN`

---

## 🗄️ Database Design

### Entity-Relationship Diagram

```
Hospital 1──────* Department
                      │
                      ├──◆ Resource (composition - 1-to-many)
                      │
                      ├──○ Doctor (aggregation - 1-to-many)
                      │
                      └──○ Nurse (aggregation - 1-to-many)

Doctor 1──────* Appointment *──────1 Patient
Doctor 1──────* Treatment   *──────1 Patient
Nurse  *──────1 Patient
```

### Key Relationships
- **Composition:** Department ◆──> Resource (Resource cannot exist without Department)
- **Aggregation:** Department ○──> Doctor/Nurse (Can exist independently)
- **Association:** Doctor/Patient ←→ Appointment, Treatment

### Database Tables (Auto-generated by Hibernate)
1. `hospitals` - Hospital information
2. `departments` - Hospital departments
3. `doctors` - Doctor profiles (inherits from staff)
4. `nurses` - Nurse profiles (inherits from staff)
5. `patients` - Patient records
6. `resources` - Hospital resources
7. `appointments` - Appointment scheduling
8. `treatments` - Treatment records

---

## 🌐 API Endpoints

### Public Endpoints
- `GET /login` - Login page

### Protected Endpoints (Requires Authentication)

#### Dashboard
- `GET /dashboard` - Hospital statistics

#### Patients
- `GET /patients` - List all patients
- `POST /patients` - Add new patient
- `GET /patients/{id}/edit` - Edit patient form
- `POST /patients/{id}` - Update patient
- `POST /patients/{id}/delete` - Delete patient

#### Doctors
- `GET /doctors` - List all doctors
- `POST /doctors` - Add new doctor
- `GET /doctors/{id}/edit` - Edit doctor form
- `POST /doctors/{id}` - Update doctor
- `POST /doctors/{id}/delete` - Delete doctor
- `POST /doctors/{id}/mark-free` - Mark doctor available

#### Nurses
- `GET /nurses` - List all nurses
- `POST /nurses` - Add new nurse

#### Resources
- `GET /resources` - List all resources
- `POST /resources` - Add new resource
- `GET /resources/{id}/edit` - Edit resource form
- `POST /resources/{id}` - Update resource
- `POST /resources/{id}/delete` - Delete resource
- `POST /resources/{id}/release` - Release allocated resource

#### Appointments
- `GET /appointments` - List all appointments

#### Treatments
- `GET /treatments` - List all treatments

#### Departments
- `GET /departments` - List all departments
- `POST /departments` - Add new department

#### Allocation
- `GET /allocate` - Treatment allocation form
- `POST /allocate` - Process allocation

---

## 📊 Workflow Diagrams

### Treatment Allocation Sequence (13 Steps)

```
Patient          Doctor          Resource         System          Appointment
   │                │                │               │                 │
   ├─(1) Request───>│                │               │                 │
   │    Treatment   │                │               │                 │
   │                │                │               │                 │
   │                ├─(2) Check──────>               │                 │
   │                │   Availability  │               │                 │
   │                │                │               │                 │
   │                │<──(3) Available─┤               │                 │
   │                │                │               │                 │
   │                ├─(4) Search─────────────────────>│                 │
   │                │   Resource     │               │                 │
   │                │                │               │                 │
   │                │<──(5) Resource─┤<──────────────┤                 │
   │                │   Found        │               │                 │
   │                │                │               │                 │
   │                ├─(6) Allocate───>               │                 │
   │                │   Resource     │               │                 │
   │                │                │               │                 │
   │                │                ├─(7) Change────>                 │
   │                │                │   Status to    │                 │
   │                │                │   ALLOCATED    │                 │
   │                │                │               │                 │
   │                ├─(8) Mark Busy──>               │                 │
   │                │                │               │                 │
   │                ├─(9) Create─────────────────────────────────────>│
   │                │   Appointment  │               │                 │
   │                │                │               │                 │
   │<──(10) Confirm─┤<───────────────────────────────────────────────┤
   │    Appointment │                │               │                 │
   │                │                │               │                 │
   │                ├─(11) Apply─────>               │                 │
   │                │   Treatment    │               │                 │
   │                │                │               │                 │
   │<──(12) Confirm─┤                │               │                 │
   │    Treatment   │                │               │                 │
   │                │                │               │                 │
   └────────────────┴────────────────┴───────────────┴─────────────────┘
```

### Resource State Machine (Statechart)

```
  AVAILABLE
      │
      ├──> REQUEST_LOGGED
      │         │
      │         ├──> VERIFYING
      │         │       │
      │         │       ├──> LOCATING
      │         │       │       │
      │         │       │       ├──> AWAITING_APPROVAL
      │         │       │       │         │
      │         │       │       │         ├──> ALLOCATED
      │         │       │       │         │       │
      │         │       │       │         │       ├──> RELEASED
      │         │       │       │         │       │       │
      │         │       │       │         │       │       └──> AVAILABLE
      │         │       │       │         │       │
      │         │       │       │         │       └──> MAINTENANCE
      │         │       │       │         │               │
      │         │       │       │         │               └──> AVAILABLE
      │         │       │       │         │
      │         │       │       │         └──> MISMATCH_DETECTED
      │         │       │       │                   │
      │         │       │       │                   └──> VERIFYING
      │         │       │       │
      │         │       │       └──> UNAVAILABLE
      │         │       │               │
      │         │       │               └──> RETIRED (final)
```

---

## 🚀 Running the Application

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.9+ (or use IDE with built-in Maven)

### Steps
1. **Start MySQL** and ensure it's running on port 3306
2. **Configure database** credentials in `application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   Or use IDE: Run `RamsApplication.java`

4. **Access the application:**
   - URL: http://localhost:8080
   - Username: `bro`
   - Password: `bro`

### Sample Data
On first startup, the application automatically loads:
- 1 Hospital
- 5 Departments (Cardiology, Orthopedics, Neurology, Emergency, Pediatrics)
- 5 Doctors
- 5 Nurses
- 7 Patients
- 16 Resources
- 7 Appointments
- 5 Treatments

---

## 📝 Summary

**RAMS** is a complete hospital resource management system built with:
- **Spring Boot 3.3.4** for rapid development
- **Spring Security** for authentication
- **Spring Data JPA** for data persistence
- **Thymeleaf + Bootstrap** for responsive UI
- **MySQL** for reliable data storage
- **3-layer architecture** for maintainability

The application implements **5 UML diagrams** from the SRS:
1. ✅ Class Diagram - Entity relationships
2. ✅ Sequence Diagram - Treatment allocation flow
3. ✅ Statechart Diagram - Resource lifecycle
4. ✅ Use Case Diagram - User-feature mapping
5. ✅ Data Flow Diagram - System processes

**Total Lines of Code:** ~3,000+ lines
**Total Files:** 50+ files
**Development Time:** Academic semester project

---

## 👨‍💻 Developer
**Tejas V.S Thakur**
Shri Ramdeobaba University (RBU)
Department of Computer Science and Engineering

---

## 📄 License
Academic project for Software Engineering coursework.

---

*Last Updated: September 24, 2026*
