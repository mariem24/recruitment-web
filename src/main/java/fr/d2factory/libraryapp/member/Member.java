package main.java.fr.d2factory.libraryapp.member;

import java.util.List;
import main.java.fr.d2factory.libraryapp.book.Book;

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
    
    public Member(float wallet, List<Book> books) {
		this.setWallet(wallet);
		this.setBorrowedbooks(books);

	}
    
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
