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
		                       return "<a class='btn-small' href='${pageContext.request.contextPath}/sellers/admin/update_start?id=" + oObj.aData['id'] + "'><span class='icon-edit'></span></a>" + 
		                       		  "<a class='btn-small' href='${pageContext.request.contextPath}/sellers/admin/delete_start?id=" + oObj.aData['id'] + "'><span class='icon-trash'></span></a>" +
		                       		  "<a class='btn-small' href='${pageContext.request.contextPath}/sellers/pagecontents?id=" + oObj.aData['id'] + "'><span class='icon-file'></span></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/sellers/admin/viewAllSellersEnabledPaginated",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},        
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>
<!-- My Account -->

<!-- Main content -->
      <div class="span9">

          <!-- <h5 class="title">My Account</h5> -->

          <h5 class="title"><spring:message code="seller.enabled"/></h5>
          	<div class="row-fluid">
				<a class="btn" href="${pageContext.request.contextPath}/sellers/create_start"><spring:message code="seller.create"/></a>
			</div>
				<table id="datatables" class="table table-striped tcart">
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
