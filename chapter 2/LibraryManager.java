import java.util.*;

class LibraryManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int itemId = sc.nextInt();
        int titleCode = sc.nextInt();
        int availableInt = sc.nextInt();

        boolean available = (availableInt == 1);

        Book book = new Book(itemId, titleCode, available);

        while (sc.hasNextInt()) {
            int operatn = sc.nextInt();
            switch (operatn) {
                case 1:
                    book.checkOut();
                    break;
                case 2:
                    book.returnBook();
                    break;
                case 3:
                    book.display();
                    break;
                default:
                    System.out.println("Invalid operation.");
            }
            sc.close();
        }
    }
}

class LibraryItem {
    int itemId;
    int titleCode;
    boolean available;

    LibraryItem(int itemId, int titleCode, boolean available) {
        this.itemId = itemId;
        this.titleCode = titleCode;
        this.available = available;
    }
}

class Book extends LibraryItem {
    Book(int itemId, int titleCode, boolean available) {
        super(itemId, titleCode, available);
    }

    void checkOut() {
        if (this.available) {
            this.available = false;
            System.out.println("Book Checked Out Successfully.");
        } else {
            System.out.println("Already Checked Out.");
        }
    }

    void returnBook() {
        if (!this.available) {
            this.available = true;
            System.out.println("Book Returned Successfully.");
        } else {
            System.out.println("Already Available.");
        }
    }

    void display() {
        System.out.println("Book ID: " + this.itemId);
        System.out.println("Title Code: " + this.titleCode);
        System.out.println("Available: " + this.available);
    }
}
