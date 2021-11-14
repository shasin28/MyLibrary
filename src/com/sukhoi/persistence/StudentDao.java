package com.sukhoi.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import com.sukhoi.bean.Student;

public interface StudentDao {

	ArrayList<Student> getAllStudents() throws ClassNotFoundException, SQLException;

	boolean insertStudent(Student student);

	boolean deleteStudent(int studentId);

	Student searchStudent(int studentId);

	int countIssuedBooksforStudent(int studId);

}
