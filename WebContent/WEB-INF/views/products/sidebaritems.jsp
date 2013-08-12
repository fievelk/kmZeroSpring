<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

 <!-- Sidebar -->
<div class="span3">
	<h5 class="title">Categories</h5>
	        <!-- Sidebar navigation -->
	        <nav id="categ">
	            <ul id="nav">
	            <li><a id="cat_" href="#"><spring:message code="category.all"/></a></li>
					<tag:categoriesPrinter categoryTree="${categoryTree}"/> 
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
                <a href="single-item.html"><img src="" alt="" /></a>
              </div>
              <div class="onethree-right">
                <!-- Title -->
                <a href="single-item.html">product 1</a>
                <!-- Para -->
                <p>Aenean ullamcorper justo tincidunt justo aliquet.</p>
                <!-- Price -->
                <p class="bold">19 EURO</p>
              </div>
              <div class="clearfix"></div>
            </div>             
          </div>
      </div>
      
      
      
      
      
   