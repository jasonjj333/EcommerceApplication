package com.xadmin.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.usermanagement.bean.User;
import com.xadmin.usermanagement.dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
    private User accountUser;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		case "/new":
			showNewForm(request, response);
			break;

		case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case "/delete":
			try {
				deleteUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/login":
			try {
				loginUser(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		// handles list redirect
		case "/list":
			try {
				listUser(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			try {
				listUser(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	private void sendUserHome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(accountUser.getAdmin() == 1) {
			response.sendRedirect("list");
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer-page.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(userDao.searchEmailPassword(email, password) != null) {
			user = userDao.searchEmailPassword(email, password);
			accountUser = user;
			System.out.println("Logging in user: " + user.getName() + " " + user.getId() + " admin:" + user.getAdmin());
			sendUserHome(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("accountUser", accountUser);
		dispatcher.forward(request, response);
	}
	
	//insert user
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String billingAddress = request.getParameter("billing_address");
		System.out.println("Attempting to insert: " + name + " " + email);
		User newUser = new User(name, email, password, billingAddress);
		if(userDao.searchEmail(email) != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
		}
		else {
			if(request.getParameter("admin") == null || !request.getParameter("admin").equals("1")) {
				newUser.setAdmin(0);
			}
			else {
				newUser.setAdmin(1);
			}
			if(accountUser == null) {
				accountUser = newUser;
			}
			userDao.insertUser(newUser);
			sendUserHome(request, response);
		}
	
	}
	
	//delete user
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			userDao.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}

	//editUser
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser;
		try {
			existingUser = userDao.selectUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user", existingUser);
			request.setAttribute("accountUser", accountUser);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//update
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String billingAddress = request.getParameter("billing_address");
		User user = new User(id, name, email, password, billingAddress);
		if(request.getParameter("admin") == null || !request.getParameter("admin").equals("1")) {
			user.setAdmin(0);
		}
		else {
			user.setAdmin(1);
		}
		userDao.updateUser(user);
		sendUserHome(request, response);
	}
	
	//default
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		try {
			List<User> listUser = userDao.selectAllUsers();
			request.setAttribute("listUser", listUser);
			request.setAttribute("accountUser", accountUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
