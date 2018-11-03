package main.java.fr.d2factory.libraryapp.member;

import java.util.List;

import main.java.fr.d2factory.libraryapp.book.Book;

public class Student extends Member {

	public static final int NUMBER_OF_DAYS_OF_A_VALID_LOAN = 30;
	public static final int NUMBER_OF_DAYS_OF_A_FREE_LOAN = 15;
	public static final float NORMAL_TARIFF = 0.10f;
	public static final float LATE_TARIFF = 0.15f;

	private int year;

	public Student(float wallet, List<Book> books, int year) {
		super(wallet, books);
		this.year = year;
	}

	@Override
	public void payBook(int numberOfDays) {
		float amountToPay = 0f;
		/*
		 * case of student whose in his first year
		 */
		if (this.year == 1) {
			if (numberOfDays <= NUMBER_OF_DAYS_OF_A_FREE_LOAN) {
				amountToPay = 0f;
			} else if (numberOfDays <= NUMBER_OF_DAYS_OF_A_VALID_LOAN) {
				amountToPay = (numberOfDays - NUMBER_OF_DAYS_OF_A_FREE_LOAN) * NORMAL_TARIFF;
			} else {
				amountToPay = (NUMBER_OF_DAYS_OF_A_VALID_LOAN - NUMBER_OF_DAYS_OF_A_FREE_LOAN) * NORMAL_TARIFF
						+ (numberOfDays - NUMBER_OF_DAYS_OF_A_VALID_LOAN) * LATE_TARIFF;
			}
		} else {
			if (numberOfDays <= NUMBER_OF_DAYS_OF_A_VALID_LOAN) {
				amountToPay = numberOfDays * NORMAL_TARIFF;
			} else {
				amountToPay = NUMBER_OF_DAYS_OF_A_VALID_LOAN * NORMAL_TARIFF
						+ (numberOfDays - NUMBER_OF_DAYS_OF_A_VALID_LOAN) * LATE_TARIFF;
			}

		}
		this.setWallet(this.getWallet() - amountToPay);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
