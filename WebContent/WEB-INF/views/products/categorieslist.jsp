<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(document).ready(function(){
 	  alert("Attenzione: Cancellare soltanto categorie alle quali non sono stati ancora associati prodotti! \n(Altrimenti presumibilmente sfancula tutto)");
});
</script>

<div class="items">
  <div class="container">
    <div class="row">

      <div class="span3 side-menu">

        <!-- Sidebar navigation -->
        <h5 class="title">Menu</h5>
        <!-- Sidebar navigation -->
          <nav>
            <ul id="navi">
              <li><a href="myaccount.html">Gestione Ordini</a></li>
              <li><a href="wish-list.html">Storico Ordini</a></li>
              <li><a href="order-history.html">Gestione Utenti</a></li>
              <li><a href="edit-profile.html">Gestione Venditori</a></li>
            </ul>
          </nav>

      </div>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="category.view"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/products/createCategory_start.do"><spring:message code="category.create"/></a>
		</div>

<table class="table table-striped tcart">
    <thead>
    	<tr>
		    <th><spring:message code="category.id"/></th>
		    <th><spring:message code="category.name"/></th>
		    <th><spring:message code="category.parent"/></th>
		    <th><spring:message code="common.actions"/></th>
    	</tr>
    </thead>
    <tbody>	
	<c:forEach items="${requestScope.categories}" var="category">
	<tr>
		<td>${category.id}</td>
		<td>${category.name}</td>
		<td>${category.parent_id}</td>
		<td>
			<a href="${pageContext.request.contextPath}/products/updateCategory_start.do?id=${category.id}"><i class='icon-edit'></i></a> | 
			<a id="deletelink" href="${pageContext.request.contextPath}/products/deleteCategory_start.do?id=${category.id}"><i class='icon-trash'></i></a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
		
      </div>
    </div>
  </div>
</div>
