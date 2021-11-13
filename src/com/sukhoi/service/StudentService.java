package com.sukhoi.service;

import java.util.ArrayList;
import com.sukhoi.bean.Student;
import com.sukhoi.exceptions.BookNotFoundException;
import com.sukhoi.exceptions.StudentIdNotFoundException;

public interface StudentService {
	
	
	int maxmNoOfIssuedBooks = 3;
	int maxmDaysWithNoFine=7;
	ArrayList<Student> viewAllStudents();
	boolean addStudent(Student student);
	boolean removeStudent(int studentId) throws StudentIdNotFoundException;
	void studentStatusView(int studentId,boolean admin) throws StudentIdNotFoundException;
	boolean IssueBook(int studentId) throws StudentIdNotFoundException, BookNotFoundException;
	boolean ReturnBook(int studentId) throws StudentIdNotFoundException, BookNotFoundException;
	
	
	

}
