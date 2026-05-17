# 📚 Library Management System

A feature-rich **console-based Library Management System** built with **Java**. This application provides role-based access for **Librarians** and **Members** to manage books, borrow/return operations, and user registrations — all through an interactive command-line interface.

---

## ✨ Features

### 🔐 Authentication & Authorization
- **Role-based login** — Separate flows for Librarian and Member
- **Member self-registration** with username uniqueness validation and password strength check
- **Pre-configured accounts** for quick testing

### 📖 Librarian Capabilities
| Feature | Description |
|---------|-------------|
| **View All Books** | Display the entire book catalogue in a formatted table |
| **Add Book** | Add new books with duplicate ISBN detection & stock merge option |
| **Remove Book** | Safely remove books with borrowed-book conflict prevention |
| **Update Book** | Modify title, author, or stock of existing books |
| **Search Book** | Search by title, author, or ISBN (case-insensitive) |
| **View Members** | List all registered members and their borrow counts |

### 👤 Member Capabilities
| Feature | Description |
|---------|-------------|
| **View Available Books** | Browse the full catalogue with stock availability |
| **Borrow Book** | Borrow books with stock validation and duplicate borrow prevention |
| **Return Book** | Return borrowed books with automatic stock restoration |
| **My Borrowed Books** | View personal borrowed book list |
| **Search Book** | Find books by keyword search |

### 🛡️ Robustness & Validation
- Input validation on all fields (empty checks, numeric parsing)
- Stock-level checks before borrowing (prevents negative stock)
- Duplicate ISBN prevention when adding books
- Borrowed-book safety check before book removal
- Graceful error handling with user-friendly messages

---

## 🚀 Getting Started

### Prerequisites
- **Java JDK 8** or higher installed
- Terminal / Command Prompt access

### Verify Java Installation
```bash
java -version
javac -version
```

### Compile & Run
```bash
# Clone the repository
git clone https://github.com/<your-username>/library-management-system.git
cd library-management-system

# Compile the source code
javac LibrarySystem.java

# Run the application
java LibrarySystem
```

---

## 🔑 Default Credentials

| Role | Username | Password |
|------|----------|----------|
| **Librarian** | `Admin` | `Admin123` |
| **Member** | `Jay` | `Jay0101` |
| **Member** | `NKP` | `Nkp2501` |

> 💡 You can also register a new member account from the main menu (Option 3).

---

## 📂 Project Structure

```
library-management-system/
├── LibrarySystem.java    # Main source file (all classes)
├── .gitignore            # Git ignore rules for Java projects
└── README.md             # Project documentation
```

### Class Architecture

```
LibrarySystem (Main Class)
├── Book (Inner Class)        → Stores title, author, ISBN, stock
├── Member (Inner Class)      → Handles login, borrow, return operations
├── Librarian (Inner Class)   → Manages book additions & removals
└── Main Methods              → Menu systems, search, registration
```

---

## 🎮 Usage Flow

```
                    ┌─────────────────────┐
                    │     MAIN MENU       │
                    ├─────────────────────┤
                    │ 1. Librarian Login  │
                    │ 2. Member Login     │
                    │ 3. Register Member  │
                    │ 4. Exit             │
                    └────────┬────────────┘
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
     ┌────────────┐  ┌────────────┐  ┌──────────────┐
     │  LIBRARIAN │  │   MEMBER   │  │ REGISTRATION │
     │    MENU    │  │    MENU    │  │    FLOW      │
     ├────────────┤  ├────────────┤  └──────────────┘
     │ View Books │  │ View Books │
     │ Add Book   │  │ Borrow     │
     │ Remove     │  │ Return     │
     │ Update     │  │ My Books   │
     │ Search     │  │ Search     │
     │ Members    │  │ Logout     │
     │ Logout     │  └────────────┘
     └────────────┘
```

---

## 🛠️ Technologies Used

- **Language:** Java (JDK 8+)
- **Paradigm:** Object-Oriented Programming (OOP)
- **Concepts:** Encapsulation, Inner Classes, Arrays, Role-Based Access Control

---

## 📸 Sample Output

```
╔══════════════════════════════════════════════════╗
║       LIBRARY MANAGEMENT SYSTEM                 ║
║       Welcome! Please login to continue.        ║
╚══════════════════════════════════════════════════╝

--- Main Menu ---
1. Librarian Login
2. Member Login
3. Register New Member
4. Exit
Choice: 1

Enter Librarian Username: Admin
Enter Password: Admin123
Login successful! Welcome, Admin.

--- Librarian Menu ---
1. View All Books
2. Add Book
3. Remove Book
4. Update Book Details
5. Search Book
6. View All Members
7. Logout
Choice: 1

╔══════════════════════════════════════════════════════════════════════════════════╗
║                              BOOK CATALOGUE                                    ║
╠══════════════════════════════════════════════════════════════════════════════════╣
║ No.   Title                        Author                 ISBN            Stock ║
╠══════════════════════════════════════════════════════════════════════════════════╣
║ 1     The Great Gatsby             F. Scott Fitzgerald    978-0743273565  3     ║
║ 2     1984                         George Orwell          978-0451524935  5     ║
║ 3     To Kill a Mockingbird        Harper Lee             978-0061120084  2     ║
║ 4     The Catcher in the Rye       J.D. Salinger          978-0316769488  4     ║
║ 5     Pride and Prejudice          Jane Austen            978-0141439518  3     ║
╚══════════════════════════════════════════════════════════════════════════════════╝
Total books: 5
```

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. **Fork** this repository
2. **Create** a feature branch (`git checkout -b feature/new-feature`)
3. **Commit** your changes (`git commit -m "Add new feature"`)
4. **Push** to the branch (`git push origin feature/new-feature`)
5. **Open** a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

**Jay Patel**

---

> ⭐ If you found this project helpful, please give it a star!
