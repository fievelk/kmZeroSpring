<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#user').dataTable({
			"bProcessing": true,
			"bJQueryUI": true,
			"bServerSide": true,
			"sPaginationType": "full_numbers",
			"sAjaxDataProp": "rows",
			"aoColumns":[
						{"mData":"id"},
		                {"mData":"name"},
		                {"mData":"surname"},
		                {"mData":"p_iva"},
		                {"mData":"company"},
		                {"mData":"phone", "sDefaultContent": ""},		                
		                { "sName": "name",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a href='${pageContext.request.contextPath}/sellers/admin/update_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-pencil'></span></a>" + 
		                       		  "<a href='${pageContext.request.contextPath}/sellers/admin/delete_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-circle-close'></span></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/sellers/admin/viewAllSellersToEnablePaginated.do",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},        
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>
<!-- My Account -->


<!-- Main content -->
      <div class="span9">

          <!-- <h5 class="title">My Account</h5> -->

          <h5 class="title"><spring:message code="seller.toenable"/></h5>
          	<div class="row-fluid">
				<a class="btn" href="${pageContext.request.contextPath}/sellers/create_start.do"><spring:message code="seller.create"/></a>
			</div>
				<table id="user" class="table table-striped tcart">
				    <thead>
				        <tr>
				        	<th><spring:message code="user.id"/></th>
				            <th><spring:message code="user.name"/></th>
				            <th><spring:message code="user.surname"/></th>
				            <th><spring:message code="seller.p_iva"/></th>
				            <th><spring:message code="seller.company"/></th>
				            <th><spring:message code="seller.phone"/></th>
				            <th><spring:message code="common.actions"/></th>
					        </tr>
					    </thead>
					<tbody>
				    </tbody>
				</table>
			</div>
