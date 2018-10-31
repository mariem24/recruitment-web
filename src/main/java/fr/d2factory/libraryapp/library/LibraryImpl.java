package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

public class LibraryImpl implements Library {

	private BookRepository bookRepository;

	/**
	 * A member is borrowing a book from our library.
	 *
	 * @param isbnCode
	 *            the isbn code of the book
	 * @param member
	 *            the member who is borrowing the book
	 * @param borrowedAt
	 *            the date when the book was borrowed
	 *
	 * @return the book the member wishes to obtain if found
	 * @throws HasLateBooksException
	 *             in case the member has books that are late
	 *
	 * @see fr.d2factory.libraryapp.book.ISBN
	 * @see Member
	 */

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		List<Book> books = member.getBorrowedbooks();
		Book book = bookRepository.findBook(isbnCode);
		// First test member isn't a late one
		if (books == null || books.isEmpty()) {
			if (book != null) {
				bookRepository.saveBookBorrow(book, borrowedAt);
				if (books == null) {
					books = new ArrayList<>();
				}
				books.add(book);
				member.setBorrowedbooks(books);
			}
		} else {
			// Create a MAP composed by borrowed books by this member and date
			// of borrowing
			List<Long> dateOfLoan = new ArrayList<>();
			LocalDate localDate = LocalDate.now();
			books.forEach(book -> dateOfLoan
					.add(new Long(getDifferncebetweenTwoDate(bookRepository.findBorrowedBookDate(book), now))));
			boolean exceddeddays = false;
			if (member instanceof Student) {
				Long validPeriod = new Long(Member.NUMBER_OF_DAYS_OF_A_VALID_LOAN);
				exceddeddays = dateOfLoan.stream().anyMatch(e -> (e > validPeriod));

			} else {
				Long validPeriod = new Long(Resident.NUMBER_OF_DAYS_OF_A_VALID_LOAN);
				exceddeddays = dateOfLoan.stream().anyMatch(e -> (e > validPeriod));

			}
			if (exceddeddays)
				throw HasLateBooksException("Excedded valid period, you can't borrow another book");
			else {
				books.add(book);
				member.setBorrowedbooks(books);
			}
		}
		return book;
	}

	@Override
	public void returnBook(Book book, Member member) {
		// First step : Find book in borrowed books to calculate number of days
		// of loan
		LocalDate dateOfLoan = bookRepository.findBorrowedBookDate(book);
		LocalDate now =LocalDate.now();
		int numberOfDays=Math.toIntExact(getDifferncebetweenTwoDate(dateOfLoan,now));
		// Second Step : PAY Book
		member.payBook(numberOfDays);
		// Third Step : Remove book from borrowed Books andFourth and last step : ADD book To Available books
		bookRepository.saveReturnedBookBorrow(book);
		List<java.awt.print.Book> books = member.getBorrowedbooks();
		books.remove(book);
		member.setBorrowedbooks(books);
	}

	@Override
	public long getDifferncebetweenTwoDate(LocalDate dateBorrow, LocalDate now) {
		return ChronoUnit.DAYS.between(dateBorrow, now);
	}

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

}
