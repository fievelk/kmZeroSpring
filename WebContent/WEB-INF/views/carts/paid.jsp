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
					<label class="control-label" for="name"><b>Hai completato il pagamento</b></label>
					<br>
					<label id="idtx"></label>
					<label id="emailtx"></label>
					<label class="control-label" for="name">Grazie per aver effettuato il pagamento. <br>
					La transazione è stata completata e una ricevuta dell'acquisto è stata inviata <br> all'indirizzo email che hai utilizzato su PayPal.</label>
					<a href="https://www.sandbox.paypal.com/it/cgi-bin/merchantpaymentweb?cmd=_account">Vai a Informazioni generali sul conto</a>
				</div>
			</div>
		</div>
	</div>
</div>