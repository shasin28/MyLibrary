package com.sukhoi.persistence;

import com.sukhoi.bean.Book;
import com.sukhoi.exceptions.BookNotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface BookDao {

	ArrayList<Book> getAllBooks() throws ClassNotFoundException, SQLException;

	boolean insertBook(Book book) throws ClassNotFoundException, SQLException;

	boolean deleteBook(int bookId);

	boolean updateBookStatus(int bookId, boolean availability, int studentId, LocalDate issueDate,
			LocalDate returnDate);

	Book searchBook(int bookId);

	ArrayList<Book> searchBookByName(String BookName);

	int getFinePerDay(String bookType);

}
