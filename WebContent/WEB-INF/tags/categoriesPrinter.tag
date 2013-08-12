<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@ attribute name="categoryTree" type="java.util.List"%>  

<c:forEach var="category" items="${categoryTree}">
		 	<c:choose>
		 		<c:when test="${!empty category.childs}">
		 			<li class="has_sub"><a id="cat_${category.id}" href="#">${category.name}</a>
		 				<ul>
		 					<tag:categoriesPrinter categoryTree="${category.childs}"/>
		 				</ul>
		 			</li>
		 		</c:when>
		 		<c:otherwise>
		 			<li><a id="cat_${category.id}" href="#">${category.name}</a></li>
		 		</c:otherwise>
		 	</c:choose>	
</c:forEach> 
