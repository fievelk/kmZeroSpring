<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="warehouse.view"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/sellers/admin/updateWarehouse_start?id=${warehouse.id}"><spring:message code="warehouse.update"/></a>
		</div>

			<table class="table table-striped tcart">
			    <thead>
			    	<tr>
					    <th><spring:message code="warehouse.id"/></th>
					    <th><spring:message code="warehouse.name"/></th>
					    <th><spring:message code="warehouse.address"/></th>
					    <th><spring:message code="common.actions"/></th>
			    	</tr>
			    </thead>
			    <tbody>	
				<tr>
					<td>${warehouse.id}</td>
					<td>${warehouse.name}</td>
					<td>${warehouse.address}</td>
					<td>
						<a href="${pageContext.request.contextPath}/sellers/admin/updateWarehouse_start?id=${warehouse.id}"><i class='icon-edit'></i></a>
					</td>
				</tr>
				</tbody>
			</table>
		
      </div>

