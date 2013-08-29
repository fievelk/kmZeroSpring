<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		var sellerId = '${sellerId}';
		
		$('#datatables').dataTable({
			"bProcessing": true,
			"bJQueryUI": true,
			"bServerSide": true,
			"sAjaxDataProp": "rows",
			"aoColumns":[
		                {"mData":"id"}, // contenuto dei products restituiti dal metodo viewProducts 
		                {"mData":"title"},
		                {"mData":"description"},
		                { "sName": "id",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a class='btn-small' href='${pageContext.request.contextPath}/sellers/updatepagecontent_start?id=" + oObj.aData['id'] + "&sellerId="+sellerId+"'><i class='icon-edit'></i></a>" +  
		                       		  "<a class='btn-small' href='${pageContext.request.contextPath}/sellers/deletepagecontent_start?id=" + oObj.aData['id'] + "&sellerId="+sellerId+"'><i class='icon-trash'></i></a>";
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/sellers/viewPageContentsPaginated?sellerId="+sellerId,
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},
            "fnServerParams": addsortparams
		});
	    
		
	});
	

</script>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="sellercontent.view"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/sellers/createpagecontent_start?sellerId=${sellerId}"><spring:message code="sellercontent.create"/></a>
		</div>
		<table id="datatables" class="table table-striped tcart">
			<thead>
			    <tr>
				    <th><spring:message code="sellercontent.id"/></th>
				    <th><spring:message code="sellercontent.title"/></th>
				    <th><spring:message code="sellercontent.description"/></th>
		   	   	    <th><spring:message code="common.actions"/></th>
			    </tr>
		    </thead>
		    <tbody>
		    </tbody>
		</table>
      </div>

