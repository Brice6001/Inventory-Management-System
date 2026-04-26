# Inventory Management System for End-User Equipment

**Developed by:**  
- 24RP05647 – IGIRANEZA Daniella  
- 24RP15903 – NGOGA Private  
- 24RP10518 – NSABIMANA Brice  

**Client:** Airtel  

---

##  Project Overview

This is a fully **offline Windows‑based inventory management system** that tracks end‑user devices (laptops, desktops, mobile phones). It ensures accurate asset ownership, condition monitoring, issue/return processes – even without internet connectivity.

The application is built with:

- **Java Swing** – for the graphical user interface (desktop app)
- **Spring Boot** – dependency injection, transaction management, data persistence
- **MySQL** – local relational database
- **Maven** – build and dependency management

---

## 🔐 System Administrator Credentials

The application includes a **login screen**. Use the following admin credentials to access all features:

- **Username:** `24RP05647`
- **Password:** `24RP15903`

> The admin user is automatically seeded into the database when the application runs for the first time (via `data.sql`).

---

##  Key Features

- **Login Security** – Only authenticated users can access the system.
- **Asset Registration** – Register devices with serial number, type, brand, model, specifications.
- **Employee Management** – Add, view employees (ID, name, department, email).
- **Assignment & Ownership Tracking** – Assign a device to an employee, record condition at assignment.
- **Condition Monitoring** – Log device condition when returned (Excellent, Good, Damaged, Missing Parts).
- **Issue & Return Management** – Full lifecycle from assignment to return.
- **Audit History** – Every action (register, assign, return) is logged with timestamp and username.
- **Reporting & Filtering** – View devices filtered by type and status; view full audit log.
- **Fully Offline** – No internet required; runs entirely on a local Windows machine with MySQL.

---

##  How the System Works

### 1. **Database Layer (MySQL)**

- Six main tables: `device`, `employee`, `assignment`, `condition_log`, `audit_log`, `sys_user`.
- Relationships: One device can be assigned multiple times; each assignment links a device to an employee.
- Hibernate (JPA) automatically creates/updates the schema when the application starts (`ddl-auto=update`).
- `data.sql` seeds the admin user automatically.

### 2. **Backend (Spring Boot)**

- **Entities** – Java classes mapped to database tables.
- **Repositories** – Spring Data JPA interfaces for CRUD operations.
- **Service Layer** (`InventoryService`) – Contains all business logic:
  - Register device (status = "Available")
  - Add employee
  - Assign device (checks availability, creates assignment, updates status)
  - Return device (logs condition, closes assignment, updates status)
  - Authenticate user (checks `sys_user` table)
  - Audit logging (writes to `audit_log` table)

### 3. **User Interface (Java Swing)**

- **Login dialog** – appears first; validates credentials against the database.
- **Main window** – `JTabbedPane` with six tabs:
  1. **Register Device** – form to input device details.
  2. **Add Employee** – form to add employee records.
  3. **Assign Device** – serial number, employee ID, condition dropdown.
  4. **Return Device** – serial number, return condition, remarks.
  5. **View All Devices** – table of devices with filter by type and status.
  6. **Audit Log** – table showing all actions with timestamps.
- Each tab is a separate `JPanel` class that receives `InventoryService` via constructor.
- Data loads on demand using `SwingUtilities.invokeLater` to keep UI responsive.

### 4. **Application Flow Example**

1. **Login** – using admin credentials.
2. **Register a device** → saved with status 'Available'.
3. **Add an employee** → saved.
4. **Assign device** → service checks availability, creates assignment, updates device status to 'Assigned', writes audit log.
5. **Return device** → service finds active assignment, logs condition in `condition_log`, sets `returned_date`, updates device status to 'Available', writes audit log.
6. **View devices** → table shows updated status.
7. **Audit log** → shows complete history.

---


