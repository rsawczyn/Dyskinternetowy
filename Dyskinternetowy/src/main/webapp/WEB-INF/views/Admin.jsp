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
		<img id="LogoDIV" src="${pageContext.request.contextPath}/resources/Images/file-server-300px.png" alt="Logo">
	</div>
	
	<jsp:include page="/WEB-INF/views/menu1.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/menu2.jsp"></jsp:include>
	
	<div id="zawartosc">
		<center>	
			<table border="1" >
				<tr>
					<td><p>Imie: ${Imie }</p></td>
				</tr>
				<tr>
					<td><p>Nazwisko : ${Nazwisko}</p></td>
				</tr>
				<tr>
					<td><p>Login: ${Login }</p></td>
				</tr>
				<tr>
					<td>
						Haslo:
						<form action="/dysk/user/changepass" method="POST">
							<input name="pass" type="password" value="${Haslo }"/>
							<input type="submit" value="Zmien"/>
						</form>			
					</td>
				</tr>
				<tr>
					<td><p>Email: ${Email }</p></td>
				</tr>
			</table>
		</center>	
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
</div>
</body>

</html>