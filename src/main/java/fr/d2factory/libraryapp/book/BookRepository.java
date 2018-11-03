package main.java.fr.d2factory.libraryapp.book;

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
	
	public void addBooks(List<Book> books) {
		books.forEach(book -> availableBooks.put(book.isbn, book));
	}

	public Book findBook(long isbnCode) {
		// Search the book in the available ones
		for (Map.Entry<ISBN, Book> entry : availableBooks.entrySet()) {
			long key = entry.getKey().isbnCode;
			if (key == isbnCode) {
				return entry.getValue();
			}
		}
		return null;
	}

	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		// Remove book from the available books
		availableBooks.remove(book.isbn);
		// put the book in the borrowed books
		borrowedBooks.put(book, borrowedAt);
	}

	public void saveReturnedBookBorrow(Book book) {
		// put the book in the borrowed books
		borrowedBooks.remove(book);
		// Remove book from the available books
		availableBooks.put(book.isbn,book);

	}

	public LocalDate findBorrowedBookDate(Book book) {
		for (Map.Entry<Book, LocalDate> entry : borrowedBooks.entrySet()) {
			if ((entry.getKey()).equals(book)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public Map<ISBN, Book> getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(Map<ISBN, Book> availableBooks) {
		this.availableBooks = availableBooks;
	}

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
}