<%@ page import="java.util.*" import="java.io.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
<!--  <script type="text/javascript">
	function DoAjax(e)
	{
		var data = $("#tresc1").val();
		alert("In func");
		$.ajax({
			type: "POST",
			url: "/dysk/user/AddComment",
			dataType : 'json',
	        data : JSON.stringify(data),
			success: function(res)
			{
				alert(res);
			}
		})
	}
	</script>
	
-->
<!-- <script type="text/javascript">
	$(document).ready(function() 
	{
		$("#form1").submit(function(e){
			var data = $("#form1").serialize();
			e.preventDefault();
			var tworca =  $("#tworca").val();
			var file =   $("#file").val();
			var tresc =   $("#tresc").val();
			var test = [{"Tworca":tworca,"Przypisany":file,"Tresc":tresc}]
			
			$.ajax({
				type:"POST",
				url:"/dysk/user/AddComment",
				dataType: "json",
				data: data,
				contentType: 'application/json',
				success: function(res)
				{
					alert("Ok");
				}
			})
		})
		
	})
	
	
</script>	
	
-->

<div id="strona">

	<div id="naglowek">
		<img id="LogoDIV" src="${pageContext.request.contextPath}/resources/Images/file-server-300px.png" alt="Logo">
	</div>
	
	<jsp:include page="/WEB-INF/views/menu1.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/menu2.jsp"></jsp:include>
	
	
	<div id="zawartosc">	
		<div align="center">
			
			<form action="Up" method="get">
				<input type="hidden" name="type" value="FileView">
				<input type="hidden" name="currDir" value="${currDir}">
				<input type="hidden" name="dirType" value="${dirType}">
				<input type="hidden" name="Login" value="${user.getLogin()}">
				<input type="submit" value="Up"/>
			</form>
			
			<hr>
			<table>	
				<tr>
				<td>
					<form action="downloadFile" method="post">				
						<input type="hidden" name="fileName" value="${file.getNazwa()}">
						<input type="hidden" name="currDir" value="${currDir}">
						<input type="hidden" name="dirType" value="${dirType}">
						<input type="submit" value="Pobierz">
					</form>
				</td>
				<% String dirType = (String)request.getAttribute("dirType");
				if(dirType.equals("private")) { %>	
				<td>
					<form action="publishFile" method="post">
						<input type="hidden" name="fileId" value="${file.getId()}">				
						<input type="hidden" name="fileName" value="${file.getNazwa()}">
						<input type="hidden" name="currDir" value="${currDir}">
						<input type="hidden" name="dirType" value="${dirType}">
						<input type="submit" value="Opublikuj">
					</form>
				</td>
				<% }
				%>	
				<c:if test="${IsOwner == true }">
					<td>
						<form action="deleteFile" method="post">				
							<input type="hidden" name="fileId" value="${file.getId()}">
							<input type="hidden" name="currDir" value="${currDir}">
							<input type="hidden" name="dirType" value="${dirType}">
							<input type="submit" value="Usun">
						</form>
					</td>
				</c:if>
				</tr>
			</table>
			<form:form action="updateFile" method="post" commandName="file">				
				<input type="hidden" name="currDir" value="${currDir}">
				<input type="hidden" name="dirType" value="${dirType}">				
				<form:input type="hidden" path="Id" value="${file.getId()}"/>
				<form:input type="hidden" path="Lokalizacja" value="${file.getLokalizacja()}"/>
				<table border="1">
					<tr>
						<th>Nazwa</th>
						<td>
							<c:if test="${IsOwner == true }">
								<form:input path="Nazwa" />
							</c:if>
							<c:if test="${IsOwner == false }">
								<form:input path="Nazwa" disabled="true"/>
							</c:if>
						</td>
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
						<td>
							<c:if test="${IsOwner == true }">
								<form:textarea path="Opis"  />
							</c:if>
							<c:if test="${IsOwner == false }">
								<form:textarea path="Opis" disabled="true" />
							</c:if>
						</td>
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
				<c:if test="${IsOwner == true }">
					<input type="submit" value="Edytuj">
				</c:if>
			</form:form>			
			<br>
			<c:forEach items="${comments }" var="Kom">
			<fieldset>
				<span><b>Dodal:</b> ${UserIdMap.get(Kom.getTworca())}</span><br>
				<span><b>Data Dodania:</b> ${Kom.getDate() }</span><br>
				<span><b>Tresc:</b> ${Kom.getTresc() }</span><br>
				<c:if test="${IsOwner == true || p == UserIdMap.get(Kom.getTworca()) }">
					<form:form action="/dysk/user/DelComment" mehod="POST">
						<input type="hidden" name="curDir" value="${currDir }"/>	
						<input type="hidden" name="login" value="${Login }"/>
						<input type="hidden" name="file" value="${file.getId() }"/>
						<input type="hidden" name="commentId" value="${Kom.getId() }"/>
						<input type="submit" value="Usun" />
					</form:form>
				</c:if>
			</fieldset>
			<br>
			</c:forEach>
			<c:if test="${IsOwner == false }">
				<fieldset>
					<form:form action="/dysk/user/AddComment" method="POST" id="form1"  >	
						<input type="hidden" name="curDir" value="${currDir }"/>	
						<input type="hidden" name="login" value="${Login }"/>				
						<input name="tworca" type="hidden" value="${p }"/>							
						<p>Dodaj Nowy komentaz</p>
						<span>Tresc</span><input name="tresc" type="text" /><br>
						<input name="file" type="hidden" value="${file.getId() }"/>						
						<input type="submit" value="Dodaj"/>
					</form:form>
				</fieldset>
				
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/stopka.jsp"></jsp:include>
</div>
</body>

</html>