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
        for (Book book :books){
        	availableBooks.put(book.isbn, book);
        }
    }

    public Book findBook(long isbnCode) {
    	//Search the book in the available ones 
      for(Map.Entry<ISBN, Book> entry : availableBooks.entrySet()) {
        long key = entry.getKey().isbnCode; 
        if (key==isbnCode){
            return entry.getValue();
        }
       }   
        return null;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
    	// put the book in the borrowed books 
        borrowedBooks.put(book,borrowedAt);
        // Remove book from the available books
        availableBooks.remove(book.isbn);
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