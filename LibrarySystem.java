import java.util.Scanner;

public class LibrarySystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Book[] books = new Book[100];
    private static Member[] members = new Member[50];
    private static Librarian librarian = new Librarian("Admin", "Admin123");

    // ========================= BOOK CLASS =========================
    static class Book {
        private String title;
        private String author;
        private String isbn;
        private int stock;

        public Book(String title, String author, String isbn, int stock) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.stock = stock;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getISBN() {
            return isbn;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }

    // ========================= MEMBER CLASS =========================
    static class Member {
        private String username;
        private String password;
        private Book[] borrowedBooks = new Book[10];

        public Member(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public boolean login(String username, String password) {
            return this.username.equals(username) && this.password.equals(password);
        }

        public void borrowBook(Book book) {
            // Bug Fix: Check if stock is available before borrowing
            if (book.getStock() <= 0) {
                System.out.println("Sorry, this book is currently out of stock.");
                return;
            }

            // Bug Fix: Prevent borrowing the same book twice
            for (int i = 0; i < borrowedBooks.length; i++) {
                if (borrowedBooks[i] != null && borrowedBooks[i].getISBN().equals(book.getISBN())) {
                    System.out.println("You have already borrowed this book.");
                    return;
                }
            }

            for (int i = 0; i < borrowedBooks.length; i++) {
                if (borrowedBooks[i] == null) {
                    borrowedBooks[i] = book;
                    book.setStock(book.getStock() - 1);
                    System.out.println("Book borrowed successfully!");
                    return;
                }
            }
            System.out.println("You have reached the maximum limit of borrowed books (10).");
        }

        public void returnBook(String isbn) {
            for (int i = 0; i < borrowedBooks.length; i++) {
                if (borrowedBooks[i] != null && borrowedBooks[i].getISBN().equals(isbn)) {
                    borrowedBooks[i].setStock(borrowedBooks[i].getStock() + 1);
                    System.out.println("Book \"" + borrowedBooks[i].getTitle() + "\" returned successfully!");
                    borrowedBooks[i] = null;
                    return;
                }
            }
            System.out.println("You have not borrowed a book with ISBN: " + isbn);
        }

        public void listBorrowedBooks() {
            boolean hasBorrowed = false;
            System.out.println("\nYour Borrowed Books:");
            System.out.printf("%-5s %-30s %-20s %-15s%n", "No.", "Title", "Author", "ISBN");
            System.out.println("----------------------------------------------------------------------");
            int count = 1;
            for (Book book : borrowedBooks) {
                if (book != null) {
                    hasBorrowed = true;
                    System.out.printf("%-5d %-30s %-20s %-15s%n", count++, book.getTitle(), book.getAuthor(), book.getISBN());
                }
            }
            if (!hasBorrowed) {
                System.out.println("  You have no borrowed books.");
            }
        }

        public int getBorrowedCount() {
            int count = 0;
            for (Book book : borrowedBooks) {
                if (book != null) count++;
            }
            return count;
        }

        public boolean hasBorrowed(String isbn) {
            for (Book book : borrowedBooks) {
                if (book != null && book.getISBN().equals(isbn)) {
                    return true;
                }
            }
            return false;
        }
    }

    // ========================= LIBRARIAN CLASS =========================
    static class Librarian {
        private String username;
        private String password;

        public Librarian(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public boolean login(String username, String password) {
            return this.username.equals(username) && this.password.equals(password);
        }

        public void addBook(Book[] books, Book newBook) {
            // Bug Fix: Check for duplicate ISBN before adding
            for (int i = 0; i < books.length; i++) {
                if (books[i] != null && books[i].getISBN().equals(newBook.getISBN())) {
                    System.out.println("A book with ISBN \"" + newBook.getISBN() + "\" already exists.");
                    System.out.print("Do you want to update the stock instead? (y/n): ");
                    String answer = scanner.nextLine().trim().toLowerCase();
                    if (answer.equals("y") || answer.equals("yes")) {
                        books[i].setStock(books[i].getStock() + newBook.getStock());
                        System.out.println("Stock updated! New stock: " + books[i].getStock());
                    } else {
                        System.out.println("Book not added.");
                    }
                    return;
                }
            }

            for (int i = 0; i < books.length; i++) {
                if (books[i] == null) {
                    books[i] = newBook;
                    System.out.println("Book added successfully!");
                    return;
                }
            }
            System.out.println("Library is full. Cannot add more books.");
        }

        public void removeBook(Book[] books, String isbn) {
            // Bug Fix: Check if any member has this book borrowed
            for (Member member : members) {
                if (member != null && member.hasBorrowed(isbn)) {
                    System.out.println("Cannot remove book. It is currently borrowed by member: " + member.getUsername());
                    return;
                }
            }

            for (int i = 0; i < books.length; i++) {
                if (books[i] != null && books[i].getISBN().equals(isbn)) {
                    System.out.println("Book \"" + books[i].getTitle() + "\" removed successfully!");
                    books[i] = null;
                    return;
                }
            }
            System.out.println("Book with ISBN \"" + isbn + "\" not found.");
        }
    }

    // ========================= MAIN METHOD =========================
    public static void main(String[] args) {
        initializeBooks();
        initializeMembers();
        mainMenu();
    }

    // ========================= INITIALIZATION =========================
    private static void initializeBooks() {
        books[0] = new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 3);
        books[1] = new Book("1984", "George Orwell", "978-0451524935", 5);
        books[2] = new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084", 2);
        books[3] = new Book("The Catcher in the Rye", "J.D. Salinger", "978-0316769488", 4);
        books[4] = new Book("Pride and Prejudice", "Jane Austen", "978-0141439518", 3);
    }

    private static void initializeMembers() {
        members[0] = new Member("Jay", "Jay0101");
        members[1] = new Member("NKP", "Nkp2501");
    }

    // ========================= MAIN MENU =========================
    private static void mainMenu() {
        System.out.println("+==================================================+");
        System.out.println("|       LIBRARY MANAGEMENT SYSTEM                  |");
        System.out.println("|       Welcome! Please login to continue.         |");
        System.out.println("+==================================================+");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Librarian Login");
            System.out.println("2. Member Login");
            System.out.println("3. Register New Member");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    librarianLogin();
                    break;
                case "2":
                    memberLogin();
                    break;
                case "3":
                    registerMember();
                    break;
                case "4":
                    System.out.println("\nThank you for using the Library Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid selection. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    // ========================= LIBRARIAN SECTION =========================
    private static void librarianLogin() {
        System.out.print("\nEnter Librarian Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        if (librarian.login(username, password)) {
            System.out.println("Login successful! Welcome, Admin.");
            librarianMenu();
        } else {
            System.out.println("Incorrect credentials. Please try again.");
        }
    }

    private static void librarianMenu() {
        while (true) {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. View All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Update Book Details");
            System.out.println("5. Search Book");
            System.out.println("6. View All Members");
            System.out.println("7. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewBooks();
                    break;
                case "2":
                    addNewBook();
                    break;
                case "3":
                    removeExistingBook();
                    break;
                case "4":
                    updateBookDetails();
                    break;
                case "5":
                    searchBook();
                    break;
                case "6":
                    viewAllMembers();
                    break;
                case "7":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // ========================= MEMBER SECTION =========================
    private static void memberLogin() {
        System.out.print("\nEnter Member Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Member member = findMemberByUsername(username);

        if (member != null && member.login(username, password)) {
            System.out.println("Login successful! Welcome, " + username + ".");
            memberMenu(member);
        } else {
            System.out.println("Incorrect credentials. Please try again.");
        }
    }

    private static void memberMenu(Member member) {
        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. My Borrowed Books");
            System.out.println("5. Search Book");
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewBooks();
                    break;
                case "2":
                    borrowBook(member);
                    break;
                case "3":
                    returnBook(member);
                    break;
                case "4":
                    member.listBorrowedBooks();
                    break;
                case "5":
                    searchBook();
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // ========================= MEMBER REGISTRATION =========================
    private static void registerMember() {
        System.out.print("\nEnter a username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        // Check if username already exists
        if (findMemberByUsername(username) != null) {
            System.out.println("Username \"" + username + "\" is already taken. Please choose another.");
            return;
        }

        System.out.print("Enter a password (min 4 characters): ");
        String password = scanner.nextLine().trim();

        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters long.");
            return;
        }

        // Find empty slot in members array
        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                members[i] = new Member(username, password);
                System.out.println("Registration successful! You can now login as \"" + username + "\".");
                return;
            }
        }
        System.out.println("Member limit reached. Cannot register new members.");
    }

    // ========================= BOOK OPERATIONS =========================
    private static void viewBooks() {
        boolean hasBooks = false;
        String sep = "+-------+----------------------------+----------------------+----------------+-------+";
        System.out.println("\n" + sep);
        System.out.println("|                                BOOK CATALOGUE                                      |");
        System.out.println(sep);
        System.out.printf("| %-5s | %-26s | %-20s | %-14s | %-5s |%n", "No.", "Title", "Author", "ISBN", "Stock");
        System.out.println(sep);
        int count = 1;
        for (Book book : books) {
            if (book != null) {
                hasBooks = true;
                String stockDisplay = book.getStock() > 0 ? String.valueOf(book.getStock()) : "N/A";
                System.out.printf("| %-5d | %-26s | %-20s | %-14s | %-5s |%n",
                        count++, book.getTitle(), book.getAuthor(), book.getISBN(), stockDisplay);
            }
        }
        if (!hasBooks) {
            System.out.println("|                          No books in the library.                                |");
        }
        System.out.println(sep);
        System.out.println("Total books: " + (count - 1));
    }

    private static void addNewBook() {
        System.out.print("\nEnter Book Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Author name cannot be empty.");
            return;
        }

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("ISBN cannot be empty.");
            return;
        }

        System.out.print("Enter Stock Quantity: ");
        int stock;
        try {
            stock = Integer.parseInt(scanner.nextLine().trim());
            if (stock < 0) {
                System.out.println("Stock quantity cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for stock.");
            return;
        }

        Book newBook = new Book(title, author, isbn, stock);
        librarian.addBook(books, newBook);
    }

    private static void removeExistingBook() {
        System.out.print("\nEnter ISBN of the book to remove: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("ISBN cannot be empty.");
            return;
        }
        librarian.removeBook(books, isbn);
    }

    private static void updateBookDetails() {
        System.out.print("\nEnter ISBN of the book to update: ");
        String isbn = scanner.nextLine().trim();
        Book book = findBookByISBN(isbn);

        if (book == null) {
            System.out.println("Book with ISBN \"" + isbn + "\" not found.");
            return;
        }

        System.out.println("Current details -> Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Stock: " + book.getStock());
        System.out.println("Leave blank to keep current value.");

        System.out.print("New Title [" + book.getTitle() + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            book.setTitle(newTitle);
        }

        System.out.print("New Author [" + book.getAuthor() + "]: ");
        String newAuthor = scanner.nextLine().trim();
        if (!newAuthor.isEmpty()) {
            book.setAuthor(newAuthor);
        }

        System.out.print("New Stock [" + book.getStock() + "]: ");
        String stockInput = scanner.nextLine().trim();
        if (!stockInput.isEmpty()) {
            try {
                int newStock = Integer.parseInt(stockInput);
                if (newStock < 0) {
                    System.out.println("Stock cannot be negative. Stock not updated.");
                } else {
                    book.setStock(newStock);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Stock not updated.");
            }
        }

        System.out.println("Book details updated successfully!");
    }

    // ========================= SEARCH FUNCTIONALITY =========================
    private static void searchBook() {
        System.out.print("\nEnter search keyword (title, author, or ISBN): ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        boolean found = false;
        System.out.println("\nSearch Results:");
        System.out.printf("%-5s %-28s %-22s %-15s %-5s%n", "No.", "Title", "Author", "ISBN", "Stock");
        System.out.println("-------------------------------------------------------------------------------");
        int count = 1;
        for (Book book : books) {
            if (book != null) {
                if (book.getTitle().toLowerCase().contains(keyword)
                        || book.getAuthor().toLowerCase().contains(keyword)
                        || book.getISBN().toLowerCase().contains(keyword)) {
                    found = true;
                    System.out.printf("%-5d %-28s %-22s %-15s %-5d%n",
                            count++, book.getTitle(), book.getAuthor(), book.getISBN(), book.getStock());
                }
            }
        }
        if (!found) {
            System.out.println("  No books found matching \"" + keyword + "\".");
        }
    }

    // ========================= BORROW & RETURN =========================
    private static void borrowBook(Member member) {
        System.out.print("\nEnter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine().trim();
        Book book = findBookByISBN(isbn);

        if (book != null) {
            member.borrowBook(book);
        } else {
            System.out.println("Book with ISBN \"" + isbn + "\" not found.");
        }
    }

    private static void returnBook(Member member) {
        if (member.getBorrowedCount() == 0) {
            System.out.println("You have no borrowed books to return.");
            return;
        }

        member.listBorrowedBooks();
        System.out.print("\nEnter ISBN of the book to return: ");
        String isbn = scanner.nextLine().trim();
        member.returnBook(isbn);
    }

    // ========================= VIEW MEMBERS =========================
    private static void viewAllMembers() {
        boolean hasMembers = false;
        System.out.println("\nRegistered Members:");
        System.out.printf("%-5s %-20s %-15s%n", "No.", "Username", "Books Borrowed");
        System.out.println("------------------------------------------");
        int count = 1;
        for (Member member : members) {
            if (member != null) {
                hasMembers = true;
                System.out.printf("%-5d %-20s %-15d%n", count++, member.getUsername(), member.getBorrowedCount());
            }
        }
        if (!hasMembers) {
            System.out.println("  No members registered.");
        }
        System.out.println("Total members: " + (count - 1));
    }

    // ========================= UTILITY METHODS =========================
    private static Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book != null && book.getISBN().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    private static Member findMemberByUsername(String username) {
        for (Member member : members) {
            if (member != null && member.username.equals(username)) {
                return member;
            }
        }
        return null;
    }
}
