<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="span9">
   
	<div class="support-page well">
	   <h5><security:authentication property="principal.name"/>! <spring:message code="common.yourDetails"/></h5>
	   <hr>
	   <div class="clearfix"></div>
	     <ul id="slist">
	         <li>
	            <a href="#"><spring:message code="user.email"/></a>
	            <p><security:authentication property="principal.email"/></p>
	         </li>         
	         <li>
	            <a href="#"><spring:message code="user.name"/></a>
	            <p><security:authentication property="principal.name"/></p>
	         </li>
	         <li>
	            <a href="#"><spring:message code="user.surname"/></a>
	            <p><security:authentication property="principal.surname"/></p>
	         </li>         
	         <li>
	            <a href="#"><spring:message code="user.address"/></a>
	            <p><security:authentication property="principal.address"/></p>
	         </li>
	         <security:authorize access="hasRole('seller')">
	         <li>
	            <a href="#"><spring:message code="seller.p_iva"/></a>
	            <p><security:authentication property="principal.p_Iva"/></p>
	         </li>
			 </security:authorize>                    
	    </ul>
	</div>
</div>