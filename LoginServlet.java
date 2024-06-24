package com.voidmain.servlets;

import java.io.IOException;

import com.voidmain.dao.HibernateDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username=request.getParameter("username").trim();
		String password=request.getParameter("password").trim();
		
		if(username.equals("admin") && password.equals("admin"))
		{
			request.getSession().setAttribute("role","admin");
			response.sendRedirect("home.jsp");
		}
		else
		{
			String userType=HibernateDAO.isValidUser(username, password);
			
			if(!userType.equals(""))
			{
				request.getSession().setAttribute("username",username.toLowerCase());
				request.getSession().setAttribute("role",userType);
				response.sendRedirect("home.jsp");
			}
			else
			{
				response.sendRedirect("login.jsp?status=Invalid Username and Password");
			}
		}
	}
}
