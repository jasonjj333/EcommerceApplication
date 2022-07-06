<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign in</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css"
	integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: blue">
			<div>
				<a href="https://www.xadmin.net" class="navbar-brand"> User
					Management Application </a>
			</div>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<form action="login" method="post">
					<caption>
						<h2>Login</h2>
					</caption>
					<fieldset class="form-group">
						<label>User Email</label> <input type="text"
							value="<c:out value='${user.email}' />" class="form-control"
							name="email">
					</fieldset>
					<fieldset class="form-group">
						<label>Password</label> <input type="text"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password">
					</fieldset>

					<button type="submit" class="btn btn-success">Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>