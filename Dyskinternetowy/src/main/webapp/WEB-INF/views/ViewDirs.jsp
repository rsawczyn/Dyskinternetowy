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
			<table>
				<tr>
					<c:forEach items="${files}" var="file">
					<td><a href="/dysk/user/${user.getLogin()}/${dirType}?currDir=${currDir}&nextDir=${file.getNazwa()}&fileId=${file.getId()}">${file.getNazwa()}</a></td>
				 	</c:forEach>
				 </tr>
			</table>
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
		</div>	
	</div>
	<div id="stopka">
	stopka
	</div>
</div>
</body>

</html>