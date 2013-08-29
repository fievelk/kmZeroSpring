<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#datatables').dataTable({
			"bProcessing": true,
			"bJQueryUI": true,
			"bServerSide": true,
			"sPaginationType": "full_numbers",
			"sAjaxDataProp": "rows",
			"aoColumns":[
		                {"mData":"id"}, // contenuto dei products restituiti dal metodo viewProductsBySellerIdPaginated 
		                {"mData":"name"},		
		                {"mData":"price"},
		                {"mData":"category.name"},
		                {"mData":"date_in"},
		                {"mData":"date_out"},
		                {"mData":"position"},
		                {"mData":"seller.company"},
		                { "sName": "id",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a class='btn-small' href='${pageContext.request.contextPath}/products/update_start?id=" + oObj.aData['id'] + "'><i class='icon-edit'></i></a>" +  
		                       		  "<a class='btn-small' href='${pageContext.request.contextPath}/products/delete_start?id=" + oObj.aData['id'] + "'><i class='icon-trash'></i></a>";
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/products/viewProductsBySellerIdPaginated",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="product.viewforseller"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/products/create_start"><spring:message code="product.create"/></a>
		</div>


		<table id="datatables" class="table table-striped tcart">
			<thead>
			    <tr>
				    <th><spring:message code="product.id"/></th>
				    <th><spring:message code="product.name"/></th>
				    <th><spring:message code="product.price"/></th>
				    <th><spring:message code="product.category"/></th>
				    <th><spring:message code="product.date_in"/></th>
				    <th><spring:message code="product.date_out"/></th>
				    <th><spring:message code="product.position"/></th>
				    <th><spring:message code="product.seller"/></th>
		   	   	    <th><spring:message code="common.actions"/></th>
			    </tr>
		    </thead>
		    <tbody>
		    </tbody>
		</table>
		
      </div>

