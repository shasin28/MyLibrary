package com.sukhoi.presentation;
import java.sql.SQLException;

import com.sukhoi.bean.Book;
import com.sukhoi.bean.Student;
import com.sukhoi.exceptions.BookNotFoundException;
import com.sukhoi.exceptions.IssuedBookDeletionException;
import com.sukhoi.exceptions.StudentIdNotFoundException;
import com.sukhoi.service.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;


public class LibPresentationImpl implements LibPresentation {

	private BookService bookService=new BookServiceImpl();
	private StudentService studentService=new StudentServiceImpl();
	Scanner scanner=new Scanner(System.in);

	@Override
	public void showMenuAdmin() {
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println();
		System.out.println("Choose an option to execute: ");
		System.out.println("1.  View All Books");
		System.out.println("2.  View All Students");
		System.out.println("3.  Add a Book");
		System.out.println("4.  Remove a Book");
		System.out.println("5.  Add a Student");
		System.out.println("6.  Remove a Student");
		System.out.println("7.  Issue a book");
		System.out.println("8.  Return a book(update)");
		System.out.println("9.  Search a student by Id");
		System.out.println("10. Search a book by bookId");
		System.out.println("11. Search a book by book name");
		System.out.println("12. Exit");
		
		
		
	}

	
	
	
	@Override
	public void performMenuAdmin(int choice) {
		
		switch (choice) {
		case 1:
			ArrayList<Book>books=bookService.viewAllBooks();
			if(books.size()==0)
				System.out.println("Poor library!!! waitng for books ; no books at present here");
			else
			{
				for(Book book:books)
				System.out.println(book);
			}	
			break;
		case 2:
			ArrayList<Student>students=studentService.viewAllStudents();
			if(students.size()==0)
				System.out.println("Poor library!!! waitng for students to register ; no students registered at present here");
			else
			{
			for(Student student:students)
			System.out.println(student);
			}
			
			break;
		case 3:
		     
			try {
				if(bookService.addBook(addNewBook()))
		    		 System.out.println("Book Added Successfully!");
		    	 else
		    		 System.out.println("Aww Snap!!Book wasn't added");
			} catch (ClassNotFoundException e) {
			
				e.printStackTrace();
			} catch (SQLException e) {
		
				e.printStackTrace();
			}
				
			
			break;
		case 4:
			System.out.println("Enter Book Id to remove book:");
			try
			{

			int bId=scanner.nextInt();
			try
			{
			if(bookService.removeBook(bId)==true)
			System.out.println("Book Removed Successfully!");
			}
			catch (BookNotFoundException e)
			{
				System.out.println(e.getMessage());
			}
			catch(IssuedBookDeletionException e)
			{
				System.out.println(e.getMessage());
			}
			
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the book id more carefully; better luck ! Found Illegal book id format");
			}
			break;
		case 5:
			try {
				if(studentService.addStudent(addNewStudent()))
					System.out.println("Yayy the UIT library family has one more member! Student added successfully!!");
					
						
			} catch (NullPointerException e) {
				System.out.println("Aww Snap! can't add the new geek!try again");
				//e.printStackTrace();
			}
			break;
		case 6:
			System.out.println("Enter Student Id to remove a Student:");
			try
			{
			int sId=scanner.nextInt();
			
			
			try {
				if(studentService.removeStudent(sId))
					System.out.println("Student removed successfully! Goodbye hurts though :(");
			} catch (StudentIdNotFoundException e) {

				System.out.println(e.getMessage());
			}
			
			
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
			} 
			break;
		case 7:
			System.out.println("Enter Student Id of student to be issued book :");
			try
			{
			int studId=scanner.nextInt();
			try {
				if(studentService.IssueBook(studId))
				{
					System.out.println("Happy Reading! Book issued successfully!");
					
				}
				
			} catch (StudentIdNotFoundException e) {

				System.out.println(e.getMessage());
			}
			catch(BookNotFoundException e)
			{
				System.out.println(e.getMessage());
			}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
			}
			
			break;
		case 8:
			System.out.println("Enter Student Id of student requesting to return book");
			try
			{
			int studIdRet=scanner.nextInt();
			try {
				if(studentService.ReturnBook(studIdRet))
				{
					System.out.println("Either no fine / or Fine Paid -thus no dues? Yes/No?");
					String dues=scanner.nextLine();
					if(dues.equals("Yes"))
					System.out.println("Book returned successfully!");
					else
						System.out.println("Please pay your dues!!! Don't leave the library before that");
						
				}
			} catch (StudentIdNotFoundException e) {
				System.out.println(e.getMessage());
			
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
			}
				
				
			break;
		case 9:
			System.out.println("Enter Student Id :");
			try
			{
			int studdId=scanner.nextInt();
			try
			{
			studentService.studentStatusView(studdId, true);
			}
			catch (StudentIdNotFoundException e) {
				System.out.println(e.getMessage());
			}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
			}
			
			break;
		case 10:
			System.out.println("Enter book Id:");
			try
			{
			int bIdd=scanner.nextInt();
			try
			{
			bookService.searchBookById(bIdd);
			}
			catch(BookNotFoundException e)
			{
				System.out.println(e.getMessage());
			}
			
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put the book id more carefully; better luck ! Found Illegal book id format");
			}
			break;
		case 11:
			System.out.println("Enter book Name to be searched:");
			String bookNam=scanner.nextLine();
			bookService.searchBookByName(bookNam);
			break;
		case 12:
			System.out.println("******************Thanks for visiting the Library*****************************");
			System.exit(0);
		default:
			break;
		}
		
	}
	
	

	@Override
	public void showMenuStudent() {
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println();
		System.out.println("Choose an option to execute: ");
		System.out.println("1.  View your Status: can/can't get books; issued books/fine");
		System.out.println("2.  Search for a book by Name");
		System.out.println("3.  Exit");
	}

	@Override
	public void performMenuStudent(int choice) {
		switch (choice) {
		case 1:
			System.out.println("Enter Student Id :");
			try
			{
			int studId=scanner.nextInt();
			studentService.studentStatusView(studId,false);
			}
			catch(InputMismatchException e)
			{
				System.out.println("Meditate a bit and put your student id more carefully; better luck ! Found Illegal student id format");
			} catch (StudentIdNotFoundException e) {
			
				System.out.println(e.getMessage());
			}
			break;
		case 2:
			System.out.println("Enter book Name to be searched:");
			String bookNam=scanner.nextLine();
			bookService.searchBookByName(bookNam);
			break;
		case 3:
			System.out.println("Thanks for visiting the Library");
			System.exit(0);
		default:
			break;
		}
	}
	
	public Book addNewBook()
	{
		//Book book=new Book();
		System.out.println("Enter Book Name");
		String nam=scanner.nextLine();
		nam=nam.substring(0,Math.min(nam.length(), 100));
		
		System.out.println("Enter Author");
		String auth=scanner.nextLine();
		auth=auth.substring(0,Math.min(auth.length(), 30));
		System.out.println("Enter Book Field");
		String fld=scanner.nextLine();
		fld=fld.substring(0,Math.min(fld.length(), 30));
		boolean avbl=true;
		Book book=new Book(nam.toLowerCase(),auth.toLowerCase(),fld.toLowerCase(),avbl);
		return book;
	}
	public Student addNewStudent() throws InputMismatchException
	{
		
		System.out.println("Enter Student Name:");
		String studName=scanner.nextLine();
	    studName=studName.substring(0,Math.min(studName.length(),50));
		System.out.println("Enter Student's Branch:");
		String studBranch=scanner.nextLine();
		studBranch=studBranch.substring(0,Math.min(studBranch.length(),5));
		System.out.println("Enter Student's college Roll No.(Student Id No) be very careful while entering that:");
		try {
		int studId=scanner.nextInt();
		Student newStudent=new Student(studId,studName,studBranch);
		return newStudent;
		}
		catch(InputMismatchException e)
		{
			System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
		}
		return null;
	}
	
	
	
	
		
	

}
