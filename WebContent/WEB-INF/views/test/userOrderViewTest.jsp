<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="${pageContext.request.contextPath}/resources/custom/js/jquery.raty.js"></script>

<!-- rating script -->
<script>
$(document).ready(function () {
		$('#star').raty({ 
			path: '${pageContext.request.contextPath}/resources/custom/img/rating',
			number: 5,
			hints: ['1', '2', '3', '4', '5'],
			// evento onclick. Al suo posto mettere un metodo ajax tipo setRating e poi bloccare le stelline:
			click: function(score) {
			   alert('ID: ' + $(this).attr('id') + "\nscore: " + score);}
			
			});
		});

</script>
<!-- end of rating script -->

<!-- Main content -->
      <div class="span9">
      	<h5 class="title">TITOLO I TUOI CARRELLI</h5>
		<div class="row-fluid">
			In questa pagina potrai valutare i prodotti acquistati
		</div>

			<table class="table table-striped tcart">
			    <thead>
			    	<tr>
					    <th>cartLine.id</th>
					    <th>cartLine.product</th>
					    <th>Stelline per il voto con jQuery</th>
			    	</tr>
			    </thead>
			    <tbody>	
<%-- 				<c:forEach items="${requestScope.categories}" var="category"> --%>
				<tr>
<%-- 					<td>${cartLine.id}</td> --%>
<%-- 					<td>${cartLine.product}</td> --%>
					<td>cartLine.id</td>
					<td>cartLine.product</td>
					<td>
						<div id="star"></div>




					</td>
				</tr>
<%-- 				</c:forEach> --%>
				</tbody>
			</table>
		
      </div>

