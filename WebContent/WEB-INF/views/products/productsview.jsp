<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	getProducts();
});

function getProducts(){
	url = "${pageContext.request.contextPath}/products/viewProducts2";
	$.ajax({
		type: "POST",
		url: url,
		data:
		success: function(data) {
					console.log(data);
				}
	});		
	return false;
}

</script>

<!-- Items - START -->

<div class="items">
  <div class="container">
    <div class="row">

      <!-- Sidebar -->
      <div class="span3 hidden-phone">

        <h5 class="title">Categories</h5>
        <!-- Sidebar navigation -->
          <nav>
            <ul id="nav">
              <!-- Main menu. Use the class "has_sub" to "li" tag if it has submenu. -->
              <li><a href="index.html">Home</a></li>
              <li class="has_sub"><a href="#">Smartphone</a>
                <!-- Submenu -->
                <ul>
                              <li><a href="items.html">Category 1</a></li>
                              <li><a href="items.html">Category 2</a></li>        
                </ul>
              </li>
            </ul>
          </nav>
<br />
          <!-- Sidebar items (featured items)-->

          <div class="sidebar-items">

            <h5 class="title">Featured Items</h5>

            <!-- Item #1 -->
            <div class="sitem">
              <!-- Don't forget the class "onethree-left" and "onethree-right" -->
              <div class="onethree-left">
                <!-- Image -->
                <a href="single-item.html"><img src="img/photos/2.png" alt="" /></a>
              </div>
              <div class="onethree-right">
                <!-- Title -->
                <a href="single-item.html">HTC One V</a>
                <!-- Para -->
                <p>Aenean ullamcorper justo tincidunt justo aliquet.</p>
                <!-- Price -->
                <p class="bold">$199</p>
              </div>
              <div class="clearfix"></div>
            </div>                  
          </div>

      </div>


<!-- Main content -->
      <div class="span9">

        <!-- Breadcrumb -->
        <ul class="breadcrumb">
          <li><a href="index.html">Home</a> <span class="divider">/</span></li>
          <li><a href="items.html">Smartphone</a> <span class="divider">/</span></li>
          <li class="active">Apple</li>
        </ul>

                            <!-- Title -->
                              <h4 class="pull-left">Apple iPhones</h4>


                                          <!-- Sorting -->
                                            <div class="controls pull-right">                               
                                                <select>
                                                <option>Sort By</option>
                                                <option>Name (A-Z)</option>
                                                <option>Name (Z-A></option>
                                                <option>Price (Low-High)</option>
                                                <option>Price (High-Low)</option>
                                                <option>Ratings</option>
                                                </select>  
                                            </div>

                          <div class="clearfix"></div>

              <div class="row">

                <!-- Item #1 -->
                <div class="span3">
                  <!-- Each item should be enclosed in "item" class -->
                  <div class="item">
                    <!-- Item image -->
                    <div class="item-image">
                      <a href="single-item.html"><img src="img/photos/2.png" alt="" /></a>
                    </div>
                    <!-- Item details -->
                    <div class="item-details">
                      <!-- Name -->
                      <!-- Use the span tag with the class "ico" and icon link (hot, sale, deal, new) -->
                      <h5><a href="single-item.html">HTC One V</a><span class="ico"><img src="img/hot.png" alt="" /></span></h5>
                      <div class="clearfix"></div>
                      <!-- Para. Note more than 2 lines. -->
                      <p>Something about the product goes here. Not More than 2 lines.</p>
                      <hr />
                      <!-- Price -->
                      <div class="item-price pull-left">$360</div>
                      <!-- Add to cart -->
                      <div class="button pull-right"><a href="#">Add to Cart</a></div>
                      <div class="clearfix"></div>
                    </div>
                  </div>
                </div>


                <div class="span3">
                  <div class="item">
                    <!-- Item image -->
                    <div class="item-image">
                      <a href="single-item.html"><img src="img/photos/9.png" alt="" /></a>
                    </div>
                    <!-- Item details -->
                    <div class="item-details">
                      <!-- Name -->
                      <h5><a href="single-item.html">Sony One V</a></h5>
                      <!-- Para. Note more than 2 lines. -->
                      <p>Something about the product goes here. Not More than 2 lines.</p>
                      <hr />
                      <!-- Price -->
                      <div class="item-price pull-left">$100</div>
                      <!-- Add to cart -->
                      <div class="button pull-right"><a href="#">Add to Cart</a></div>
                      <div class="clearfix"></div>
                    </div>
                  </div>
                </div>  


                <div class="span9">
                                    <!-- Pagination -->
                                    <div class="paging">
                                       <span class='current'>1</span>
                                       <a href='#'>2</a>
                                       <span class="dots">&hellip;</span>
                                       <a href='#'>6</a>
                                       <a href="#">Next</a>
                                    </div>
                </div>           
              </div>
            </div>                                                                    
    </div>
  </div>
</div>
<!-- Items  - END-->