# 🌱 GreenLog – Tree Plantation Drive Tracker

GreenLog is a web-based **Tree Plantation Drive Tracker** developed using **Spring Boot, Java, MySQL, JPA/Hibernate, REST APIs, HTML, CSS, and JavaScript**.

The application helps organizations manage plantation drives, volunteers, planted trees, and periodic survival check-ins. It automatically calculates survival rates and provides useful reports such as due check-ins and volunteer leaderboards.

---

# 🌿 Project Overview

Tree plantation activities are often conducted through different drives involving multiple volunteers and locations. Simply recording the number of trees planted is not enough to measure the long-term impact of plantation activities.

GreenLog provides a centralized system to:

* Create and manage plantation drives
* Register volunteers
* Record planted trees
* Track tree survival status
* Perform periodic survival check-ins
* Identify trees whose check-ins are due
* Calculate survival rates
* Generate survival reports
* Track volunteer contributions through a leaderboard

The system uses a layered Spring Boot architecture where the frontend communicates with backend REST APIs, and the backend communicates with MySQL using Spring Data JPA and Hibernate.

---

# ❗ Problem Statement

Traditional plantation drive tracking may rely on spreadsheets or manual records. This can make it difficult to:

* Track individual trees
* Monitor tree survival over time
* Identify trees requiring follow-up
* Calculate survival rates
* Track volunteer contributions
* Maintain consistent and centralized records

GreenLog addresses these problems by providing a structured digital tracking system.

---

# 🎯 Objectives

The main objectives of GreenLog are:

1. To maintain plantation drive information.
2. To maintain volunteer information.
3. To record every planted tree.
4. To assign trees to plantation drives and volunteers.
5. To perform periodic tree survival check-ins.
6. To prevent invalid check-ins for dead trees.
7. To automatically recalculate survival rates.
8. To identify trees with due check-ins.
9. To generate survival reports by drive and species.
10. To calculate volunteer planting contributions.

---

# 🚀 Key Features

## 🌳 1. Plantation Drive Management

The application allows users to:

* Create plantation drives
* View plantation drives
* View individual drive details
* Update drive information
* Delete plantation drives
* Store drive location and date
* Store drive description
* Track survival rate

---

## 👥 2. Volunteer Management

The application allows users to:

* Register volunteers
* Store volunteer name
* Store email
* Store phone number
* Store joining date
* View volunteer details
* Update volunteer information
* Delete volunteers
* Prevent duplicate volunteer email registration

---

## 🌱 3. Tree Management

The system records:

* Tree species
* Tree location
* Planted date
* Plantation drive
* Volunteer who planted the tree
* Current survival status
* Next check-in date

### Supported Tree Statuses

```text
ALIVE
DEAD
```

---

## 🔄 4. Survival Check-ins

Volunteers can perform periodic check-ins for planted trees.

A check-in records:

* Tree
* Volunteer
* Check-in date
* Survival status
* Remarks
* Next check-in date

The system updates the tree's current status whenever a new check-in is recorded.

If a tree is marked as `DEAD`, no further check-in can be added for that tree.

---

## 📊 5. Survival Reports

GreenLog provides survival reports based on:

* Plantation drive
* Tree species

The survival rate is automatically calculated whenever a new check-in is recorded.

### Example

```text
Total Trees = 2
Alive Trees = 1

Survival Rate = 50.00%
```

---

## ⏰ 6. Due Check-ins

The system identifies trees whose next check-in date is due.

This helps volunteers identify trees that require monitoring and follow-up.

### Example

```text
Tree Species       : Banyan
Current Status     : ALIVE
Next Check-in Date : 2026-09-28
```

The system can identify trees that are:

* Due for check-in
* Overdue for check-in
* Still active and requiring monitoring

---

## 🏆 7. Volunteer Leaderboard

GreenLog tracks the number of trees planted by each volunteer.

The leaderboard displays volunteer contributions based on the number of trees planted.

### Example

```text
Volunteer       Trees Planted
-----------------------------
Kavipriya             5
Volunteer A            3
Volunteer B            2
```

This helps organizations understand individual volunteer contributions to plantation activities.

---

# 🏗️ System Architecture

GreenLog follows a layered Spring Boot architecture.

```text
                    ┌─────────────────────┐
                    │      Frontend       │
                    │   HTML / CSS / JS   │
                    └──────────┬──────────┘
                               │
                         Fetch API
                               │
                               ▼
                    ┌─────────────────────┐
                    │  REST Controllers   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Service Layer    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Repository Layer    │
                    │  Spring Data JPA    │
                    └──────────┬──────────┘
                               │
                         Hibernate ORM
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    │      Database       │
                    └─────────────────────┘
```

### Request Flow

```text
User
  ↓
HTML / CSS / JavaScript
  ↓
Fetch API
  ↓
REST Controller
  ↓
Service Layer
  ↓
Repository
  ↓
JPA / Hibernate
  ↓
MySQL Database
```

---

# 🛠️ Technology Stack

| Technology            | Purpose                                   |
| --------------------- | ----------------------------------------- |
| **Java 17**           | Backend programming                       |
| **Spring Boot 4.1.1** | Backend framework                         |
| **Spring Data JPA**   | Database access and repository management |
| **Hibernate**         | ORM and object-relational mapping         |
| **MySQL 8**           | Relational database                       |
| **MySQL Connector/J** | JDBC database driver                      |
| **Maven**             | Build and dependency management           |
| **HTML5**             | Frontend structure                        |
| **CSS3**              | Frontend styling                          |
| **JavaScript**        | Frontend functionality                    |
| **Fetch API**         | Frontend-backend communication            |
| **REST API**          | Client-server communication               |
| **Postman**           | REST API testing                          |

---


Configure the MySQL con
