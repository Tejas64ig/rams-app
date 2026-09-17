# RAMS Web App — Starter Scaffold

Implements the "Resource Allocation and Management System for Multi-Specialty
Hospitals" case study (Experiments 2–6) as a running Spring Boot web app.

## How to run

Requires **JDK 17+** and **Maven** (or use your IDE's built-in Maven support —
IntelliJ/Eclipse will detect `pom.xml` automatically and give you a Run button).

```bash
mvn spring-boot:run
```

Then open:
- `http://localhost:8080/setup` — create test Patients, Doctors, Resources
- `http://localhost:8080/allocate` — runs the full sequence-diagram flow
- `http://localhost:8080/h2-console` — inspect the live in-memory database
  (JDBC URL: `jdbc:h2:mem:ramsdb`, user: `sa`, blank password)

## Run the tests

```bash
mvn test
```

`TreatmentAllocationServiceTest` proves both branches of the `alt` fragment
from the sequence diagram: resource-available (confirmed) and
resource-unavailable (`resourceNotFound()`).

## What maps to what

| Diagram (Experiment) | Code |
|---|---|
| Class Diagram (3) | `entity/*.java` — same classes, attributes, relationships |
| Sequence Diagram (4) | `service/TreatmentAllocationService.allocateTreatment()` — same message names, same alt/else |
| Statechart (5) | `entity/ResourceStatus.java` + transition methods on `Resource.java` |
| Use Case Diagram (2) | Each controller endpoint (`/setup`, `/allocate`) corresponds to a use case |
| DFD (6) | Repository layer = the data stores (D1–D4); services = the numbered processes |

## Extending this

- Add `AppointmentController`, `PatientController` etc. as separate pages the
  same way `SetupController` does it, once you need full CRUD per module.
- Swap H2 for MySQL by editing `application.properties` (instructions inline).
- Add Spring Security later if you need real login (matches SRS section 3.1
  Login Interface / NFR-12–16).
