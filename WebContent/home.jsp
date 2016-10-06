<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home screen</title>
</head>
<body>

	<%
	
		String user = (String)session.getAttribute("ben");
	
		if (user == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		out.print("<h2>Hallo " + user + "</h2>");
	%>
	
</body>
</html>