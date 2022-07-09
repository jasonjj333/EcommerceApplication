<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Management Application</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"></head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #333232">
			<div>
				<a href="https://www.xadmin.net" class="navbar-brand"
					style="color: white"> User Management Application </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Users</a></li>
			</ul>
			
			<c:if test="${accountId != null}">
				<ul class="navbar-nav">
					<li>
						<a href="<%=request.getContextPath()%>/logout"
						class="nav-link">Sign Out</a></li>
				</ul>
			</c:if>
		</nav>
	</header>
	<br>
	
	<div class="row">
		<!-- <div class"alert alert-success" *ngIf='message'>{{message}}</div> -->
		
		<div class = "container">
			<h3 class = "text-center">List of Products</h3>
			<hr>
			<div class = "container text-left">
				<a href = "<%=request.getContextPath()%>/new" class = "btn btn-success">Add New Product</a>
			</div>
			<br>
			<table class = "table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Product</th>
						<th>Description</th>
						<th>Price</th>
						<th>Stock</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${listProduct}">
						<tr>
							<td><c:out value="${product.productId}"/></td>
							<td><c:out value="${product.name}"/></td>
							<td><c:out value="${product.description}"/></td>
							<td><c:out value="${product.price}"/></td>
							<td><c:out value="${product.stock}"/></td>
							<td><a href="edit?id=<c:out value='${product.productId}'/>">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?id=<c:out value='${product.productId}'/>">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>