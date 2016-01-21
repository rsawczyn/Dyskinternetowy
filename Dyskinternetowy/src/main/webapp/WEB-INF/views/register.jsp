<%@page import="org.zut.dyskDomain.User"%>
<html>
<head>
	<link rel="Stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css" />
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
			<% if(request.getAttribute("InValid")!=null || request.getMethod() == "GET"){
			%>
			<form:form method="post" commandName="User">
				<table border="0">
	                <tr>
	                    <td colspan="2" align="center"><h2>Spring MVC Form Demo - Registration</h2></td>
	                </tr>
	                <tr>
	                    <td>Imie:</td>
	                    <td><form:input path="Imie" /><%if(request.getAttribute("ImieError")!=null){%>
	                    <p>${ImieError }</p>
	                    <%} %></td>
	                </tr>
	                <tr>
	                    <td>Nazwisko:</td>
	                    <td><form:input path="Nazwisko" /><%if(request.getAttribute("NazwiskoError")!=null){%>
	                    <p>${NazwiskoError }</p>
	                    <%} %></td>
	                </tr>
	                <tr>
	                    <td>User Name:</td>
	                    <td><form:input path="Login" /><%if(request.getAttribute("LoginError")!=null){%>
	                    <p>${LoginError }</p>
	                    <%} %></td>
	                </tr>
	                <tr>
	                    <td>Password:</td>
	                    <td><form:password path="Haslo" /> <%if(request.getAttribute("HasloError")!=null){%>
	                    <p>${HasloError }</p>
	                    <%} %>
	                    </td>
	                </tr>
	                <tr>
	                    <td>E-mail:</td>
	                    <td><form:input path="Email" /><%if(request.getAttribute("EmailError")!=null){%>
	                    <p>${EmailError }</p>
	                    <%} %></td>
	                   
	                </tr>             
	              
	                <tr>
	                    <td colspan="2" align="center"><input type="submit" value="Register" /></td>
	                </tr>
	            </table>		
			</form:form>
			<%}
			else if(request.getMethod() == "POST" && request.getAttribute("InValid")==null)
			{%>
			<p>${message }</p>
			<form:form method="post" action="login" commandName="UserLoginAtt">
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
			<%} %>
		</div>
		
	</div>
	<div id="stopka">
	stopka
	</div>
	
</div>
</body>

</html>