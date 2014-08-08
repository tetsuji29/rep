<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>削除確認</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
</head>
<body>
	<h1>削除確認</h1>
	<hr>
	<div align="center">
		<table border="0">
			<tr>
				<td class="add_field">項目 <s:property value="item.name" />
					を削除します。<br> よろしいですか？
				</td>
			</tr>
			<tr>
				<td class="add_button"><s:set var="item_id" value="item.id" />
					<s:form action="delete_action">
						<s:hidden name="item_id" />
						<s:submit value="削除" />
					</s:form> <s:form action="list">
						<s:submit value="キャンセル" />
					</s:form></td>
			</tr>
		</table>
	</div>
</body>
</html>
