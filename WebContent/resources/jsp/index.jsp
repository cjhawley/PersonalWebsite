<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="rbt" uri="urn:org:glassfish:jersey:servlet:mvc" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <title>Chris Hawley</title>
      <meta name="description" content="">
      <meta name="viewport" content="width=device-width">

      <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

      <link rel="stylesheet" href="resources/css/normalize.css">
      <link rel="stylesheet" href="resources/css/main.css">
      <link rel="stylesheet" href="resources/css/font-awesome/css/font-awesome.css">

      <script src="resources/js/vendor/modernizr-2.6.2.min.js"></script>

    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <!-- Add your site or application content here -->
        <div style="margin-left:12%; margin-top:10%">
        <div style="margin-bottom:20px; width: 100%; position: absolute;">
          <div style="float:left;">
            <h1 style="display: inline; margin-right: 10px; margin-top: 5px;">Chris Hawley</h1>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="mailto:cjhawley001@gmail.com" style="text-decoration:none; color:black;">
                <i class="icon-envelope-alt icon-black"></i></a>
            </span>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="www.linkedin.com/pub/christopher-hawley/6b/23a/2/en" target="_blank" style="text-decoration:none; color:black;"><i class="icon-linkedin"></i></a>
            </span>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="https://github.com/cjhawley" target="_blank" style="text-decoration:none; color:black;"><i class="icon-github"></i></a>
            </span>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="https://www.facebook.com/chris.hawley.33" target="_blank" style="text-decoration:none; color:black;"><i class="icon-facebook"></i></a>
            </span>
          </div>
        </div>
  
        <div style="float:left; margin-top: 40px; width:75%">
          <p>Hi, I'm Chris. (<a href="resources/resume/resume.html" style="text-decoration: underline; color: black;">View my résumé</a>)</p>
          <p>I develop software for A9.com (an Amazon company). I am a full stack engineer, primarily building software for backend systems, but am also interested in many Computer Vision topics, such as photometric stereo, radiometric calibration, and shadow detection and estimation.</p>
        </div>

        <div style="width:75%;">
          <div style="margin-right:5%; width: 40%; float:left;">
            <p><h3>Recent News</h3></p>
            <div style="background-color:white; margin-bottom:5px; border-radius:2px;">
               <c:forEach var="personal_event" items="${it.personal_events}">
                 <hr>
                 <p class="news-header"><fmt:formatDate value="${personal_event.date}" pattern="yyyy.MM.dd" /> - ${personal_event.title }</p>
                 <p class="news">${personal_event.description}"</p>
   			   </c:forEach>
            </div>
          </div>
          <div style="width: 50%; float:right;">
            <p><h3>Publications</h3></p>
            <div style="background-color:white; margin-bottom:5px; border-radius:2px;">
              <h4 style="margin:0px 0px 0px 2px;">Heliometric Stereo: Shape from Sun Position</h4>
              <h6 style="margin:0px 0px 5px 10px;">A. Abrams, C. Hawley, and R. Pless. Heliometric Stereo: Shape From Sun Position. In <i>Proc. European Conference on Computer Vision, October 2012.</i></h6>
              <button id="helio_abstract_header" onClick="toggleAbstract(this.id, 'helio_abstract')" style="margin:0; border:0; background:none; border:none; display:inline;">Abstract [-]</button>
              <h5 style="display:inline;"><a id="helio_download_header" href="resources/content/eccv2012.pdf" style="color:#222222;margin:0px 0px 0px 10px; text-decoration:none; background:none; border:none; display:inline;">Download PDF</a></h5>
              <hr style="margin:2px 0px 2px 0x;">
              <div id="helio_abstract" style="margin:2px 0px 0px 10px;">
                <p style="margin: 0px; font-size:10pt"><i>In this work, we present a method to uncover shape from webcams "in the wild." We present a variant of photometric stereo which uses the sun as a distant light source, so that lighting direction can be computed from known GPS and timestamps. We propose an iterative, non-linear optimization process that optimizes the error in reproducing all images from an extended time-lapse with an image formation model that accounts for ambient lighting, shadows, changing light color, dense surface normal maps, radiometric calibration, and exposure. Unlike many approaches to uncalibrated outdoor image analysis, this procedure is automatic, and we report quantitative results by comparing extracted surface normals to Google Earth 3D models. We evaluate this procedure on data from a varied set of scenes and emphasize the advantages of including imagery from many months.</i></p>
              </div>
            </div>
          </div>
        </div>
        </div>

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
        <!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>-->
        <script>window.jQuery || document.write('<script src="resources/js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
        <script src="resources/js/plugins.js"></script>
        <script src="resources/js/main.js"></script>
        <script>
          toggleAbstract('helio_abstract_header', 'helio_abstract');
        </script>
        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <!--<script>
            var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src='//www.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>-->
    </body>
</html>
