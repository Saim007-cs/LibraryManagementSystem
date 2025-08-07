import java.util.*;

class Book {
    int id;
    String title;
    String author;
    boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }
}

class User {
    int id;
    String name;
    List<Book> issuedBooks = new ArrayList<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Library {
    List<Book> books = new ArrayList<>();
    List<User> users = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.title);
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.name);
    }

    public void issueBook(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null) {
            System.out.println("Book not found.");
        } else if (user == null) {
            System.out.println("User not found.");
        } else if (book.isIssued) {
            System.out.println("Book is already issued.");
        } else {
            book.isIssued = true;
            user.issuedBooks.add(book);
            System.out.println("Book issued: " + book.title + " to " + user.name);
        }
    }

    public void returnBook(int bookId, int userId) {
        User user = findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Book bookToReturn = null;
        for (Book book : user.issuedBooks) {
            if (book.id == bookId) {
                bookToReturn = book;
                break;
            }
        }

        if (bookToReturn != null) {
            bookToReturn.isIssued = false;
            user.issuedBooks.remove(bookToReturn);
            System.out.println("Book returned: " + bookToReturn.title + " by " + user.name);
        } else {
            System.out.println("User did not issue this book.");
        }
    }

    public void showAvailableBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            if (!book.isIssued) {
                System.out.println(book.id + ". " + book.title + " by " + book.author);
            }
        }
    }

    public void showIssuedBooks() {
        System.out.println("\nIssued Books:");
        for (Book book : books) {
            if (book.isIssued) {
                System.out.println(book.id + ". " + book.title + " by " + book.author);
            }
        }
    }

    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.id == id) return b;
        }
        return null;
    }

    private User findUserById(int id) {
        for (User u : users) {
            if (u.id == id) return u;
        }
        return null;
    }
}


public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Adding some initial data
        library.addBook(new Book(1, "The Alchemist", "Paulo Coelho"));
        library.addBook(new Book(2, "1984", "George Orwell"));
        library.addUser(new User(1, "Alice"));
        library.addUser(new User(2, "Bob"));

        while (true) {
            System.out.println("\n==== Library Menu ====");
            System.out.println("1. Show Available Books");
            System.out.println("2. Show Issued Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.showAvailableBooks();
                    break;
                case 2:
                    library.showIssuedBooks();
                    break;
                case 3:
                    System.out.print("Enter Book ID to issue: ");
                    int bookId = scanner.nextInt();
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    library.issueBook(bookId, userId);
                    break;
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    System.out.print("Enter User ID: ");
                    int returnUserId = scanner.nextInt();
                    library.returnBook(returnBookId, returnUserId);
                    break;
                case 5:
                    System.out.println("Exiting Library System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
