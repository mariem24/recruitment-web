package main.java.fr.d2factory.libraryapp.member;

import java.util.List;
import main.java.fr.d2factory.libraryapp.book.Book;

public class Resident extends Member {

	public static final int NUMBER_OF_DAYS_OF_A_VALID_LOAN = 60;
	public static final float NORMAL_TARIFF = 0.10f;
	public static final float LATE_TARIFF = 0.20f;

	public Resident(float wallet, List<Book> books) {
		super(wallet,books);
	}

	/**
	 * The member should pay their books when they are returned to the library
	 *
	 * @param numberOfDays
	 *            the number of days they kept the book
	 */
	@Override
	public void payBook(int numberOfDays) {
		float amountToPay = 0f;
		if (numberOfDays <= NUMBER_OF_DAYS_OF_A_VALID_LOAN) {
			amountToPay = numberOfDays * NORMAL_TARIFF;
		} else {
			amountToPay = NUMBER_OF_DAYS_OF_A_VALID_LOAN * NORMAL_TARIFF
					+ (numberOfDays - NUMBER_OF_DAYS_OF_A_VALID_LOAN) * LATE_TARIFF;
		}
		this.setWallet(this.getWallet() - amountToPay);
	}

}
