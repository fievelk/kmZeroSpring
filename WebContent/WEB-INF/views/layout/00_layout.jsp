<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<tiles:importAttribute scope="request"/>
<!DOCTYPE html>
<html lang="it">
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>TITOLO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>
	
	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/bootstrap.css" rel="stylesheet">
	<!-- Pretty Photo -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/prettyPhoto.css" rel="stylesheet">
	<!-- Flex slider -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/flexslider.css" rel="stylesheet">
	<!-- Sidebar nav -->
	  <link href="${pageContext.request.contextPath}/resources/mackart/style/sidebar-nav.css" rel="stylesheet">
	<!-- Font awesome icon -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/mackart/style/font-awesome.css"> 
	<!-- Main stylesheet -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/style.css" rel="stylesheet">
	<!-- Responsive style (from Bootstrap) -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/bootstrap-responsive.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jquery-ui/css/jquery-ui-1.10.3.custom.min.css" />
	<!-- Stylesheet for Color -->
	<link href="${pageContext.request.contextPath}/resources/mackart/style/kmzero.css" rel="stylesheet">
	<!-- Stylesheet for Color -->
	<link href="${pageContext.request.contextPath}/resources/datatables/css/kmzero_datatables.css" rel="stylesheet">
	
	<!-- HTML5 Support for IE -->
	<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/resources/mackart/js/html5shim.js"></script>
	<![endif]-->
	
	<!-- Favicon -->
	<link rel="shortcut icon" href="img/favicon/favicon.png">
    
	

	<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.9.1.min.js"></script> <!-- jQuery -->
	<script src="${pageContext.request.contextPath}/resources/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
    

    
<!-- Collegamenti per il jQuery datepicker -->    
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> -->

  </head>

  <body>
  
	<tiles:insertAttribute name="header"/>
	
	 <!-- Cart, Login and Register form (Modal) -->
	
	<tiles:insertAttribute name="cartModal"/>
	<tiles:insertAttribute name="loginModal"/>
	<tiles:insertAttribute name="registerModal"/>
	
	<tiles:insertAttribute name="navigation"/>
	<tiles:insertAttribute name="content"/>
    <tiles:insertAttribute name="footer"/>

	<!-- Scroll to top -->
	<span class="totop"><a href="#"><i class="icon-chevron-up"></i></a></span> 
	
	<!-- JS -->

	<script src="${pageContext.request.contextPath}/resources/mackart/js/bootstrap.js"></script> <!-- Bootstrap -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/jquery.prettyPhoto.js"></script> <!-- Pretty Photo -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/filter.js"></script> <!-- Filter for support page -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/jquery.tweet.js"></script> <!-- Twitter -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/jquery.flexslider-min.js"></script> <!-- Flex slider -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/nav.js"></script> <!-- Sidebar navigation -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/jquery.carouFredSel-6.1.0-packed.js"></script> <!-- Carousel for recent posts -->
	<script src="${pageContext.request.contextPath}/resources/mackart/js/custom.js"></script> <!-- Custom codes -->
	
	<script src="${pageContext.request.contextPath}/resources/datatables/js/my.js"></script>
    <script src="${pageContext.request.contextPath}/resources/datatables/js/jquery.dataTables.min.js"></script>
   
    
  </body>
  
</html>