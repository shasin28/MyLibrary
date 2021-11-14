package com.sukhoi.persistence;

import com.sukhoi.bean.Student;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDaoImpl implements StudentDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/uit_library", "root",
				"shalini");
		return connection;
	}

	@Override
	public ArrayList<Student> getAllStudents() throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student_lib_details");
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Student> students = new ArrayList<Student>();
		// 3.Process Result
		while (resultSet.next()) {
			int sId = resultSet.getInt("student_id");
			String na = resultSet.getString("student_name");
			String brnch = resultSet.getString("student_branch");
			ArrayList<Integer> issd_books = new ArrayList<Integer>();
			int b1 = resultSet.getInt("book1");
			if (b1 > 0)
				issd_books.add(b1);
			int b2 = resultSet.getInt("book2");
			if (b2 > 0)
				issd_books.add(b2);
			int b3 = resultSet.getInt("book3");
			if (b3 > 0)
				issd_books.add(b3);

			Student student = new Student(sId, na, brnch, issd_books);
			students.add(student);
		}
		preparedStatement.close();
		connection.close();
		return students;
	}

	@Override
	public boolean insertStudent(Student student) {
		Connection connection;
		try {
			connection = getConnection();
			String insertQuery = "insert into student_lib_details(student_id,student_name,student_branch)"
					+ "values (?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, student.getStudentId());
			preparedStatement.setString(2, student.getStudentName());
			preparedStatement.setString(3, student.getStudentBranch());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			return true;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteStudent(int studentId) {
		try {
			Connection connection = getConnection();
			String delStmt = "delete from student_lib_details where student_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(delStmt);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			return true;

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;

	}

	@Override
	public Student searchStudent(int studentId) {
		try {
			Connection connection = getConnection();
			String searchStmt = "select * from student_lib_details where student_id=" + studentId;
			java.sql.Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(searchStmt);
			while (resultSet.next()) {
				int sId = resultSet.getInt("student_id");
				String na = resultSet.getString("student_name");
				String brnch = resultSet.getString("student_branch");
				ArrayList<Integer> issd_books = new ArrayList<Integer>();
				int b1 = resultSet.getInt("book1");
				if (b1 > 0)
					issd_books.add(b1);
				int b2 = resultSet.getInt("book2");
				if (b2 > 0)
					issd_books.add(b2);
				int b3 = resultSet.getInt("book3");
				if (b3 > 0)
					issd_books.add(b3);

				Student student = new Student(sId, na, brnch, issd_books);
				return student;
			}
			stmt.close();
			connection.close();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int countIssuedBooksforStudent(int studId) {
		Connection connection;
		try {
			connection = getConnection();
			String countQuery = "select book1,book2,book3 from student_lib_details where student_id=" + studId;
			PreparedStatement preparedStatement = connection.prepareStatement(countQuery);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int count = 0;
				int b1 = resultSet.getInt("book1");
				if (b1 > 0)
					count++;
				int b2 = resultSet.getInt("book2");
				if (b2 > 0)
					count++;
				int b3 = resultSet.getInt("book3");
				if (b3 > 0)
					count++;
				preparedStatement.close();
				connection.close();
				return count;
			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return -1;
	}

}
