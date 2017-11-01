﻿<%@page language="java" import="com.ywj.model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	if(request.getAttribute("user") == null){
		String userName = null;
		String password = null;
		
		Cookie[] cookies = request.getCookies();
		for(int i = 0;cookies !=null && i< cookies.length;i++){
			if(cookies[i].getName().equals("user")){
				userName = cookies[i].getValue().split("-")[0];
				password = cookies[i].getValue().split("-")[1];
			}
		}
		
		if(userName == null){
			userName = "";
		}
		
		if(password == null){
			password = "";
		}
		pageContext.setAttribute("user", new User(userName,password));
	}
%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人日记本登录</title>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<style type="text/css">
	  body {
        padding-top: 200px;
        padding-bottom: 40px;
        background-image: url('images/star.gif');
      }
      
      .form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 0px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

</style>
<script type="text/javascript">
	function checkFrom() {
		var userName = document.getElementById("userName").value;
		var password = document.getElementById("password").value;
		if(userName == null || userName == ""){
			document.getElementById("error").innerHTML = "用户名不能为空";
			return false;
		}
		
		if(password == null || password == ""){
			document.getElementById("error").innerHTML = "密码不能为空";
			return false;
		}
		return true;
	}
	
	function Reset() {
		document.getElementById("userName").value = "";
		document.getElementById("password").value = "";
	}
</script>
</head>
<body>
<div class="container">
      <form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkFrom()">
        <h2 class="form-signin-heading">杨文杰的日记本</h2>
        <input id="userName" name="userName"  type="text" class="input-block-level" placeholder="你的登录用户名..." value="${user.userName }">
        <input id="password" name="password"   type="password" class="input-block-level" placeholder="你的登录密码..." value="${user.password }">
        <label class="checkbox">
          <input id="remember" name="remember" type="checkbox" value="remember-me">记住我 &nbsp;&nbsp;&nbsp;&nbsp; <font id="error" color="red">${error }</font>  
        </label>
        <button class="btn btn-primary btn-info" type="submit">登录</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-primary btn-info" type="button" onclick="Reset()">重置</button>

<p align="center" style="padding-top: 15px;">版权所有  2017  ywj所有 <br/></p>
      </form>
</div>
</body>
</html>