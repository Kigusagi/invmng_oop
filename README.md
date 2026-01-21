# 🧾 Console-Based Inventory Management System (OOP)

A console-based inventory management system developed using Java or Python that manages Computer Parts and Car Parts using strict object-oriented programming principles.

This project focuses on applying:
- Inheritance  
- Polymorphism  
- Encapsulation  
- Abstraction  
- File I/O  
- Modular development  

The system is divided into multiple modules and follows strict naming rules to ensure smooth integration between team members.

---

## 🎯 Objective

Build a modular inventory system that:
- Manages computer and car parts  
- Supports multiple user roles  
- Persists data using text files  
- Demonstrates core OOP concepts clearly  

---

## 📦 Data Models

### Item (Parent Class)
Attributes:
- id (String, format: CP-001)  
- name (String)  
- quantity (int)  
- price (double)  

---

### ComputerPart (Child of Item)
Additional attributes:
- brand (String)  
- specs (String)  

CSV format:
id,name,quantity,price,brand,specs  

---

### CarPart (Child of Item)
Additional attribute:
- vehicleType / modelCompatibility (String)  

CSV format:
id,name,quantity,price,modelCompatibility  

---

### User
Attributes:
- username (String)  
- password (String)  
- role (String: ADMIN or STAFF)  

---

## 👤 User Roles

### Admin
- Add Item  
- Remove Item  
- Register User  
- View Stock  
- Search Item  
- Update Quantity  

### Staff
- View Stock  
- Search Item  
- Update Quantity  

---

## 🔐 Authentication

- Users must log in before accessing the system  
- Role determines available menu options  
- Default admin account for testing  

Example:
Username: admin  
Password: admin123  
Role: ADMIN  

---

## 📂 File Handling

The system uses text files for data persistence.

### inventory.txt  
Stores all items in CSV format.  
Each line represents one item.

Example:
CP-001,RAM 16GB,10,299.99,ASUS,DDR4  
CAR-001,Brake Pad,5,120.50,Toyota Corolla  

---

### users.txt  
Stores all registered users in CSV format.

Example:
admin,admin123,ADMIN  
staff1,staff123,STAFF  

---

## ▶️ Application Flow

1. Program starts  
2. Load users and inventory from text files  
3. Prompt user to log in  
4. Display menu based on user role  
5. Perform selected operations  
6. Save all data before exiting  

---

## 🧠 OOP Concepts Applied

Encapsulation  
- All attributes are private with getters and setters  

Inheritance  
- ComputerPart and CarPart extend Item  

Polymorphism  
- Inventory stored as a list of Item objects  

Abstraction  
- Item is implemented as an abstract class  

---

## 🚀 How to Run

1. Open the project in your IDE  
2. Make sure inventory.txt and users.txt exist  
3. Run Main.java  
4. Log in using the provided credentials  
5. Use the menu to manage inventory  

---

## 📌 Notes

- No database required  
- Uses simple text files for storage  
- Designed for educational and team-based development  
- Strict naming rules must be followed for module compatibility  

---

## 📜 License

This project is developed for academic purposes only.