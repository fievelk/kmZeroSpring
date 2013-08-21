<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="measure.view"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/products/createMeasure_start"><spring:message code="measure.create"/></a>
		</div>

<table class="table table-striped tcart">
    <thead>
    	<tr>
		    <th><spring:message code="measure.id"/></th>
		    <th><spring:message code="measure.name"/></th>
		    <th><spring:message code="measure.shortName"/></th>
		    <th><spring:message code="common.actions"/></th>
    	</tr>
    </thead>
    <tbody>	
	<c:forEach items="${requestScope.measures}" var="measure">
	<tr>
		<td>${measure.id}</td>
		<td>${measure.name}</td>
		<td>${measure.shortName}</td>
		<td>
			<a href="${pageContext.request.contextPath}/products/updateMeasure_start?id=${measure.id}"><i class='icon-edit'></i></a> | 
			<a id="deletelink" href="${pageContext.request.contextPath}/products/deleteMeasure_start?id=${measure.id}"><i class='icon-trash'></i></a>
		</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
		
      </div>
