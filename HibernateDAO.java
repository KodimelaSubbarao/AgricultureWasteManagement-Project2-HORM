package com.voidmain.dao;

import java.util.List;

import com.voidmain.pojo.Product;
import com.voidmain.pojo.Request;
import com.voidmain.pojo.User;

public class HibernateDAO {

	public static String isValidUser(String username,String password)
	{
		String role="";

		User user=getUserById(username);

		if(user!=null && user.getPassword().equals(password) && user.getStatus().equals("yes"))
		{
			role=user.getUserType();
		}

		return role;
	}

	//============================================================================

	public static User getUserById(String id)
	{
		return (User)HibernateTemplate.getObject(User.class,id);
	}

	public static int deleteUser(int id)
	{
		return HibernateTemplate.deleteObject(User.class,id);
	}

	public static List<User> getUsers()
	{
		List<User> users=(List)HibernateTemplate.getObjectListByQuery("From User");

		return users;
	}
	
	//========================================================================
	
	public static Product getProductById(int id)
	{
		return (Product)HibernateTemplate.getObject(Product.class,id);
	}

	public static int deleteProduct(int id)
	{
		return HibernateTemplate.deleteObject(Product.class,id);
	}

	public static List<Product> getProducts()
	{
		List<Product> products=(List)HibernateTemplate.getObjectListByQuery("From Product");

		return products;
	}

	//=========================================================================
	
	public static Request getRequestById(int id)
	{
		return (Request)HibernateTemplate.getObject(Request.class,id);
	}

	public static int deleteRequest(int id)
	{
		return HibernateTemplate.deleteObject(Request.class,id);
	}

	public static List<Request> getRequests()
	{
		List<Request> requests=(List)HibernateTemplate.getObjectListByQuery("From Request");

		return requests;
	}
	
	//=========================================================================
}
