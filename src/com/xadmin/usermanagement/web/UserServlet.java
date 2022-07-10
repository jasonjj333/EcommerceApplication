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
import javax.servlet.http.HttpSession;

import com.xadmin.usermanagement.bean.User;
import com.xadmin.usermanagement.dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
    HttpSession session;
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
			
		case "/logout":
			try {
				logoutUser(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/products":
			try {
				goToProducts(request,response);
			} catch (IOException e) {
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
	
	private void goToProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductServlet/list");
		dispatcher.forward(request, response);
	}

	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			session.invalidate();
		} catch (IllegalStateException e) {
			System.out.println("Session invalidated already.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
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
	
	
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(userDao.searchEmailPassword(email, password) != null) {
			user = userDao.searchEmailPassword(email, password);
			session = request.getSession();
			session.setAttribute("accountId", user.getId());
			session.setAttribute("accountName", user.getName());
			session.setAttribute("accountEmail", user.getEmail());
			session.setAttribute("accountPassword", user.getPassword());
			session.setAttribute("accountBillingAddress", user.getBillingAddress());
			session.setAttribute("accountAdmin", user.getAdmin());
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
			if(session.getAttribute("accountId") == null) {
				System.out.println("New User Logged in");
				session = request.getSession();
				session.setAttribute("accountId", newUser.getId());
				session.setAttribute("accountName", newUser.getName());
				session.setAttribute("accountEmail", newUser.getEmail());
				session.setAttribute("accountPassword", newUser.getPassword());
				session.setAttribute("accountBillingAddress", newUser.getBillingAddress());
				session.setAttribute("accountAdmin", newUser.getAdmin());
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
