<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		                {"mData":"date_of_birth"},
		                {"mData":"address"},
		                {"mData":"email"},
		                {"mData":"created"},
		                {"mData":"last_access", "sDefaultContent": ""},		                
		                { "sName": "name",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a href='${pageContext.request.contextPath}/users/admin/update_start?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-pencil'></span></a>" +  
		                       		  "<a href='${pageContext.request.contextPath}/users/admin/delete_start?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-circle-close'></span></a>" + 
		                       		  "<a href='${pageContext.request.contextPath}/users/edit_start_password?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-locked'></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/users/admin/viewAllUsersPaginated",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},        
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>
<!-- My Account -->



<!-- Main content -->
      <div class="span9">

          <h5 class="title"><spring:message code="user.views"/></h5>
          	<div class="row-fluid">
				<a class="btn" href="${pageContext.request.contextPath}/users/create_start"><spring:message code="user.create"/></a>
			</div>

            <table id="datatables" class="table table-striped tcart">
              <thead>
                <tr>
                  <th><spring:message code="user.id"/></th>
		            <th><spring:message code="user.name"/></th>
		            <th><spring:message code="user.surname"/></th>
		            <th><spring:message code="user.date_of_birth"/></th>
		            <th><spring:message code="user.address"/></th>
		            <th><spring:message code="user.email"/></th>
		            <th><spring:message code="user.created"/></th>
		            <th><spring:message code="user.last_access"/></th>
		            <th><spring:message code="common.actions"/></th>
                </tr>
              </thead>
              <tbody>                                              
              </tbody>
            </table>

      </div>                                                                    

