package com.sukhoi.service;

import com.sukhoi.persistence.BookDao;
import com.sukhoi.persistence.BookDaoImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
//import java.util.concurrent.TimeUnit;

import com.sukhoi.bean.Book;
import com.sukhoi.exceptions.BookNotFoundException;
import com.sukhoi.exceptions.IssuedBookDeletionException;

public class BookServiceImpl implements BookService {

	BookDao bookDao = new BookDaoImpl();
	Scanner scanner = new Scanner(System.in);

	@Override
	public ArrayList<Book> viewAllBooks() {
		try {
			ArrayList<Book> books = bookDao.getAllBooks();

			return books;
		} catch (SQLException e) {

			System.out.println("SQL exception Dear!!!!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			System.out.println("can't find your class-- exception Dear!!!!");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean addBook(Book book) {

		try {
			return bookDao.insertBook(book);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean removeBook(int bId) throws BookNotFoundException, IssuedBookDeletionException {

		Book book = bookDao.searchBook(bId);
		if (book != null) {
			if (book.isAvailability()) {
				if (bookDao.deleteBook(bId) == true) {
					return true;
				}
			} else {
				throw new IssuedBookDeletionException("This book at present is issued to a geek! with student id "
						+ book.getStudentId() + " first get it back!! can't remove book before that ");
			}

		} else
			throw new BookNotFoundException("OOps the book requested has iota >_< ; not available for removal!");
		return false;

	}

	@Override
	public void searchBookById(int bookId) throws BookNotFoundException {
		Book currBook = bookDao.searchBook(bookId);
		if (currBook != null) {
			System.out.println("*************Book Details*********");
			System.out.println("Book id: " + currBook.getBookId());
			System.out.println("Book Name: " + currBook.getBookName());
			System.out.println("Book Author: " + currBook.getAuthor());
			System.out.println("Book Field: " + currBook.getBookField());
			boolean isAvailable = currBook.isAvailability();
			System.out.println("Book Availability: " + isAvailable);
			if (isAvailable == false) {
				System.out.println("Issue Date: " + currBook.getIssueDate());
				System.out.println("Return Date: " + currBook.getReturnDate());
				System.out.println("Issued To Student with id: " + currBook.getStudentId());
			}
		} else
			throw new BookNotFoundException("This book id is not present in the library");
	}

	@Override
	public void searchBookByName(String BookName) {

		System.out.println("Book id(s) for your desired book and their availability: ");
		int countAvailable = 0;
		ArrayList<Book> bookNames = bookDao.searchBookByName(BookName);
		for (Book book : bookNames) {
			System.out.print("Book Id:" + book.getBookId() + "   Availability:");
			if (book.isAvailability())
				countAvailable++;
			System.out.println(book.isAvailability());

		}
		if (countAvailable == 0) {
			System.out.println("Sorry the Book you have searched is currently unavailable");
		}
	}

	@Override
	public void fineOnABook(Book currBook) {
		System.out.println("Fine?");

		LocalDate currDate = LocalDate.now();
		long numDays = ChronoUnit.DAYS.between(currBook.getIssueDate(), currDate);

		if (numDays > StudentService.maxmDaysWithNoFine) {
			System.out.println("Yes");
			int daysExceeded = (int) (numDays - StudentService.maxmDaysWithNoFine);
			System.out.println("Days Exceeded: " + daysExceeded);
			int finePerDay = bookDao.getFinePerDay(currBook.getBookField());
			System.out.println("Fine Amount per extra day: " + finePerDay);
			System.out.println("Fine Amount= Rs" + daysExceeded * finePerDay);
		} else {
			System.out.println("No");
		}
	}

}
