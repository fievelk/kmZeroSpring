<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

$(document).ready(function(){
	var tx = getUrlVars()["tx"];
	$('#idtx').replaceWith('<label id="idtx" class="control-label" for="name">Il tuo ID transazione per questo pagamento è: <b>' + tx + '</b></label>');
});

</script>

<div class="items">
	<div class="container">
		<div class="row">
			<div class="span9">
			
				<div class="control-group">
					<label class="control-label" for="name"><b><spring:message code="cart.completedPayment" />.</b></label>
					<br>
					<label id="idtx"></label>
					<label id="emailtx"></label>
					<label class="control-label" for="name"><spring:message code="cart.thankYouForOrder" />. <br>
					<spring:message code="cart.transactionMessage" /></label>
					<a href="https://www.sandbox.paypal.com/it/cgi-bin/merchantpaymentweb?cmd=_account"><spring:message code="cart.accountInformations" /></a>
				</div>
			</div>
		</div>
	</div>
</div>