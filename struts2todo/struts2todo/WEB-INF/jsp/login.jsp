<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>ログイン</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
<script type="text/javascript" src="login.js">
        </script>
</head>
<body onload="fieldChanged();">
	<h1>ログイン</h1>
	<hr>
	<div align="center">
		<s:form action="login_action">
			<s:textfield name="user_id" value="" size="24" id="user_id"
				label="ユーザID" onkeyup="fieldChanged();" onchange="fieldChanged();" />
			<s:password name="password" value="" size="24" id="password"
				label="パスワード" onkeyup="fieldChanged();" onchange="fieldChanged();" />
			<s:submit value="ログイン" id="login" align="center" />
		</s:form>
	</div>
</body>
</html>