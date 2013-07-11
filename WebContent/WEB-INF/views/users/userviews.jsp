<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		                       return "<a href='${pageContext.request.contextPath}/users/update_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-pencil'></span></a>" +  
		                       		  "<a href='${pageContext.request.contextPath}/users/delete_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-circle-close'></span></a>" + 
		                       		  "<a href='${pageContext.request.contextPath}/users/edit_start_password.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-locked'></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/users/viewAllUsersPaginated.do",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},        
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>
<!-- My Account -->

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

          <!-- <h5 class="title">My Account</h5> -->

          <h5 class="title"><spring:message code="user.views"/></h5>
          	<div class="row-fluid">
				<a class="btn" href="${pageContext.request.contextPath}/users/create_start.do"><spring:message code="user.add"/></a>
			</div>

            <table id="user" class="table table-striped tcart">
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



    </div>
  </div>
</div>



















<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		                {"mData":"date_of_birth"},
		                {"mData":"address"},
		                {"mData":"email"},
		                {"mData":"created"},
		                {"mData":"last_access", "sDefaultContent": ""},		                
		                { "sName": "id",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a href='${pageContext.request.contextPath}/users/update_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-pencil'></span></a>" +  
		                       		  "<a href='${pageContext.request.contextPath}/users/delete_start.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-circle-close'></span></a>" + 
		                       		  "<a href='${pageContext.request.contextPath}/users/edit_start_password.do?id=" + oObj.aData['id'] + "'><span class='ui-icon ui-icon-locked'></a>";
		                    	
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/users/viewAllUsersPaginated",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},        
            "fnServerParams": addsortparams
		});
	    
		
	});
</script>
<!-- My Account -->

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

          <!-- <h5 class="title">My Account</h5> -->

          <h5 class="title"><spring:message code="user.views"/></h5>
          	<div class="row-fluid">
				<a class="btn" href="${pageContext.request.contextPath}/users/create_start.do"><spring:message code="user.add"/></a>
			</div>

            <table id="user" class="table table-striped tcart">
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



    </div>
  </div>
</div> --%>

