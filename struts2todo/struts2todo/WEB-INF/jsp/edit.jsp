<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>作業更新</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
</head>
<body>
	<h1>作業更新</h1>
	<hr>
	<div align="center">
		<s:form action="edit_action">
			<s:hidden name="item_id" />
			<s:textfield name="name" size="24" label="項目名" />
			<s:select name="user_id" size="1" label="担当者" list="users"
				listValue="name" listKey="id" />
			<s:textfield name="year" size="8" label="期限(年)" />
			<s:textfield name="month" size="4" label="期限(月)" />
			<s:textfield name="day" size="4" label="期限(日)" />
			<s:checkbox name="finished" label="完了した" />
			<s:submit value="更新" />
			<s:submit action="list" value="キャンセル" />
		</s:form>
	</div>
</body>
</html>