# 🌱 GreenLog – Tree Plantation Drive Tracker

GreenLog is a web-based **Tree Plantation Drive Tracker** developed using **Spring Boot, Java, MySQL, JPA/Hibernate, REST APIs, HTML, CSS, and JavaScript**.

The application helps organizations manage plantation drives, volunteers, planted trees, and periodic survival check-ins. It automatically calculates survival rates and provides useful reports such as due check-ins and volunteer leaderboards.

---

# 🌿 Project Overview

Tree plantation activities are often conducted through different drives involving multiple volunteers and locations. Simply recording the number of trees planted is not enough to measure the long-term impact of plantation activities.

GreenLog provides a centralized system to:

- Create and manage plantation drives
- Register volunteers
- Record planted trees
- Track tree survival status
- Perform periodic survival check-ins
- Identify trees whose check-ins are due
- Calculate survival rates
- Generate survival reports
- Track volunteer contributions through a leaderboard

The system uses a layered Spring Boot architecture where the frontend communicates with backend REST APIs, and the backend communicates with MySQL using Spring Data JPA and Hibernate.

---

# ❗ Problem Statement

Traditional plantation drive tracking may rely on spreadsheets or manual records. This can make it difficult to:

- Track individual trees
- Monitor tree survival over time
- Identify trees requiring follow-up
- Calculate survival rates
- Track volunteer contributions
- Maintain consistent and centralized records

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

- Create plantation drives
- View plantation drives
- View individual drive details
- Update drive information
- Delete plantation drives
- Store drive location and date
- Store drive description
- Track survival rate

---

## 👥 2. Volunteer Management

The application allows users to:

- Register volunteers
- Store volunteer name
- Store email
- Store phone number
- Store joining date
- View volunteer details
- Update volunteer information
- Delete volunteers
- Prevent duplicate volunteer email registration

---

## 🌱 3. Tree Management

The system records:

- Tree species
- Tree location
- Planted date
- Plantation drive
- Volunteer who planted the tree
- Current survival status
- Next check-in date

Supported tree statuses:

```text
ALIVE
DEAD
