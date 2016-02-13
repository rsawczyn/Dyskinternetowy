<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<% if(request.getUserPrincipal()!=null){ 
				response.sendRedirect("/dysk/user/");
			}%>
<head>

	<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<div id="strona">

	<div id="naglowek">
		
		<img id="LogoDIV" src="${pageContext.request.contextPath}/resources/Images/file-server-300px.png" alt="Logo">
		
	</div>
	
	<div id="menu_1">
	Szybkie Menu
		<ul>
			<li><a href="/dysk/register">Zarejestruj sie</a></li>
			
		</ul>
	</div>
	
	<div id="menu_2">
		<p>Miejsce na Reklame</p>
	</div>
	
	<div id="zawartosc">	
		<div align="center">
			<% if(request.getAttribute("registrationOK")!=null){ %>
			<fieldset> <p>Registration Successful</p> </fieldset>
			<%} %>
			<% if(request.getParameter("loginErr")!=null){ %>
			<fieldset> <p>Logowanie nie powiodlo sie</p> </fieldset>
			<%} %>
			<% if(request.getParameter("logoutOk")!=null){ %>
			<fieldset> <p>Wylogowales sie</p> </fieldset>
			<%} %>	
			
			<p>Witaj w usludze dysku sieciowego Zaloguj sie aby kontynuowac  </p>
			<form:form id="loginform" action="j_spring_security_check" method="POST" modelAttribute="User">
				<table border="0">
		        	<tr>
	                    <td colspan="2" align="center"><h2>Logowanie!</h2></td>
	                </tr>
	                <tr>
	                    <td>Login:</td>
	                    <td><form:input path="Login" /></td>
	                </tr>
	                <tr>
	                    <td>Haslo:</td>
	                    <td><form:password path="Haslo" /></td>
	                </tr>          
	                <tr>
	                    <td colspan="2" align="center"><input type="submit" value="Zaloguj" /></td>
	                </tr>
		        </table>
			</form:form>
			<p>Nie masz Konta? <a href="/dysk/register/">Zajerestruj sie</a></p>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
	
</div>
</body>

</html>
