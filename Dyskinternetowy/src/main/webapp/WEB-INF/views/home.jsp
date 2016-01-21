<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
	<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css" />
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
		<% if(request.getAttribute("registrationOK")!=null){ %>
		<p>Registration Successful</p>
		<%} %>
		<p>Witaj w usludze dysku sieciowego Zaloguj sie aby kontynuowac  </p>
		<form:form id="loginform" action="login" method="POST" modelAttribute="User">
			<table border="0">
	        	<tr>
                    <td colspan="2" align="center"><h2>Logowanie!</h2></td>
                </tr>
                <tr>
                    <td>User Name:</td>
                    <td><form:input path="Login" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="Haslo" /></td>
                </tr>          
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Zaloguj" /></td>
                </tr>
	        </table>
		</form:form>
	</div>
	<div id="stopka">
	stopka
	</div>
	
</div>
</body>

</html>
