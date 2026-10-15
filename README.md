# Pet Care Management System

A traditional Java web application built for **Java 8 + JSP + Servlets (`javax.servlet`) + JDBC + MySQL + Apache Tomcat 7**. It is designed as an Eclipse Dynamic Web Project and does not use Spring, Maven, Gradle, Hibernate, or frontend frameworks.

## Features
- Registration, login, logout, and session-based access control
- PBKDF2-HMAC-SHA256 password hashing (passwords are not stored as plaintext)
- Pet CRUD with owner-level access checks
- Services listing; service create/update/delete for ADMIN
- Appointment booking, listing, cancellation, date validation, and basic slot conflict checking
- Medical record listing for owners; create for ADMIN/STAFF
- Vaccination history listing for owners; create for ADMIN/STAFF
- Responsive plain HTML/CSS interface
- Six core tables: `users`, `pets`, `services`, `appointments`, `medical_records`, `vaccinations`

## Requirements
- JDK 8
- Apache Tomcat 7
- MySQL 5.7+ (or a compatible MySQL version)
- Eclipse IDE for Enterprise Java and Web Developers (or Eclipse with Web Tools)
- MySQL Connector/J 5.1.49 (Java 8 compatible; place JAR in `WebContent/WEB-INF/lib/`)

> Tomcat 7 is end-of-life. This project targets your requested learning environment; do not expose an old Tomcat 7 server directly to the public internet. For a public deployment, use a supported server/runtime and review security settings.

## 1. Create the database
Open MySQL Command Line Client and run the SQL in `database/petcarestore.sql`. If your MySQL account is not `root`, update `src/com/petcare/util/DBConnection.java` with a local development username/password. Never commit real credentials to GitHub.

## 2. Import into Eclipse
1. Extract this ZIP.
2. In Eclipse, choose **File → Import → Existing Projects into Workspace** and select the extracted `PetCareManagementSystem` folder. If Eclipse does not recognize it, create a **Dynamic Web Project** named `PetCareManagementSystem` with target runtime **Apache Tomcat v7.0**, then copy `src`, `WebContent`, and `database` into it.
3. Set project Java compiler compliance to **1.8**.
4. Add Tomcat 7 under **Window → Preferences → Server → Runtime Environments**.
5. Download MySQL Connector/J 5.1.49 from the official MySQL archive and copy `mysql-connector-java-5.1.49.jar` into `WebContent/WEB-INF/lib/`.
6. Confirm `javax.servlet` is provided by Tomcat; do not add a Jakarta Servlet API JAR.
7. Update database credentials in `DBConnection.java`.
8. Run `database/petcarestore.sql` in MySQL.
9. Right-click project → **Run As → Run on Server**.

Open: `http://localhost:8080/PetCareManagementSystem/`

## 3. First use
- Register an account. New registrations receive the `OWNER` role.
- To enable staff/admin features locally, register the account first, then promote it in MySQL:
  ```sql
  USE petcarestore;
  UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';
  ```
  Log out and back in to refresh the session role.
- Demo services are inserted by the SQL script.

## 4. GitHub
From the extracted project folder:
```bash
git init
git add .
git commit -m "Build Pet Care Management System"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/PetCareManagementSystem.git
git push -u origin main
```
Before pushing, replace local database credentials with environment-specific configuration and make sure no real password is committed. This project includes a `.gitignore`.

## 5. Main workflow
JSP form → Servlet → DAO → JDBC → MySQL → DAO → Servlet → JSP.

- **Model:** simple Java classes for the core entities.
- **View:** JSP pages and CSS.
- **Controller:** Servlets receive requests, validate inputs, manage sessions, and route to DAOs.
- **DAO:** JDBC queries using `PreparedStatement`.
- **Database:** MySQL relational tables and foreign keys.

## Limitations / important notes
- Appointment conflict checking is a basic application-level check and is not a substitute for a production-grade locking strategy under concurrent requests.
- Password hashing uses PBKDF2 with a per-password random salt and 65,536 iterations.
- The project is a learning/demo application. Before public deployment, configure secrets outside source code, use HTTPS, add CSRF protection, review authorization, and deploy on supported infrastructure.
- Do not claim any feature is complete until you run the test checklist and verify it in your environment.

## Manual test checklist
- [ ] Register with valid details
- [ ] Try registering the same email twice
- [ ] Login with correct and incorrect password
- [ ] Open dashboard without logging in (should redirect to login)
- [ ] Logout and try opening dashboard again
- [ ] Add, view, edit, and delete a pet
- [ ] Verify one owner cannot open another owner's pet by changing an ID in the URL
- [ ] View available services
- [ ] Promote a test account to ADMIN and test service management
- [ ] Book an appointment for a pet
- [ ] Try booking a past date
- [ ] Try booking the same service/date/time twice
- [ ] Cancel an upcoming appointment
- [ ] Promote test account to STAFF or ADMIN and create a medical record
- [ ] View medical records for an owned pet
- [ ] Add vaccination and verify next due date
- [ ] Stop MySQL and confirm the app shows a generic error rather than a stack trace
- [ ] Test on desktop and mobile-sized browser widths

## Troubleshooting
- **ClassNotFoundException for JDBC driver:** check `mysql-connector-java-5.1.49.jar` exists in `WebContent/WEB-INF/lib/`.
- **No suitable driver:** verify the JAR is on the web app classpath and JDBC URL begins with `jdbc:mysql:`.
- **Communications link failure:** start MySQL and verify port 3306 and database name.
- **Access denied:** check username/password and MySQL grants.
- **HTTP 404:** check context path and servlet mappings in `web.xml`.
- **HTTP 500:** inspect Eclipse Console / Tomcat logs; do not expose stack traces to end users.
- **JSP compilation errors:** confirm Java 8 compiler settings and Tomcat 7 runtime.
- **Unsupported major.minor version:** compile the project with Java 8, not a newer JDK.
