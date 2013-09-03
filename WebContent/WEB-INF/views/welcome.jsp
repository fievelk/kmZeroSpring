<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<script>
$(document).ready(function() {
	$("p").each(function () { $(this).show(); });	
});
</script>

<div class="span9">
   
	<div class="support-page well">
	   <h5><spring:message code="common.yourDetails"/></h5>
	   <hr>
	   <div class="clearfix"></div>
	     <ul id="slist">
	         <li>
	            <a href="#"><spring:message code="user.email"/></a>
	            <p>${user.email}</p>
	         </li>         
	         <li>
	            <a href="#"><spring:message code="user.name"/></a>
	            <p>${user.name}</p>
	         </li>
	         <li>
	            <a href="#"><spring:message code="user.surname"/></a>
	            <p>${user.surname}</p>
	         </li>         
	         <li>
	            <a href="#"><spring:message code="user.address"/></a>
	            <p>${user.address}</p>
	         </li>
	         <security:authorize access="hasRole('seller')">
	         <li>
	            <a href="#"><spring:message code="seller.p_iva"/></a>
	            <p>${user.p_iva}</p>
	         </li>
			 </security:authorize>                    
	    </ul>
	</div>
</div>