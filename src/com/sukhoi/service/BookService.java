package com.sukhoi.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sukhoi.bean.Book;
import com.sukhoi.exceptions.BookNotFoundException;
import com.sukhoi.exceptions.IssuedBookDeletionException;

public interface BookService {
	
	boolean addBook(Book book) throws ClassNotFoundException, SQLException;
	boolean removeBook(int bookId) throws BookNotFoundException, IssuedBookDeletionException;
	void searchBookById(int bookId) throws BookNotFoundException;
	void fineOnABook(Book book);
	void searchBookByName(String BookName);
	ArrayList<Book> viewAllBooks();

}
