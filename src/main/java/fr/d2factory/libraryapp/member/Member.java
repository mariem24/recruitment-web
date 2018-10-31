package fr.d2factory.libraryapp.member;

import java.util.List;

import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.book.Book;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    /**
     * List of books borrowed by this member at t=0
     */
    private List<Book> borrowedbooks;
    
	/**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
    
    public List<Book> getBorrowedbooks() {
		return borrowedbooks;
	}

	public void setBorrowedbooks(List<Book> borrowedbooks) {
		this.borrowedbooks = borrowedbooks;
	}
}
