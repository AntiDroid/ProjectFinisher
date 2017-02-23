<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
if(session.getAttribute("benutzer") == null){
	response.sendRedirect("login.jsp");
	return;
}
else{
	Client user = (Client) session.getAttribute("benutzer");
	if(user instanceof Lehrer){
		response.sendRedirect("lehrer_kurse.jsp");
		return;
	}
	else if(user instanceof Student){
		response.sendRedirect("studenten_kurse.jsp");
		return;
	}
}
%>
</head>
<body>
</body>
</html>