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
			<c:if test="${root !=true }">
				<form action="Up" method="get">
					<input type="hidden" name="type" value="DirView">
					<input type="hidden" name="currDir" value="${currDir}">
					<input type="hidden" name="dirType" value="${dirType}">
					<input type="hidden" name="Login" value="${user.getLogin()}">
					<input type="submit" value="Up"/>
				</form>
			</c:if>
			<hr>
			<table>
					<!-- Zmiany niepewne brak uploadu plików do testów -->
					<c:forEach items="${files}" var="file">
					<tr><td><a href="/dysk/user/${user.getLogin()}/${dirType}?currDir=${currDir}&nextDir=${file.getNazwa()}&fileId=${file.getId()}">
					<c:if test="${file.isFolder() == false}"> 
						<img src="${pageContext.request.contextPath}/resources/Images/file_icon.png"/> ${file.getNazwa()}</a>
						<br/>
					</c:if>
					<c:if test="${file.isFolder() == true}"> 
						<img src="${pageContext.request.contextPath}/resources/Images/folder_icon.png"/> ${file.getNazwa()}</a>
						<br/>
					</c:if>
					</td>
					</tr>
				 	</c:forEach>
				 
			</table>
		<c:if test="${owner == true && root !=true }">			
			<form method="POST" action="addFile" enctype="multipart/form-data">
				<table>
					<input type="hidden" name="currDir" value="${currDir}">
					<input type="hidden" name="dirType" value="${dirType}">
					<tr> 
			        	<td>Plik do dodania:</td> <td><input type="file" name="file"></td><br />
			        </tr>
			       	<tr>
			        	<td></td><td><input type="submit" value="Upload"> Dodaj plik</td>
			        </tr>
		        </table>
		   	</form>
	   
		    <form method="POST" action="addDir">
		    	<input type="hidden" name="currDir" value="${currDir}">
				<input type="hidden" name="dirType" value="${dirType}">
				Katalog do dodania:<input type="text" name="dirName"><br />
				<input type="submit" value="Upload"> Dodaj katalog
		    </form>
	    </c:if>
		</div>	
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
</div>
</body>

</html>