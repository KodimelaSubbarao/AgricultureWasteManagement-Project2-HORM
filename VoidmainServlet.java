package com.voidmain.servlets;

import java.io.IOException;


import com.voidmain.dao.HibernateTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VoidmainServlet")
public class VoidmainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	HttpServletRequest request;
	HttpServletResponse response;

	Object obj=null;
	String redirect=null;
	String type;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.request=request;
		this.response=response;

		try {

			type=request.getParameter("type");
			redirect=request.getParameter("redirect");

			if(type!=null && redirect!=null)
			{
				obj=Class.forName(type).newInstance();

				Object object=HttpRequestParser.parseRequest(request, obj);

				if(HibernateTemplate.addObject(object)==1)
				{
					response.sendRedirect(redirect+"?status=success");
				}
				else
				{
					response.sendRedirect(redirect+"?status=failed");
				}

			}

			//======================================================

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
