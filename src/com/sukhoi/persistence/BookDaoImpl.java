package com.sukhoi.persistence;

import java.util.ArrayList;
import com.sukhoi.bean.Book;
import com.sukhoi.bean.Student;
import com.sukhoi.exceptions.BookNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;



public class BookDaoImpl implements BookDao {

	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/uit_library", "root", "tulLu1998");
		return connection;
	}
	@Override
	public boolean insertBook(Book book) throws ClassNotFoundException, SQLException {
		
        Connection connection = getConnection();
       // System.out.println(book.getBookName());
        String insertQuery="insert into book_details(book_name,book_author,book_field,availability)"
        		+ "values (?,?,?,?)";
		
		PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);
		
		preparedStatement.setString(1,book.getBookName());
		preparedStatement.setString(2,book.getAuthor());
		preparedStatement.setString(3,book.getBookField());
		preparedStatement.setBoolean(4,book.isAvailability());
		/*preparedStatement.setInt(5,book.getStudentId());
		preparedStatement.setDate(6,(java.sql.Date) Date.from(book.getIssueDate().atStartOfDay()
		        .atZone(ZoneId.systemDefault())
		        .toInstant()));
		preparedStatement.setDate(7,(java.sql.Date) Date.from(book.getIssueDate().atStartOfDay()
		        .atZone(ZoneId.systemDefault())
		        .toInstant()));*/
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		
		
		return true;
	}

	@Override
	public boolean deleteBook(int bId){
	
			Connection connection;
				try {
					connection = getConnection();
					String delStmt="delete from book_details where book_id="+bId;
					PreparedStatement preparedStatement=connection.prepareStatement(delStmt);
					preparedStatement.executeUpdate();
					preparedStatement.close();
					connection.close();
					return true ;
				} 
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} 
				return false;
			
			
			
		
	}

	@Override
	public boolean updateBookStatus(int bookId, boolean availability, int studentId, LocalDate issueDate,
			LocalDate returnDate) {
		try {
			Connection connection = getConnection();
			String updStmt;
			if(issueDate==null)
			{
			updStmt="update book_details SET student_id=0,availability="+availability+" where book_id="+bookId;	
			//StudentDao studentDaoImpl=new StudentDaoImpl();
			//Student stud=studentDaoImpl.searchStudent(studentId);
			String searchStmt="select * from student_lib_details where student_id="+studentId;
			
			
			PreparedStatement zpreparedStatement=connection.prepareStatement(searchStmt);
			ResultSet zresultSet=zpreparedStatement.executeQuery();
			
			while(zresultSet.next())
			{
			//int sId=zresultSet.getInt("student_id");
		//	String na=resultSet.getString("student_name");
			//String brnch=resultSet.getString("student_branch");
			//ArrayList<Integer>issd_books=new ArrayList<Integer>();
			int b1=zresultSet.getInt("book1");	
			int b2=zresultSet.getInt("book2");		
			int b3=zresultSet.getInt("book3");
			
			if(b1==bookId)
			{
				String updSmt="update student_lib_details set book1=0 where student_id="+studentId;
				PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
				zzpreparedStatement.executeUpdate();
				zzpreparedStatement.close();
			}
			
			else if(b2==bookId)
			{
				String updSmt="update student_lib_details set book2=0 where student_id="+studentId;
				PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
				zzpreparedStatement.executeUpdate();
				zzpreparedStatement.close();
			}
			else if(b3==bookId)
			{
				String updSmt="update student_lib_details set book3=0 where student_id="+studentId;
				PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
				zzpreparedStatement.executeUpdate();
				zzpreparedStatement.close();
			}
			
			//System.out.println("reached126");
			//Student student=new Student(sId,na,brnch,issd_books);
			//return student;
			
			
			}
			}
			else
			{
				updStmt="update book_details SET student_id="+studentId+",availability="+availability+",issue_date='"+issueDate+"',return_date='"+returnDate+"' where book_id="+bookId;
				String searchStmt="select * from student_lib_details where student_id="+studentId;
				
				
				PreparedStatement zpreparedStatement=connection.prepareStatement(searchStmt);
				ResultSet zresultSet=zpreparedStatement.executeQuery();
				
				while(zresultSet.next())
				{
			
				int b1=zresultSet.getInt("book1");	
				int b2=zresultSet.getInt("book2");		
				int b3=zresultSet.getInt("book3");		
				
				if(b1==0)
				{
					String updSmt="update student_lib_details set book1="+bookId+" where student_id="+studentId;
					PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
					zzpreparedStatement.executeUpdate();
					zzpreparedStatement.close();
				}
				
				else if(b2==0)
				{
					String updSmt="update student_lib_details set book2="+bookId+" where student_id="+studentId;
					PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
					zzpreparedStatement.executeUpdate();
					zzpreparedStatement.close();
				}
				else if(b3==0)
				{
					String updSmt="update student_lib_details set book3="+bookId+" where student_id="+studentId;
					PreparedStatement zzpreparedStatement=connection.prepareStatement(updSmt);
					zzpreparedStatement.executeUpdate();
					zzpreparedStatement.close();
				}
					
				
				
				
				}
		     }
			PreparedStatement preparedStatement=connection.prepareStatement(updStmt);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public ArrayList<Book> getAllBooks() throws ClassNotFoundException, SQLException {
		
		Connection connection = getConnection();
		
		PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM book_details");
		ResultSet resultSet=preparedStatement.executeQuery();
		
		ArrayList<Book> books=new ArrayList<Book>();
		//3.Process Result
		while(resultSet.next()) {
			int bId=resultSet.getInt("book_id");
			String na=resultSet.getString("book_name");
			String auth=resultSet.getString("book_author");
			String field=resultSet.getString("book_field");
			boolean avbl=resultSet.getBoolean("availability");
			int stuId=resultSet.getInt("student_id");
			if(avbl==false)
			{
			LocalDate idt=Instant.ofEpochMilli(resultSet.getDate("issue_date").getTime())
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate();
			LocalDate rdt=Instant.ofEpochMilli(resultSet.getDate("return_date").getTime())
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate();
			Book book=new Book(bId,na,auth,field,avbl,stuId,idt,rdt);
			books.add(book);
			}
			else
			{
				Book book=new Book(bId,na,auth,field,avbl);
				books.add(book);
			}
			//books.add(book);
		}
		preparedStatement.close();
		connection.close();
		return books;
	
	}

	

	@Override
	public Book searchBook(int bookId) {
		
		 try {
				Connection connection = getConnection();
				String searchStmt="select * from book_details where book_id="+bookId;
				//System.out.println(searchStmt);
				PreparedStatement preparedStatement=connection.prepareStatement(searchStmt);
				//preparedStatement.setInt(1,bookId);
				ResultSet resultSet=preparedStatement.executeQuery();
			
              //  System.out.println(resultSet.getFetchSize());
				while(resultSet.next())
				{
					int bId=resultSet.getInt("book_id");
					String na=resultSet.getString("book_name");
					String auth=resultSet.getString("book_author");
					String field=resultSet.getString("book_field");
					
				     boolean avbl=resultSet.getBoolean("availability");
				if(avbl)
				{
					
				
				
				
				Book book=new Book(bId,na,auth,field,avbl);
				return book;
				}
				else
				{
					   int stuId=resultSet.getInt("student_id");
						LocalDate idt=Instant.ofEpochMilli(resultSet.getDate("issue_date").getTime())
							      .atZone(ZoneId.systemDefault())
							      .toLocalDate();
						LocalDate rdt=Instant.ofEpochMilli(resultSet.getDate("return_date").getTime())
							      .atZone(ZoneId.systemDefault())
							      .toLocalDate();
						Book book=new Book(bId,na,auth,field,avbl,stuId,idt,rdt);
						return book;
						
				}
				
				
				}
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		return null;
	
	}
	@Override
	public int getFinePerDay(String bookType) {
		try {

			Connection connection = getConnection();
			String searchStmt="select fine_per_day from fine where book_type='"+bookType+"'";
			PreparedStatement preparedStatement=connection.prepareStatement(searchStmt);
			int amount=0;
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.getFetchSize()==0)
			{
				String zsearchStmt="select fine_per_day from fine where book_type='Other'";
				PreparedStatement zpreparedStatement=connection.prepareStatement(zsearchStmt);
				//int amount=0;
				ResultSet zresultSet=zpreparedStatement.executeQuery();
				while(zresultSet.next())
				{
				return amount=zresultSet.getInt("fine_per_day");
				}
			}
			while(resultSet.next())
			{
			amount=resultSet.getInt("fine_per_day");
			}
			
				/*String searchhStmt="select fine_per_day from fine where book_type='Other'";
				PreparedStatement prepareddStatement=connection.prepareStatement(searchhStmt);
				ResultSet rs=prepareddStatement.executeQuery();
				amount=rs.getInt("book_type");*/
				//amount=3;
		
			return amount;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	@Override
	public ArrayList<Book> searchBookByName(String BookName) {
	    
        Connection connection;
		try {
			connection = getConnection();
			//System.out.println(BookName);
			String searchStmt="select * from book_details where book_name='"+BookName+"'";
			PreparedStatement preparedStatement=connection.prepareStatement(searchStmt);
			//preparedStatement.setString(1,BookName);
			ResultSet resultSet=preparedStatement.executeQuery();
			//System.out.println(resultSet.getFetchSize());
			ArrayList<Book> books=new ArrayList<Book>();
			//3.Process Result
			while(resultSet.next()) {
				int bId=resultSet.getInt("book_id");
				String na=resultSet.getString("book_name");
				String auth=resultSet.getString("book_author");
				String field=resultSet.getString("book_field");
				boolean avbl=resultSet.getBoolean("availability");
			//	int stuId=resultSet.getInt("student_id");
				/*LocalDate idt=Instant.ofEpochMilli(resultSet.getDate("issue_date").getTime())
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
				LocalDate rdt=Instant.ofEpochMilli(resultSet.getDate("return_date").getTime())
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();*/
				
				
				Book book=new Book(bId,na,auth,field,avbl);
				books.add(book);
			}
			preparedStatement.close();
			connection.close();
			return books;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	

}
