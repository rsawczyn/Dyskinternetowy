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
		<div align="center">
			<hr>
			<table>
				<tr>
					<td><a href="/dysk/user/${user.getLogin()}/public?currDir=${user.getLogin()}/&nextDir=public&fileId=${pub.getId()}">
					<img src="${pageContext.request.contextPath}/resources/Images/folder_icon.png"/>${pub.getNazwa()}</a></td>
				</tr>
				<tr>
				    <td><a href="/dysk/user/private?currDir=${user.getLogin()}/&nextDir=private&fileId=${priv.getId()}">
				    <img src="${pageContext.request.contextPath}/resources/Images/folder_icon.png"/>${priv.getNazwa()}</a></td>
			 	</tr>
			</table>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
</div>
</body>

</html>