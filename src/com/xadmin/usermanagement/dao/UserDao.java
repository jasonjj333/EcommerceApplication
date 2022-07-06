package com.xadmin.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.usermanagement.bean.User;

public class UserDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Awesome33@";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO customers" + " (name,email,password,billing_address) VALUES " + " (?,?,?,?);";
	private static final String SELECT_USER_BY_ID = "SELECT customerId,name,email,password,billing_address FROM customers WHERE customerId=?";
	private static final String SELECT_ALL_USERS = "SELECT * FROM customers";
	private static final String DELETE_USERS_SQL = "DELETE FROM customers WHERE customerId = ?;";
	private static final String UPDATE_USERS_SQL = "UPDATE customers SET name = ?, email = ?, password = ?, billing_address = ? WHERE customerId = ?;";
	private static final String SELECT_USER_BY_EMAIL_PASSWORD = "SELECT * FROM customers WHERE email = ? AND password = ?;";
	
	public UserDao() {
		
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//insert user
	public void insertUser(User user) {
		System.out.println(INSERT_USERS_SQL);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setNString(1, user.getName());
			preparedStatement.setNString(2, user.getEmail());
			preparedStatement.setNString(3, user.getPassword());
			preparedStatement.setNString(4, user.getBillingAddress());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		
	}
	
	//select user by id
	public User selectUser(int id) {
		User user = null;
		//Establish connection
		try(Connection connection = getConnection();
				//Create statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();
			
			//Process result set object
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String billingAddress = rs.getString("billing_address");
				user = new User(id,name,email,password,billingAddress);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
		
	}
	
	//select all users
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		
		//Establish connection
		try(Connection connection = getConnection();
				//Create statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();
			
			//Process result set object
			while(rs.next()) {
				int id = rs.getInt("customerId");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				users.add(new User(id,name,email,password,billingAddress,admin));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}
	
	//update user
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		//Establish connection
				try(Connection connection = getConnection();
						//Create statement using connection object
						PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);) {
					System.out.println("Updated User: " + preparedStatement);
					preparedStatement.setNString(1, user.getName());
					preparedStatement.setNString(2, user.getEmail());
					preparedStatement.setNString(3, user.getPassword());
					preparedStatement.setNString(4, user.getBillingAddress());
					preparedStatement.setInt(5, user.getId());
					
					rowUpdated = preparedStatement.executeUpdate() > 0;
				}
				return rowUpdated;	
	}
	
	//delete user by id
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try(Connection connection = getConnection();
				//Create statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
			System.out.println("Updated User: " + preparedStatement);
			preparedStatement.setInt(1, id);
			
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	//Print SQL Error
	private void printSQLException(SQLException ex) {
		for(Throwable e:ex) {
			if(e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
				System.err.println("Message: " + e.getLocalizedMessage());
				Throwable t = ex.getCause();
				while(t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public User searchEmailPassword(String email, String password) {
		User user = null;
		try(Connection connection = getConnection();
				//Create statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASSWORD);) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println(rs);
			//Process result set object
			while(rs.next()) {
				int id = rs.getInt("customerId");
				String name = rs.getString("name");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				System.out.println("User Found: " + id + " " + name);
				user = new User(id,name,email,password,billingAddress,admin);
			}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
}
	
