<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
  <!-- Login Modal starts -->
 
<div id="login" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4>Login</h4>
  </div>
  <div class="modal-body">
  	<div class="form">
		<security:authorize access="!isAuthenticated()">
			<form name="addressForm" action="${pageContext.request.contextPath}/" method="post" class="form-horizontal">
	          	    <div class="control-group">
                       <label class="control-label" for="address">Type your address</label>
                       <div class="controls">
                         <input type="text" id="address" class="input-large" placeholder="Address" name="address">
                       </div>
                     </div>
          
                     <div class="form-actions">
                        <!-- Buttons -->
                       <button type="submit" class="btn"><spring:message code="common.signin"/></button>
                       <button type="reset" class="btn" id="reset">Reset</button>
                     </div>
	         </form>
	         <form id="loginform" name="loginform" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="form-horizontal">
	          	    <div class="control-group">
                       <label class="control-label" for="username">Username</label>
                       <div class="controls">
                         <input type="text" id="username" class="input-large" placeholder="Username" name="j_username">
                       </div>
                     </div>
                     <div class="control-group">
                       <label class="control-label" for="email">Password</label>
                       <div class="controls">
                         <input type="password" id="password" class="input-large" placeholder="Password" name="j_password">
                       </div>
                     </div>
                     <div class="form-actions">
                        <!-- Buttons -->
                       <button type="submit" class="btn"><spring:message code="common.signin"/></button>
                       <button type="reset" class="btn" id="reset">Reset</button>
                     </div>
	         </form>
	    </security:authorize>
  </div> 

  </div>
  <div class="modal-footer">
    <p>Dont have account? <a href="register.html">Register</a> here.</p>
  </div>
</div>

<!-- Login modal ends -->