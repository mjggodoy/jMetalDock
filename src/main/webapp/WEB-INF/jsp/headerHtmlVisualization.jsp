<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="jMetalDock is a web-service oriented to provide users multi- and mono-objective approaches to solve the molecular docking problem">
<meta name="author" content="Khaos Group. Language and Computing Science Department. University of Malaga">
<link rel="shortcut icon" href="/images/liferay.ico?v=2" type="image/x-icon">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<title>jMetalDock: A web-service that provides single and multi-objetive approaches to solve the molecular docking problem</title>

<!-- Bootstrap core CSS -->
<link href='<c:url value="/resources/css/bootstrap.min.css" />' rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
 

<!-- Custom styles for this template -->
<!-- <link href="resources/css/bootstrap.css" rel="stylesheet"> -->
<link href='<c:url value="/css/style.css" />' rel="stylesheet">

<script
	src='<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>'></script>

<script src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script src='<c:url value="/resources/js/bootstrap.js" />'></script>

 <style>
    body {
      font-family: Helvetica;
      background-color: #fff;/*#f0f0f0;*/
      font-weight: lighter;
      margin: 0px;
      width:100%;
      height:100%x;
    }
    a {
      color:#393;
    }
    #gl {
      position:fixed;
      bottom:0px;
      top:0px;
      left:0px;
      right:0px;
    }
    #inspector {
      top:10px;
      left:10px;
      box-shadow: 2px 2px 5px #888888;
      border-radius:8px;
      position:absolute;
      background-color:#fafafa;
      padding:10px;
      border-style:solid;
      border-width:1px;
      border-color:#ccc;
    }

    #inspector ul {
      padding:0px;
    }

    #inspector ul li {
      margin-left:5px;
      margin-right:5px;
      margin-bottom:5px;
      list-style:none;
      cursor: pointer;
      color:#393
    }

    #inspector ul li:hover {
      color:#994444;
    }
    #inspector h1 {
      font-weight:normal;
      font-size:12pt;
    }
    </style>
