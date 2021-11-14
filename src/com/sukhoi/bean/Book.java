package com.sukhoi.bean;

import java.time.LocalDate;

public class Book {

	// book info
	private int bookId;
	private String bookName;
	private String author;
	private String bookField;
	private boolean availability;
	private int studentId;
	private LocalDate issueDate;
	private LocalDate returnDate;

	// constructor

	public Book() {

	}

	public Book(int bookId, String bookName, String author, String bookField, boolean availability, int studentId,
			LocalDate issueDate, LocalDate returnDate) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.bookField = bookField;
		this.availability = availability;
		this.studentId = studentId;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}

	public Book(int bId, String na, String auth, String field, boolean avbl) {
		super();
		this.bookId = bId;
		this.bookName = na;
		this.author = auth;
		this.bookField = field;
		this.availability = avbl;
	}
	// getters and setters

	public Book(String nam, String auth, String fld, boolean avbl) {
		// super();
		// this.bookId = bookId;
		this.bookName = nam;
		this.author = auth;
		this.bookField = fld;
		this.availability = avbl;

	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookField() {
		return bookField;
	}

	public void setBookField(String bookField) {
		this.bookField = bookField;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", bookField=" + bookField
				+ ", availability=" + availability + ", studentId=" + studentId + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + "]";
	}

}
