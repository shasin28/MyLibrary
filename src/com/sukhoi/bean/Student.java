package com.sukhoi.bean;

import java.util.ArrayList;
//import java.util.Date;

public class Student {

	private int studentId;
	private String studentName;
	private String studentBranch;

	private ArrayList<Integer> issuedBooks;

	// constructors
	public Student() {
	}

	public Student(int studentId, String studentName, String studentBranch) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentBranch = studentBranch;
	}

	public Student(int studentId, String studentName, String studentBranch, ArrayList<Integer> issuedBooks) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentBranch = studentBranch;
		this.issuedBooks = issuedBooks;
	}

	// getters & setters
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentBranch() {
		return studentBranch;
	}

	public void setStudentBranch(String studentBranch) {
		this.studentBranch = studentBranch;
	}

	public ArrayList<Integer> getIssuedBooks() {
		return issuedBooks;
	}

	public void setIssuedBooks(ArrayList<Integer> issuedBooks) {
		this.issuedBooks = issuedBooks;
	}

	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentBranch=" + studentBranch
				+ ", issuedBooks=" + issuedBooks + "]";
	}

}
