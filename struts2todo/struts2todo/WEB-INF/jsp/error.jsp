<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>エラー</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
</head>
<body>
	<h1>エラー</h1>
	<hr>
	<div align="center">
		<table border="0">
			<tr>
				<td class="add_field">エラーが発生しました。<br> 内容: <s:property
						value="errorMessage" />
				</td>
			</tr>
			<tr>
				<td class="add_button"><s:form action="login">
						<s:submit value="戻る" />
					</s:form></td>
			</tr>
		</table>
	</div>
</body>
</html>
