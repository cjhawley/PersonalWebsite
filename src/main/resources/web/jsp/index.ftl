<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  charset=UTF-8">
      <title>Chris Hawley</title>
      <meta name="description" content="">
      <meta name="viewport" content="width=device-width">

      <link rel="stylesheet" href="/css/normalize.css">
      <link rel="stylesheet" href="/css/main.css">
      <link rel="stylesheet" href="/css/font-awesome/css/font-awesome.css">

      <script src="/js/vendor/modernizr-2.6.2.min.js"></script>

    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade your browser</a> or <a href="https://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

        <div style="margin-left:12%; margin-top:10%">
        <div style="margin-bottom:20px; width: 100%; position: absolute;">
          <div style="float:left;">
            <h1 style="display: inline; margin-right: 10px; margin-top: 5px;">Chris Hawley</h1>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="mailto:chris@chrishawley.io" style="text-decoration:none; color:black;">
                <i class="icon-envelope-alt icon-black"></i></a>
            </span>
            <span class="icon-stack">
              <i class="icon-check-empty icon-stack-base"></i>
              <a class="btn btn-small" href="https://www.linkedin.com/pub/christopher-hawley/6b/23a/2/en" target="_blank" style="text-decoration:none; color:black;"><i class="icon-linkedin"></i></a>
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
          <p>Hi, I'm Chris. (<a href="/resume/resume.html" style="text-decoration: underline; color: black;">View my resume</a>)</p>
          <p>I'm a software engineer at <a href="https://meta.com" style="text-decoration: underline; color: black;">Meta</a>. I'm primarily interested in topics related to distributed systems, infrastructure, and ads.</p>
        </div>

        <div style="width:75%;">
          <div style="margin-right:5%; width: 40%; float:left;">
            <p><h3>Recent News</h3></p>
            <div style="background-color:white; margin-bottom:5px; border-radius:2px;">
                <#list personal_events as personal_event>
                <hr>
                <p class="news-header">${personal_event.date()?string["yyyy.MM.dd"]} - ${personal_event.title()}</p>
                <p class="news">${personal_event.description()}</p>
                </#list>
            </div>
          </div>
          <div style="width: 50%; float:right;">
            <p><h3>Publications</h3></p>
            <div style="background-color:white; margin-bottom:5px; border-radius:2px;">
              <h4 style="margin:0px 0px 0px 2px;">Heliometric Stereo: Shape from Sun Position</h4>
              <h6 style="margin:0px 0px 5px 10px;">A. Abrams, C. Hawley, and R. Pless. Heliometric Stereo: Shape From Sun Position. In <i>Proc. European Conference on Computer Vision, October 2012.</i></h6>
              <button id="helio_abstract_header" onClick="toggleAbstract(this.id, 'helio_abstract')" style="margin:0; border:0; background:none; border:none; display:inline;">Abstract [-]</button>
              <h5 style="display:inline;"><a id="helio_download_header" href="/content/eccv2012.pdf" style="color:#222222;margin:0px 0px 0px 10px; text-decoration:none; background:none; border:none; display:inline;">Download PDF</a></h5>
              <hr style="margin:2px 0px 2px 0x;">
              <div id="helio_abstract" style="margin:2px 0px 0px 10px;">
                <p style="margin: 0px; font-size:10pt"><i>In this work, we present a method to uncover shape from webcams "in the wild." We present a variant of photometric stereo which uses the sun as a distant light source, so that lighting direction can be computed from known GPS and timestamps. We propose an iterative, non-linear optimization process that optimizes the error in reproducing all images from an extended time-lapse with an image formation model that accounts for ambient lighting, shadows, changing light color, dense surface normal maps, radiometric calibration, and exposure. Unlike many approaches to uncalibrated outdoor image analysis, this procedure is automatic, and we report quantitative results by comparing extracted surface normals to Google Earth 3D models. We evaluate this procedure on data from a varied set of scenes and emphasize the advantages of including imagery from many months.</i></p>
              </div>
            </div>
          </div>
        </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="/js/vendor/jquery-3.2.1.min.js"><\/script>')</script>
        <script src="/js/plugins.js"></script>
        <script src="/js/main.js"></script>
        <script>
          toggleAbstract('helio_abstract_header', 'helio_abstract');
        </script>
    </body>
</html>
