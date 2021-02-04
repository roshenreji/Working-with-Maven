package com.roshen.maven.workingwithMaven;
import java.sql.*;

public class JdbcDAODemo {

	public static void main(String[] args) {
		
		StudentDAO dao=new StudentDAO();
		dao.connect();
		try {
			Student s1=dao.getStudentDetails(4);
			System.out.println(s1.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Student s2=new Student();
		s2.rollNo=5;
		s2.name="Bobby";
		
		dao.addStudent(s2.rollNo, s2.name);

	}

}

class Student{
	int rollNo;
	String name;
	
}


class StudentDAO{
	Connection con=null;
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdatabase", "root", "Helloworld1234");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
	
	public void addStudent(int id,String name) {
		String insertQuery="Insert into studentTable values(?,?)";
		try {
			PreparedStatement pt=con.prepareStatement(insertQuery);
			pt.setInt(1, id);
			pt.setString(2, name);
			int count=pt.executeUpdate();
			System.out.println(count+" row/s updated");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
	
	public Student getStudentDetails(int rollNumber) throws Exception {
		Student s=new Student();
		s.rollNo=rollNumber;
		//throw new ArithmeticException("Exceptiob not valid");
		String query="select name from studentTable where rollNumber="+rollNumber;
		
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);
		//System.out.println("im here");
		rs.next();
		String name=rs.getString(1);
		//System.out.println("after name");
		s.name=name;
		return s;
	}
}