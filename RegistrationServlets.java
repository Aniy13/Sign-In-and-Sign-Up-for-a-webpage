package com.developer.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlets
 */
@WebServlet("/register")
public class RegistrationServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("name");
		String upwd=request.getParameter("pass");
		String uemail=request.getParameter("email");
		String umobileno=request.getParameter("contact");
		
//		PrintWriter out=response.getWriter();
//		out.print(uname);
//		out.print(upwd);
//		out.print(uemail);
//		out.print(umobileno);
		
		String url="jdbc:mysql://localhost:3306/youtube?useSSL=false";
//		String db="myJDBC";
		String userName="root";
		String password="pass1234";
		Connection con=null;
		RequestDispatcher dispatcher=null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(url,userName,password);
		PreparedStatement pst=con.prepareStatement("INSERT into users(uname,upwd,umail,umobileno) values(?,?,?,?)");
		pst.setString(1,uname);
		pst.setString(2,upwd);
		pst.setString(3,uemail);
		pst.setString(4,umobileno);
		
		int rowCount =pst.executeUpdate();
		dispatcher =request.getRequestDispatcher("registration.jsp");
		if(rowCount>0) {
			request.setAttribute("status", "success");
		}else {
			request.setAttribute("status", "failed");
		}
		dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				 if (con != null) {
			con.close();}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
