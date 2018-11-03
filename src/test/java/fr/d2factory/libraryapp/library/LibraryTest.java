package test.java.fr.d2factory.libraryapp.library;

import main.java.fr.d2factory.libraryapp.book.BookRepository;
import main.java.fr.d2factory.libraryapp.book.ISBN;

import main.java.fr.d2factory.libraryapp.book.Book;
import main.java.fr.d2factory.libraryapp.library.HasLateBooksException;
import main.java.fr.d2factory.libraryapp.library.*;
import main.java.fr.d2factory.libraryapp.member.*;

import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryTest {
	private static org.apache.log4j.Logger log = Logger.getLogger(LogClass.class);
	private Library library;
	private BookRepository bookRepository;

	@Before
	public void setup() {
		// TODO instantiate the library and the repository

		// TODO add some test books (use BookRepository#addBooks)
		// TODO to help you a file called books.json is available in
		// src/test/resources
		ISBN isbn1 = new ISBN(46578964513l);
		ISBN isbn2 = new ISBN(3326456467846l);
		ISBN isbn3 = new ISBN(968787565445l);
		ISBN isbn4 = new ISBN(465789453149l);
		List<Book> books = new ArrayList<>();
		Book book1 = new Book("Harry Potter", "J.K. Rowling", isbn1);
		books.add(book1);
		Book book2 = new Book("Around the world in 80 days", "Jules Verne", isbn2);
		books.add(book2);
		Book book3 = new Book("Catch 22", "Joseph Heller", isbn3);
		Book book4 = new Book("La peau de chagrin", "Balzac", isbn4);
		bookRepository.addBooks(books);
	}

	@Test
	public void member_can_borrow_a_book_if_book_is_available() {
		LocalDate now = LocalDate.now();
		Book book = bookRepository.findBook(465789453149l);
		Book book1 = bookRepository.findBook(3326456467846l);
		if (book != null)
			System.out.println("book found");
		else
			System.out.println("book not found");
		bookRepository.saveBookBorrow(bookRepository.findBook(465789453149l), now);
		// suppose that a member would borrow the book 3 (3 in the first method)
		LocalDate localDate = bookRepository.findBorrowedBookDate(book);
		if (localDate != null)
			System.out.println("book which isbn is 46589453149 available");
		else
			System.out.println("book which isbn is 465789453149 not available");
		// Suppose that a member would borrow book (2 in the first method)
		if (book1 != null)
			System.out.println("book found");
		else
			System.out.println("book not found");
		LocalDate localDate2 = bookRepository.findBorrowedBookDate(book1);
		if (localDate != null)
			System.out.println("book which isbn is 3326456467846 available");
		else
			System.out.println("book which isbn is 3326456467846  not available");
	}

	@Test
	public void borrowed_book_is_no_longer_available() {
		LocalDate now = LocalDate.now();
		bookRepository.saveBookBorrow(bookRepository.findBook(3326456467846l), now);
		System.out.println("This book 3326456467846l is no more available");
	}

	@Test
	public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		System.out.println("Resident are taxed for each day" + Resident.NORMAL_TARIFF);
	}

	@Test
	public void students_pay_10_cents_the_first_30days() {
		System.out.println("Resident are taxed for each day" + Resident.NORMAL_TARIFF);
	}

	@Test
	public void students_in_1st_year_are_not_taxed_for_the_first_15days() {
		Student student = new Student(500, null, 1);
		Book book = bookRepository.findBook(465789453149l);
		LocalDate now = LocalDate.now();
		LocalDate now_minus_fifteen = now.minus(15, ChronoUnit.DAYS);
		library.borrowBook(465789453149l, student, now_minus_fifteen);
		library.returnBook(book, student);
		if (student.getWallet() == 500)
			System.out.println("students first year have 15 first days of a loan for free");
		else
			System.out.println(
					"the rule that said le first 15 days of a loan for first year student is free is not respected");

	}

	@Test
	public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() {
		Student student = new Student(500, null, 2);
		Student student_1 = new Student(900, null, 2);
		Book book = bookRepository.findBook(465789453149l);
		Book book_1 = bookRepository.findBook(465789453149l);
		LocalDate now = LocalDate.now();
		LocalDate now_minus_fifteen = now.minus(30, ChronoUnit.DAYS);
		LocalDate now_minus_31 = now.minus(31, ChronoUnit.DAYS);

		library.borrowBook(465789453149l, student, now_minus_fifteen);
		library.returnBook(book, student);
		long period = library.getDifferncebetweenTwoDate(now_minus_fifteen, now);
		float cost_of_first_peridod = (500 - student.getWallet()) / period;
		library.borrowBook(3326456467846l, student_1, now_minus_31);
		library.returnBook(book_1, student_1);
		long period_two = library.getDifferncebetweenTwoDate(now_minus_31, now);
		float coastofthesecondperiod = (900 - cost_of_first_peridod * 30) / (period_two - 30);
		System.out.println("cost after 30 days for residents" + coastofthesecondperiod);

	}

	@Test
	public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		Resident resident = new Resident(500, null);
		Resident resident_1 = new Resident(900, null);
		Book book = bookRepository.findBook(465789453149l);
		Book book_1 = bookRepository.findBook(465789453149l);
		LocalDate now = LocalDate.now();
		LocalDate now_minus_thirty = now.minus(30, ChronoUnit.DAYS);
		LocalDate now_minus_61 = now.minus(61, ChronoUnit.DAYS);

		library.borrowBook(465789453149l, resident, now_minus_thirty);
		library.returnBook(book, resident);
		long period = library.getDifferncebetweenTwoDate(now_minus_thirty, now);
		float cost_of_first_peridod = (500 - resident.getWallet()) / period;
		library.borrowBook(3326456467846l, resident_1, now_minus_61);
		library.returnBook(book_1, resident_1);
		long period_two = library.getDifferncebetweenTwoDate(now_minus_61, now);
		float coastofthesecondperiod = (900 - cost_of_first_peridod * 60) / (period_two - 60);
		System.out.println("cost after 60 days for residents" + coastofthesecondperiod);
	}

	@Test
	public void members_cannot_borrow_book_if_they_have_late_books() {
		Resident resident = new Resident(500, null);
		LocalDate now = LocalDate.now();
		LocalDate now_minus_61 = now.minus(30, ChronoUnit.DAYS);
		try{
			library.borrowBook(3326456467846l, resident, now_minus_61);
			}
		catch(HasLateBooksException e){
			System.out.println("you have books not returned in the fixed date");
			log.error(e);
		
		}

}}
