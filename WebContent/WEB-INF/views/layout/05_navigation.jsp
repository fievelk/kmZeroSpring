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
                      	<li><a href="${pageContext.request.contextPath}/products/create_start.do">Aggiungi Prodotto</a></li>
                        <li><a href="${pageContext.request.contextPath}/products/views.do">Lista Prodotti Vista User</a></li>
                        <li><a href="${pageContext.request.contextPath}/products/viewsforsellers.do">Lista Prodotti Vista Seller</a></li>
                      </ul>
                   </li>                   
                   <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">Utenti<b class="caret"></b></a>
                      <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/users/views.do">Lista Utenti</a></li>
						<li><a href="${pageContext.request.contextPath}/users/create_start.do">Registrati</a></li>
						<li><a href="${pageContext.request.contextPath}/sellers/viewsToEnable.do">Lista Venditori da Abilitare</a></li>
						<li><a href="${pageContext.request.contextPath}/sellers/viewsEnabled.do">Lista Venditori Abilitati</a></li>
					    <li><a href="${pageContext.request.contextPath}/sellers/create_start.do">Registrati come Venditore</a></li>
					    <li><a href="${pageContext.request.contextPath}/sellers/upgrade_start.do">Upgrade a Venditore</a></li>
                      </ul>
                   </li>      
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
 
