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
		<table>
			<c:forEach items="${users }" var="UsersList">
				<tr>
					
					<td><p>Uzytkownik ${UsersList.getLogin()}</p></td>
					<td><a href="/dysk/user/${UsersList.getLogin()}/public?currDir=${UsersList.getLogin()}/&nextDir=public&fileId=${usersFiles.get(UsersList.getLogin())[1]}">Wejdz do Katalogu Publicznego</a>
					
				</tr>				
			</c:forEach>
		</table>
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
	
</div>
</body>

</html>
