package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
        // foreach 
    }

    public Book findBook(long isbnCode) {
      for(Map.Entry<ISBN, Book> entry : availableBooks.entrySet()) {
        long key = entry.getKey().getIsbnCode(); 
        if (key==isbnCode){
            return entry.getValue();
        }
       }
        return null;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        Map.Entry<Book, LocalDate> new_entry = newEntry(book, borrowedAt);
        borrowedBooks.add(borrowedBooks);
    }

    public LocalDate findBorrowedBookDate(Book book) {
        for(Map.Entry<Book, LocalDate> entry : borrowedBooks.entrySet()) {
        Book key = entry.getKey(); 
        if (key.equals(book)){
            return entry.getValue();
        }
       }
        return null;
    }
}
