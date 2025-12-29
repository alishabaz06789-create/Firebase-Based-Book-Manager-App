# 📱 Firebase-Based Mobile Application (Assignment 3)

## 📌 Project Overview
This Android application is developed as part of **Assignment 3**.  
The purpose of this project is to implement **Firebase Authentication** and **Firebase Firestore** for secure user management and book data storage.

The application allows users to register, log in, reset their password, and manage books using cloud-based services provided by :contentReference[oaicite:0]{index=0}.

---

## 🔐 Part A: User Authentication (Firebase Authentication)

The following authentication features are implemented:

- **User Registration (Sign Up)**  
  Users can create an account using email and password with proper input validation.

- **User Login**  
  Registered users can log in securely using Firebase Authentication.

- **Forgot Password**  
  Users can reset their password through a password reset email.

- **Delete Account**  
  Users can permanently delete their account from Firebase after confirmation.

All authentication errors are handled properly with meaningful messages.

---

## 📚 Part B: Book Management (Firebase Firestore)

After successful login, users can manage books with the following features:

- **Add Book**  
  Add a new book with:
  - Book Title  
  - Author Name  
  - ISBN  
  - Publication Year  

- **View Books**  
  Display all stored books using a list / RecyclerView.

- **Update Book**  
  Modify existing book details.

- **Delete Book**  
  Remove selected books from the database.

Book data is stored in **Firebase Firestore**.

---

## 🗄️ Database Structure (Firestore)

