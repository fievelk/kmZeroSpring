<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
  <!-- Login Modal starts -->
<div id="login" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
    <h4>Login</h4>
  </div>
  <div class="modal-body">
  	<iframe width="400" height="200" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.it/maps/ms?msa=0&amp;msid=208912251465314021596.0004e003d684b9d193c72&amp;hl=it&amp;ie=UTF8&amp;t=m&amp;ll=41.442726,12.392578&amp;spn=3.294118,8.789063&amp;z=6&amp;output=embed"></iframe>              <div class="form">
		<security:authorize access="!isAuthenticated()">
	         <form name="loginform" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" class="form-horizontal">
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
                       <button type="reset" class="btn">Reset</button>
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