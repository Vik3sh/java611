# Experiment8: Servlets, JSP, JDBC

- Part A: User Login using Servlet + HTML form
- Part B: Display Employee records via JDBC + Servlet
- Part C: Student Attendance via JSP + Servlet

## Setup
1. Use Tomcat 10.1+ (Jakarta Servlet 6). Put `mysql-connector-j` on the server/lib classpath.
2. Deploy this folder as a webapp (context root `/`).
3. Configure DB in `WEB-INF/db.properties`.

```properties
url=jdbc:mysql://localhost:3306/college
username=root
password=
```

## Example schema
```sql
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(100)
);

CREATE TABLE employees (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  department VARCHAR(100),
  salary DECIMAL(10,2)
);

CREATE TABLE attendance (
  id INT PRIMARY KEY AUTO_INCREMENT,
  student_id VARCHAR(20),
  att_date DATE,
  status VARCHAR(10)
);
```

- Demo login works with username `admin` and password `admin123` if DB is not ready.

## URLs
- GET `/login` → `login.html`
- POST `/login` → `LoginServlet`
- GET `/employees` → list employees
- GET `/attendance` → `attendance.jsp`
- POST `/attendance` → record attendance

## Notes
- Servlets are in `src/com/example/servlet` and use `DBUtil` reading `WEB-INF/db.properties`.

