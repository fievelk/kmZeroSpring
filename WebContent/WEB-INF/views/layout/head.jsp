<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Header starts -->
  <header>
    <div class="container">
      <div class="row">

        <div class="span4">
          
          <div class="logo row">     	
          	<div class="span2" id="logo"><img src="${pageContext.request.contextPath}/resources/custom/img/healty1.png" /></div>
           	<div class="span2" id="logotext">
	            <h1><a href="#">Kilometro<span class="color bold">Zero</span></a></h1>
	            <p class="meta">Il mercato dei prodotti biologici vicino casa tua!</p>
            </div>
          </div>
         <!-- Logo  <div class="logo">
            <h1><a href="#">Kilometro<span class="color bold">Zero</span></a></h1>
            <p class="meta">Se non ci dovesse andare bene con il master la zappa Ë sempre un'ottima alternativa!</p>
          </div>-->
        </div>

        <div class="span4 offset4">
          
          <!-- Search form -->
          <form class="form-search">
            <div class="input-append">
              <input class="span3" id="appendedInputButton" type="text" placeholder="Cerca tra i prodotti...">
              <button class="btn" type="button"><spring:message code="menu.search" /></button>
            </div>
          </form>

          <div class="hlinks">
            <span>

              <!-- item details with price -->
              <a id="modalC" href="#modalCart" role="button" data-toggle="modal" onclick="createModalCart()">0 Item(s) in your <i class="icon-shopping-cart"></i></a> -<span class="bold">$25</span>   

            </span>

			
			<!-- Login and Register link -->
              
              	<security:authorize access="!isAuthenticated()">
              	<span class="lr">
              		<a href="#login" role="button" data-toggle="modal">Login</a> 
              		<!-- or <a href="#register" role="button" data-toggle="modal">Register</a> -->
				</span>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
				<span class="lr">
					<span><a href="${pageContext.request.contextPath}/welcome"><security:authentication property="principal.name"/></a> |</span>
					<a href="${pageContext.request.contextPath}/j_spring_security_logout" role="button" data-toggle="modal">Logout</a>
				</span>	
				</security:authorize>
            
              
          
          </div>

        </div>

      </div>
    </div>
  </header>
  <!-- Header ends -->