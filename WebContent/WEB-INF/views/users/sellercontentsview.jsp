<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#productsforuser').dataTable({
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
		                       return "<a href='${pageContext.request.contextPath}/sellers/updatepagecontent_start?id=" + oObj.aData['id'] + "'><i class='icon-edit'></i></a>" + " | " + 
		                       		  "<a href='${pageContext.request.contextPath}/sellers/deletepagecontent_start?id=" + oObj.aData['id'] + "'><i class='icon-trash'></i></a>";
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/sellers/viewPageContentsPaginated",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},
            "fnServerParams": addsortparams
		});
	    
		
	});
	

</script>

<!-- Main content -->
      <div class="span9">
      	<h5 class="title"><spring:message code="sellercontent.view"/></h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/sellers/createpagecontent_start"><spring:message code="sellercontent.create"/></a>
		</div>
		<table id="productsforuser" class="table table-striped tcart">
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

