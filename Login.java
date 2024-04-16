 package com.developer.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dis=null;
		Connection con=null;
		try {
		  Class.forName("com.mysql.cj.jdbc.Driver");
		  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","pass1234");
		  PreparedStatement pst = con.prepareStatement("select * from users where umail=? and upwd=?");
		  pst.setString(1, uemail);
		  pst.setString(2, upwd);
		  ResultSet rs=pst.executeQuery();
		  if(rs.next()) {
			  session.setAttribute("name", rs.getString("uname"));
			  dis = request.getRequestDispatcher("index.jsp");
		  }else {   
			  request.setAttribute("status","failed");
			  dis = request.getRequestDispatcher("login.jsp");
		  }
		  dis.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
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
