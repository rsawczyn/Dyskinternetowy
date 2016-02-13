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
		<img id="LogoDIV" src="${pageContext.request.contextPath}/resources/Images/file-server-300px.png" alt="Logo" >
	</div>
	
	<div id="menu_1">
	menu 1
		<ul>
			<li><a href="/dysk/">Zaloguj</a></li>
			
		</ul>
	</div>
	
	<div id="menu_2">
		<p>Miejsce na reklame</p>
	</div>
	
	<div id="zawartosc">
		<div align="center">
			<% if(request.getAttribute("Invalid")!=null || request.getMethod() == "GET"){
			%>
			<form:form method="post" commandName="User">
				<table border="0">
	                <tr>
	                    <td colspan="2" align="center"><h2>Rejestracja</h2></td>
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
			<%} %>
			
			
		
		</div>
		
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
	
</div>
</body>

</html>