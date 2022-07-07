# EcommerceApplication

## Description

A capstone project from HCL and Verizon to create a mock up e-commerce web application. 

## Latest Update

- ***07/07/22*** New Account creation accessible to new users in login page. Log in credentials now saved across pages. Admins can now create admins/ update users to admins while customers cannot. 
- ***07/05/22*** Added login page for users to sign in to. Users can log in as admin and be redirected to user-list.jps, or as customers and be redirected to temporary placeholder page. Incorrect login information results in redirect back to login page.  

## What To Improve

- [x] Add 'Create New Account' Button to login page and redirect to [user-form.jsp](/WebContent/user-form.jsp)
- [x] Prohibit accounts with existing email to be created in [user-form.jsp](/WebContent/user-form.jsp)
- [x] Have user account information saved across page redirects and displayed after login
- [ ] Account User stored in [UserServlet](/src/com/xadmin/usermanagement/web/UserServlet.java) and need to call request.setAttribute("accountUser",accountUser) for each new jsp page. Find more optimal solution  
- [x] [user-form.jsp](/WebContent/user-form.jsp) is shared between customers and admins making accounts, should be seperate
- [ ] [user-form.jsp](/WebContent/user-form.jsp) provides visibility based on logged in account. Change redirect after submitting  
(customer -> [customer-page.jsp](/WebContent/customer-page.jsp) | admin -> [user-list.jsp](/WebContent/user-list.jsp))
- [ ] Create log out button and function to clear account credentials
#### Start on handling products:
- [ ] Create products table and POJO
- [ ] Implement product-list.jsp to display all products for admins (similar to [user-list.jsp](/WebContent/user-list.jsp))
- [ ] Implement different product list view for customers
- [ ] Add 'Product List' Button to [user-list.jsp](/WebContent/user-list.jsp) and redirect to product-list.jsp

#### Start on handling order details:

- [ ] Create orderdetails table and POJO
- [ ] Implement order-detail-list.jsp to display all order details
- [ ] Add 'Orders Details' Button to order-list and redirect to order-detail-list.jsp

#### Start on handling orders:

- [ ] Create orders table and POJO
- [ ] Implement order-list.jsp to display all orders
- [ ] Add 'Orders List' Button to [user-list.jsp](/WebContent/user-list.jsp) and redirect to order-list.jsp

![image](https://user-images.githubusercontent.com/72631106/177692494-c1110d05-a230-441d-88e2-43facbbf8cf3.png)

