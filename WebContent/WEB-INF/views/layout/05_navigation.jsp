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
                   <li><a href="${pageContext.request.contextPath}"><i class="icon-home"></i></a></li>
                   <li><a href="${pageContext.request.contextPath}/products">Prodotti</a></li>
                   <li><a href="${pageContext.request.contextPath}/sellers">Venditori</a></li>  
                   <security:authorize access="!isAuthenticated()">
                   	<li><a href="${pageContext.request.contextPath}/users/create_start.do">Registrati</a></li>
                   	<li><a href="${pageContext.request.contextPath}/sellers/create_start.do">Registrati Venditore</a></li>
           

                   </security:authorize>      
                   <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">TESTS<b class="caret"></b></a>
                      <ul class="dropdown-menu">

                        <li><a href="${pageContext.request.contextPath}/test/testNumberOne">Rigenera Tutto</a></li>
						<li><a href="${pageContext.request.contextPath}/test/testNumberTwo">Test #2</a></li>
						<li><a href="${pageContext.request.contextPath}/test/testNumberThree">Test #3</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/testNumberFour">Test #4</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/test_user_start">Test User</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/maptest">Google Maps Test</a></li>
					    <li><a href="${pageContext.request.contextPath}/test/cartstodeliver">Carts To Deliver</a></li>
					    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                      </ul>
                   </li>                                
                 </ul>
               </div>
              </div>
           </div>
         </div>
<!-- Navigation ends--> 
 
