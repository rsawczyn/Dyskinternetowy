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
	nagwek
	</div>
	
	<div id="menu_1">
	menu 1
		<ul>
			<li>link 1</li>
			<li>link 2</li>
			<li>link 3</li>
			<li>link 4</li>
			<li>link 5</li>
			<li>link 6</li>
			<li>link 7</li>
			<li>link 8</li>
			<li>link 9</li>
			<li>link 10</li>
		</ul>
	</div>
	
	<div id="menu_2">
		<ul>
			<li>link 11</li>
			<li>link 12</li>
			<li>link 13</li>
			<li>link 14</li>
			<li>link 15</li>
			<li>link 16</li>
			<li>link 17</li>
			<li>link 18</li>
			<li>link 19</li>
			<li>link 20</li>
		</ul>
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
	<div id="stopka">
	stopka
	</div>
	
</div>
</body>

</html>
