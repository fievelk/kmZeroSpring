<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#productsforseller').dataTable({
			"bProcessing": true,
			"bJQueryUI": true,
			"bServerSide": true,
			"sAjaxDataProp": "rows",
			"aoColumns":[
		                {"mData":"oid"}, // contenuto dei products restituiti dal metodo viewProductsBySellerIdPaginated 
		                {"mData":"name"},
		                {"mData":"description"},		
		                {"mData":"price"},
		                {"mData":"category.name"},
		                {"mData":"date_in"},
		                {"mData":"date_out"},
		                { "sName": "id",
		                    "bSearchable": false,
		                    "bSortable": false,
		                    "sDefaultContent": "",
		                    "fnRender": function (oObj) {
		                       return "<a href='${pageContext.request.contextPath}/products/update_start.do?oid=" + oObj.aData['oid'] + "'><i class='icon-edit'></i></a>" + " | " + 
		                       		  "<a href='${pageContext.request.contextPath}/products/delete_start.do?oid=" + oObj.aData['oid'] + "'><i class='icon-trash'></i></a>";
		                     }
		                  }
            ],
            "sAjaxSource": "${pageContext.request.contextPath}/products/viewProductsBySellerIdPaginated.do",
            "oLanguage": {"sUrl": "${pageContext.request.contextPath}/resources/datatables/i18n/italian.properties"},
            "fnServerParams": addsortparams
		});
	    
		
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
      <h5 class="title">BLABLABLA</h5>
		<div class="row-fluid">
			<a class="btn" href="${pageContext.request.contextPath}/products/insert_start.do">Add Product</a>
		</div>


<table id="productsforseller" class="table table-striped tcart">
	<thead>
	    <tr>
		    <th>Id</th>
		    <th>Name</th>
		    <th>Description</th>
		    <th>Price</th>
		    <th>Category</th>
		    <th>Date IN</th>
		    <th>Date OUT</th>
   	   	    <th>update</th>
	    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<!-- Senza DATATABLES -->

<%-- <table class="table table-striped tcart">
	<thead>
	    <tr>
		    <th>Id</th>
		    <th>Name</th>
		    <th>Description</th>
		    <th>Price</th>
		    <th>Category</th>
		    <th>Seller Company</th>
	   	    <th>update</th>
		    <th>Date IN</th>
		    <th>Date OUT</th>
	    </tr>
    </thead>
    <tbody>
	<c:forEach items="${requestScope.products}" var="product">
	<tr>
		<td>${product.oid}</td>
		<td>${product.name}</td>
		<td>${product.description}</td>
		<td>${product.price}</td>
		<td>${product.category.name}</td>
		<td>${product.seller.company}</td>
		<td><a href="${pageContext.request.contextPath}/products/update_start.do?oid=${product.oid}">UPDATAMI</a></td>
		<td><fmt:formatDate pattern="dd-MM-yyyy"  value="${product.date_in.time}" /></td>
		<td><fmt:formatDate pattern="dd-MM-yyyy"  value="${product.date_out.time}" /></td>
		
	</tr>
	</c:forEach>
	</tbody>
</table> --%>

</div>
</div>
</div>
</div>
