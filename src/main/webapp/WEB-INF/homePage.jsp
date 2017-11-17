<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome to Regular User Page <c:out value="${currentUser.firstName}"></c:out></h1>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    <br><br>
    First Name: <span><c:out value="${currentUser.firstName}"></c:out></span><br>
    Last Name: <span><c:out value="${currentUser.lastName}"></c:out></span><br>
    Email: <span><c:out value="${currentUser.email}"></c:out></span><br>
    Sign-up date: <span><c:out value="${currentUser.createdAt}"></c:out></span><br>
    Last Sign In: <span><c:out value="${currentUser.updatedAt}"></c:out></span><br>
    
</body>
</html>