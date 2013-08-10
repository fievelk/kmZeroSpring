<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="span9">
	<div class="row">
		<div class="span9 gmap">
		    <!-- Google Maps. Replace the below iframe with your Google Maps embed code -->
		    <iframe height="200" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.co.in/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=Google+India+Bangalore,+Bennigana+Halli,+Bangalore,+Karnataka&amp;aq=0&amp;oq=google+&amp;sll=9.930582,78.12303&amp;sspn=0.192085,0.308647&amp;ie=UTF8&amp;hq=Google&amp;hnear=Bennigana+Halli,+Bangalore,+Bengaluru+Urban,+Karnataka&amp;t=m&amp;ll=12.993518,77.660294&amp;spn=0.012545,0.036006&amp;z=15&amp;output=embed"></iframe>
		 </div>
		<c:set var="alternate" value="1"/>
		<c:forEach var="content" items="${seller.contents}">
			<c:choose>
			    <c:when test="${alternate == 1}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span6">
							${content.description}
						</div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
			       <c:set var="alternate" value="0"/>
			    </c:when>
			    <c:when test="${alternate == 0}">
						<div class="span9"><h4 class="title">${content.title}</h4></div>
						<div class="span3">
							<img src="${pageContext.request.contextPath}/selr_content/image/${content.image.id}/${content.image.name}" alt="${content.image.name}" />
						</div>
						<div class="span6" >
							${content.description}
						</div>
			        <c:set var="alternate" value="1"/>
			    </c:when>
			</c:choose>
		</c:forEach>
	</div>
</div>