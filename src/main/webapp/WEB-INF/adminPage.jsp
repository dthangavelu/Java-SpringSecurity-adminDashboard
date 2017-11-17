<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <h1>Welcome to the Admin Page <c:out value="${currentUser.firstName}"></c:out></h1>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    <br><br>
    
    <table>
  <tr>
    <th>Name</th>
    <th>Email</th>
    <th>Action</th>
  </tr>
  <c:forEach var="user" items="${ allUsers }">
  <tr>
    <td><c:out value="${user.firstName}"></c:out> <c:out value="${user.lastName}"></c:out></td>
    <td><c:out value="${user.email}"></c:out></td>
    <td>
	    <c:forEach var="role" items="${user.roles }">
		    <c:if test="${role.getName() == 'ROLE_ADMIN'}">	        
		        Admin
		    </c:if>
		    <c:if test="${role.getName() == 'ROLE_USER'}">	        
		        <a href="/delete/${user.id }">Delete</a> | <a href="/admin/new/${user.id }">Make admin</a>
		    </c:if>	    
	    </c:forEach>
    </td>
  </tr>  
  </c:forEach>
</table>

</body>
</html>