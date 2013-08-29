<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- Footer starts -->
<footer>
  <div class="container">
    <div class="row">
      <div class="span12">

            <div class="row">

              <div class="span4">
                <div class="widget">
                  <h5><spring:message code="common.contacts" /></h5>
                  <hr />
                    <div class="social">
                      <a href="#"><i class="icon-facebook facebook"></i></a>
                      <a href="#"><i class="icon-twitter twitter"></i></a>
                      <a href="#"><i class="icon-linkedin linkedin"></i></a>
                      <a href="#"><i class="icon-google-plus google-plus"></i></a> 
                    </div>
                  <hr />
                  <i class="icon-home"></i> &nbsp; Indirizzo Wharehouse
                  <hr />
                  <i class="icon-phone"></i> &nbsp; +39 0862802190
                  <hr />
                  <i class="icon-envelope-alt"></i> &nbsp; <a href="mailto:#">info@kilometrozero.com</a>
                  <hr />
                  <div class="payment-icons">
                    <img src="${pageContext.request.contextPath}/resources/mackart/img/payment/americanexpress.gif" alt="" />
                    <img src="${pageContext.request.contextPath}/resources/mackart/img/payment/visa.gif" alt="" />
                    <img src="${pageContext.request.contextPath}/resources/mackart/img/payment/mastercard.gif" alt="" />
                    <img src="${pageContext.request.contextPath}/resources/mackart/img/payment/discover.gif" alt="" />
                    <img src="${pageContext.request.contextPath}/resources/mackart/img/payment/paypal.gif" alt="" />
                  </div>
                </div>
              </div>

              <div class="span4">
                <div class="widget">
                  <h5>Latest Tweets</h5>
                  <hr />
                    <!-- Below line is produce acutal output -->
                    <div class="tweet"></div>
                </div>
              </div>

              <div class="span4">
                <div class="widget">
                  <h5>Links Goes Here</h5>
                  <hr />
                  <div class="two-col">
                    <div class="col-left">
                      <ul>
                        <li><a href="#">Condimentum</a></li>
                        <li><a href="#">Etiam at</a></li>
                        <li><a href="#">Fusce vel</a></li>
                        <li><a href="#">Vivamus</a></li>
                        <li><a href="#">Pellentesque</a></li>
                        <li><a href="#">Vivamus</a></li>
                      </ul>
                    </div>
                    <div class="col-right">
                      <ul>
                        <li><a href="#">Condimentum</a></li>
                        <li><a href="#">Etiam at</a></li>
                        <li><a href="#">Fusce vel</a></li>
                        <li><a href="#">Vivamus</a></li>
                        <li><a href="#">Pellentesque</a></li>
                        <li><a href="#">Vivamus</a></li>
                      </ul>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </div>
              
            </div>

            <hr />
            <!-- Copyright info -->
            <p class="copy">Copyright &copy; 2012 | <a href="#">www.kilometrozero.com</a> - <a href="#">Home</a> | <a href="#">Prodotti</a> | <a href="#">Venditori</a> | <a href="#">Contact Us</a></p>
      </div>
    </div>
  <div class="clearfix"></div>
  </div>
</footer> 	

<!-- Footer ends -->