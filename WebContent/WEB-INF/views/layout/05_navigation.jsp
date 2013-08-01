<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Navigation -->
          <div class="navbar">
           <div class="navbar-inner">
             <div class="container">
               <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
               </a>
               <div class="nav-collapse collapse">
                 <ul class="nav">
                   <li><a href="index.html"><i class="icon-home"></i></a></li>
                   <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">Prodotti<b class="caret"></b></a>
                      <ul class="dropdown-menu">
                      	<li><a href="${pageContext.request.contextPath}/products/views">Lista Prodotti Vista User</a></li>
                        
                      		<security:authorize access="hasRole('seller')">
                      	<li><a href="${pageContext.request.contextPath}/products/create_start">Aggiungi Prodotto</a></li>
                        <li><a href="${pageContext.request.contextPath}/products/viewsforsellers">Lista Prodotti Vista Seller</a></li>
                        	</security:authorize>
                        	<security:authorize access="hasRole('seller')"> <!-- Cambiare in "admin" -->
                        <li><a href="${pageContext.request.contextPath}/products/createCategory_start">Aggiungi Categoria</a></li>
                        <li><a href="${pageContext.request.contextPath}/products/viewsCategories">Lista Categorie vista Admin</a></li>
                      		</security:authorize>
                      </ul>
                   </li>                   
                   <%-- <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">Utenti<b class="caret"></b></a>
                      <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/users/views.do">Lista Utenti</a></li>
						<li><a href="${pageContext.request.contextPath}/users/create_start.do">Registrati</a></li>
						<li><a href="${pageContext.request.contextPath}/sellers/viewsToEnable.do">Lista Venditori da Abilitare</a></li>
						<li><a href="${pageContext.request.contextPath}/sellers/viewsEnabled.do">Lista Venditori Abilitati</a></li>
					    <li><a href="${pageContext.request.contextPath}/sellers/create_start.do">Registrati come Venditore</a></li>
					    <li><a href="${pageContext.request.contextPath}/sellers/upgrade_start.do">Upgrade a Venditore</a></li>
                      </ul>
                   </li>    --%>
                   
                   <security:authorize access="isAuthenticated()">
                	   <li class="dropdown">
	                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">Utenti<b class="caret"></b></a>
	                      <ul class="dropdown-menu">
	                            
	                   <security:authorize access="hasRole('admin')">            
                       <li><a href="${pageContext.request.contextPath}/users/admin/views.do">Lista Utenti</a></li>
					   <li><a href="${pageContext.request.contextPath}/sellers/admin/viewsToEnable.do">Lista Venditori da Abilitare</a></li>
					   <li><a href="${pageContext.request.contextPath}/sellers/admin/viewsEnabled.do">Lista Venditori Abilitati</a></li>
				   	   <li><a href="${pageContext.request.contextPath}/users/edit_start_password.do">Modifica la password</a></li> 
	                   </security:authorize>
	                   <security:authorize access="hasRole('user')">            
                      	<li><a href="${pageContext.request.contextPath}/users/edit_start_password.do">Modifica la password</a></li>
                      	<li><a href="${pageContext.request.contextPath}/users/update_start.do">Modifica i tuoi dati</a></li>
					    <li><a href="${pageContext.request.contextPath}/sellers/upgrade_start.do">Upgrade a Venditore</a></li>   
	                   </security:authorize>
	                   <security:authorize access="hasRole('seller')">            
		                <li><a href="${pageContext.request.contextPath}/sellers/update_start.do">Modifica i tuoi dati</a></li>   
	                   </security:authorize>
	                   </ul>
	                 </li>  
	                 <security:authorize access="hasRole('seller')">            
		                <li><a href="${pageContext.request.contextPath}/sellers/content_start.do"><spring:message code="seller.content"/></a></li>   
	                 </security:authorize>
                   </security:authorize>
                   <li><a href="${pageContext.request.contextPath}/sellers/list.do">Venditori</a></li>
                   
                   <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">TESTS<b class="caret"></b></a>
                      <ul class="dropdown-menu">

                        <li><a href="${pageContext.request.contextPath}/test/testNumberOne">Test #1</a></li>
						<li><a href="${pageContext.request.contextPath}/test/testNumberTwo">Test #2</a></li>
						<li><a href="${pageContext.request.contextPath}/test/testNumberThree">Test #3</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/testNumberFour">Test #4</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/test_user_start">Test User</a></li>
					    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      </ul>
                   </li>                                
                 </ul>
               </div>
              </div>
           </div>
         </div>
<!-- Navigation ends--> 
 
