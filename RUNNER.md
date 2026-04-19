# 🏥 Smart Clinic System – Run Guide

This project is a **Spring Boot + Thymeleaf + MySQL** web application.

---

# 🚀 1. Prerequisites

Make sure the following are installed:

- Java JDK 17+
- MySQL Server (8+)
- VS Code / IntelliJ
- Git (optional)

---

# 🗄️ 2. Database Setup

Open MySQL Workbench or terminal and run:

```sql
CREATE DATABASE clinicdb;
```

(Optional: create user)

```sql
CREATE USER 'clinicuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON clinicdb.* TO 'clinicuser'@'localhost';
FLUSH PRIVILEGES;
```

---

# ⚙️ 3. Configure Application

Open:

```
src/main/resources/application.properties
```

Set:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinicdb
spring.datasource.username=clinicuser
spring.datasource.password=1234

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.thymeleaf.cache=false
server.port=8080
```

---

# 📦 4. Build Project

Run:

```bash
.\mvnw.cmd clean install
```

---

# ▶️ 5. Run Application

```bash
.\mvnw.cmd spring-boot:run
```

---

# 🌐 6. Open in Browser

### Consultation Module

```
http://localhost:8080/consultations
```

### Billing Module

```
http://localhost:8080/billing
```

---

# 🔄 7. Application Flow

1. Add Consultation
2. Complete Consultation
3. Click "Generate Bill"
4. Fill bill details
5. Save bill
6. Click "Pay"
7. View payment history

---

# 🧪 8. Verify Database

```sql
SELECT * FROM bill;
SELECT * FROM payment;
```

---

# ❗ 9. Common Issues

### Port already in use

Change:

```properties
server.port=8081
```

---

### MySQL connection error

- Check username/password
- Ensure MySQL is running

---

### Tables not created

- Ensure `spring.jpa.hibernate.ddl-auto=update`

---

# 📌 10. Notes

- Billing and Consultation modules are integrated
- Payment status is dynamically reflected in consultation UI
- No duplicate data storage between modules

---

# ✅ 11. Final Check

- App runs without errors ✔
- UI loads ✔
- Bills generated ✔
- Payments recorded ✔
- Data visible in MySQL ✔

---

# 🎯 Done!

You can now use the system or present it for evaluation.
