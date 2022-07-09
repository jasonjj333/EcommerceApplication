package com.xadmin.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xadmin.usermanagement.bean.Product;
import com.xadmin.usermanagement.dao.ProductDao;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDao productDao;
	HttpSession session;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		productDao = new ProductDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
		case "/list":
			listProducts(request, response);
			break;
		case "/logout":
			logoutUser(request,response);
			break;
		default:
			listProducts(request, response);
			break;
		}
	}

	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/UserServlet/logout");
		dispatcher.include(request, response);
		
	}

	private void sendUserHome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("Session Admin: " + session.getAttribute("accountAdmin"));
		if(((int)session.getAttribute("accountAdmin")) == 1) {
			response.sendRedirect("list");
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer-page.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void listProducts(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Product> listProduct = productDao.selectAllProducts();
			request.setAttribute("listProduct", listProduct);
			RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
