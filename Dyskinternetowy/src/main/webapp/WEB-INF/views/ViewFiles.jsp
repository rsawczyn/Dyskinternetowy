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
			<a href="/dysk/exit" >Wyloguj</a>
			<hr>		
			<form action="deleteFile" method="post">				
				<input type="hidden" name="fileId" value="${file.getId()}">
				<input type="hidden" name="currDir" value="${currDir}">
				<input type="hidden" name="dirType" value="${dirType}">
				<input type="submit" value="Usun">
			</form>
			<form:form action="updateFile" method="post" commandName="file">				
				<input type="hidden" name="currDir" value="${currDir}">
				<input type="hidden" name="dirType" value="${dirType}">
				<form:input type="hidden" path="Id" value="${file.getId()}"/>
				<form:input type="hidden" path="Lokalizacja" value="${file.getLokalizacja()}"/>
			<table border="1">
				<tr>
					<th>Nazwa</th>
					<td><form:input path="Nazwa" /></td>
				</tr>
				<tr>
					<th>Format</th>
					<td>${file.getFormat()}<!-- <form:input path="Format" />--></td>
				 </tr>
				 <tr>
					<th>Lokalizacja</th>
					<td>${file.getLokalizacja()}<!-- <form:input path="Lokalizacja" /> --></td>
				 </tr>
				 <tr>
					<th>Opis</th>
					<td><form:input path="Opis"/></td>
				 </tr>
				 <tr>
					<th>Rozmiar</th>
					<td>${file.getRozmiar()}<!-- <form:input path="Rozmiar" />--></td>
				 </tr>
				 <tr>
					<th>Data Dodania</th>
					<td>${file.getDataDodania()}<!-- <form:input path="DataDodania" /> --></td>
				 </tr>				 
			</table>
			<input type="submit" value="Edytuj">
			</form:form>
		</div>
	</div>
	<div id="stopka">
	stopka
	</div>
</div>
</body>

</html>