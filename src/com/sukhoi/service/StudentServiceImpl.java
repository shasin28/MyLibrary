package com.sukhoi.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.sukhoi.bean.Book;
import com.sukhoi.bean.Student;
import com.sukhoi.exceptions.BookNotFoundException;
import com.sukhoi.exceptions.StudentIdNotFoundException;
import com.sukhoi.persistence.BookDao;
import com.sukhoi.persistence.BookDaoImpl;
import com.sukhoi.persistence.StudentDao;
import com.sukhoi.persistence.StudentDaoImpl;

public class StudentServiceImpl implements StudentService {
	

	StudentDao studentDao=new StudentDaoImpl();
	BookService bookService=new BookServiceImpl();
	BookDao bookDao=new BookDaoImpl();
	Scanner scanner=new Scanner(System.in);

	@Override
	public ArrayList<Student> viewAllStudents(){
		ArrayList<Student>students =new ArrayList<>();
		try {
			students=studentDao.getAllStudents();
			return students;
		}  catch (SQLException e) {
			System.out.println("SQL exception Dear!!!!");
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public boolean addStudent(Student student) {
		Student checkStudent=studentDao.searchStudent(student.getStudentId());
		if(checkStudent==null)
		return studentDao.insertStudent(student);
		else
		{
			System.out.println("Given student id is already registered !!! can't register it again");
			return false;
		}
	}

	@Override
	public boolean removeStudent(int studentId) throws StudentIdNotFoundException {

		Student student=studentDao.searchStudent(studentId);
		if(student!=null)
		{
		return studentDao.deleteStudent(studentId);
		
		}
		else
		{
			
			throw new StudentIdNotFoundException("Seems this student id is from an alien planet ; not registered in the library at present ");
		}	
	}

	
	@Override
	public void studentStatusView(int studentId,boolean admin) throws StudentIdNotFoundException {
		Student currentStudent=studentDao.searchStudent(studentId);
		String name;
		
		if(currentStudent!=null) {	
			if(admin==true) {
				name=currentStudent.getStudentName();
			} else
				name="You";
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("***********Student Details************* ");
		System.out.println("Student Id: "+currentStudent.getStudentId());
		System.out.println("Student Name: "+currentStudent.getStudentName());
		System.out.println("Student Branch: "+currentStudent.getStudentBranch());
		System.out.println();
		System.out.println("************::::::::::::***Library Rules:***::::::::::::::*******************");
		System.out.println();
		System.out.println("We love those who maintain silence here! ");
		
		System.out.println("Number of maximum book we allow to be issued at a time: "+StudentService.maxmNoOfIssuedBooks);
		System.out.println("Books have to be returned within "+StudentService.maxmDaysWithNoFine+" days ; after that we charge fine ");
		
			
		System.out.println("*****************"+name+(admin?"'s":"r")+" Library Karma********************");
		int noStudsBook=currentStudent.getIssuedBooks().size();
		System.out.println("Number of current issued Books"+" to "+name+" : "+noStudsBook);
		System.out.println("Can get more books?");
		if(noStudsBook>2)
		{
			System.out.println("Oops we can't issue more books!!! to "+name);
		}
		else
		{
			System.out.println(name+" can get "+(3-noStudsBook)+" more books");
		}
			
		System.out.println("See issued book details?(Yes/No)");
		String seeBook=scanner.nextLine();
		if(seeBook.equals("Yes"))
		{
			ArrayList<Integer>issuedBooks=currentStudent.getIssuedBooks();
			if(issuedBooks.size()==0)
			{
				System.out.println("Nothing to show! :( No books have been issued ");
			}
			else
			{
				//System.out.println("Book Id | Book Name  | Author| Field  | Availability");
			for(int bookId:issuedBooks)
			{
				System.out.println("**************************************************************************************");
				Book currBook=bookDao.searchBook(bookId);
				System.out.println("book Id: "+currBook.getBookId());
				System.out.println("book name: "+currBook.getBookName());
				System.out.println("availability: "+currBook.isAvailability());
				System.out.println("book author: "+currBook.getAuthor());
				System.out.println("book field: "+currBook.getBookField());
				System.out.println("book issue date: "+currBook.getIssueDate());
				System.out.println("book expected return date: "+currBook.getReturnDate());
				
				bookService.fineOnABook(currBook);
			}
			}
			
	        
		}
		}
		else
			throw new StudentIdNotFoundException("The given student id is not registered!");
		
		
	}

	

	@Override
	public boolean IssueBook(int studentId) throws StudentIdNotFoundException, BookNotFoundException {
		Student student=studentDao.searchStudent(studentId);
		if(student!=null)
		{
		int count=studentDao.countIssuedBooksforStudent(studentId);
		if(count<maxmNoOfIssuedBooks)
		{
			System.out.println("Enter book Name to check availability:");
			String bookName=scanner.nextLine();
			bookService.searchBookByName(bookName);
			System.out.println("Found an available book id?(yes/no)");
			String avlbl=scanner.nextLine();
			if(avlbl.equals("yes"))
			{
				System.out.println("Enter book id to issue that book:");
				try
				{
				int issueBookId=scanner.nextInt();
				Book book=bookDao.searchBook(issueBookId);
				if(book!=null)
				{
					if(book.isAvailability())
					{
				LocalDate currIssueDate=LocalDate.now();
				LocalDate currReturnDate=currIssueDate.plusDays(maxmDaysWithNoFine);
				return bookDao.updateBookStatus(issueBookId,false,studentId,currIssueDate,currReturnDate);
					}
					else
					{
						System.out.println("Sorry the given book is already issued to geek with student id "+book.getStudentId());
					}
				}
				else
					throw new BookNotFoundException("The given book id is not present in the library!");
				}
				catch(InputMismatchException e)
				{
					System.out.println("Meditate a bit and put the book id more carefully; better luck ! Found Illegal book id format");
				}
				
			
			}
			else
			{
				System.out.println("Nevermind try getting any other book! we have abundant books with ocean of knowledge ;)");
			}
		}
		else
		{
			System.out.println("Can't issue more books : maximum limit reached");
			
		}
		}
		//to ---do----d
		else
			throw new StudentIdNotFoundException("This geek is from an alien planet;  not registered!! ");
		return false;
		
	}

	@Override
	public boolean ReturnBook(int studentId) throws StudentIdNotFoundException, BookNotFoundException {
		Student student=studentDao.searchStudent(studentId);
		if(student!=null) {
		System.out.println("Enter book id to return book:");
try
{
	try
	{
		int retBookId=scanner.nextInt();
		Book returnBook=bookDao.searchBook(retBookId);
		if(returnBook!=null)
		{
			if(returnBook.getStudentId()==studentId)
			{
		bookService.fineOnABook(returnBook);
		return bookDao.updateBookStatus(retBookId,true, studentId, null, null);
			}
			else
			{
				System.out.println("Can't return this book ; as it has not been issued to this geek");
			}
		}
		else
			throw new BookNotFoundException("The given book id is not present in the library!");
			
	}
	catch(InputMismatchException e)
	{
		System.out.println("Meditate a bit and put the book id more carefully; better luck ! Found Illegal book id format");
	}
	
	
		
}
catch(InputMismatchException e)
{
	System.out.println("Meditate a bit and put the student id more carefully; better luck ! Found Illegal student id format");
}
		}
		else
			throw new StudentIdNotFoundException("This geek is from an alien planet;  not registered!! ");
			
return false;
		
	}


	
}
