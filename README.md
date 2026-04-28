# Claim Management System

A full-stack web application built with Spring Boot and PostgreSQL.

## Features
- Client can submit and track claims
- Admin can approve, reject, and manage claims
- Role-based access with Spring Security
- Admin dashboard with claim approval/rejection workflow
- Search and filter claims by status

## Tech Stack
- Backend: Spring Boot, Spring Security, JPA/Hibernate
- Database: PostgreSQL
- Frontend: HTML, CSS, JavaScript

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/claims | Submit a new claim |
| GET | /api/claims | Get all claims (Admin) |
| GET | /api/claims/{id} | Get claim by ID |
| PUT | /api/claims/{id}/status | Update claim status (Admin) |
| DELETE | /api/claims/{id} | Delete a claim (Admin) |
| GET | /api/claims/status/{status} | Filter by status |
| GET | /api/claims/search?name= | Search by name |

## How to Run
1. Clone the repository
2. Create PostgreSQL database named `claimdb`
3. Update `application.properties` with your DB credentials
4. Run `ClaimManagementApplication.java`
5. Open `http://localhost:8080/index.html`

## Login
- **Admin:** username: `admin` password: `admin123`
