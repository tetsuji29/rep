<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>作業一覧</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
</head>
<body>
	<h1>作業一覧</h1>
	<hr>
	<div align="right">
		ようこそ
		<s:property value="currentUser.name" />
		さん
	</div>
	<!-- 作業登録・検索 -->
	<table border="0" width="90%" class="toolbar">
		<tr>
			<td><s:form action="add">
					<s:submit value="作業登録" />
				</s:form></td>
			<td align="right">
				<table border="0">
					<tr>
						<td><s:form action="search">
								<s:textfield name="keyword" value="" size="24" label="検索キーワード" />
								<s:submit value="検索" />
							</s:form></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<s:if test="items.length == 0">
		<div align="center">作業項目はありません。</div>
	</s:if>
	<s:else>
		<table border="0" width="90%" class="list">
			<tr>
				<th>項目名</th>
				<th>担当者</th>
				<th>期限</th>
				<th>完了</th>
				<th colspan="3">操作</th>
			</tr>
			<s:iterator value="items">
				<s:if test="finishedDate != null">
					<s:set var="tdStyle" value="'background-color: #cccccc;'" />
				</s:if>
				<s:elseif test="user.id.equals(currentUser.id)">
					<s:set var="tdStyle" value="'background-color: #ffbbbb;'" />
				</s:elseif>
				<s:else>
					<s:set var="tdStyle" value="''" />
				</s:else>
				<s:if
					test="expireDate.getTime() &lt; currentTime &amp;&amp; finishedDate == null">
					<s:set var="divStyle" value="'color: #ff0000;'" />
				</s:if>
				<s:else>
					<s:set var="divStyle" value="''" />
				</s:else>
				<tr>
					<td style="" class="list">
						<div style="">
							<s:property value="name" />
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:property value="user.name" />
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:property value="expireDate" />
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:if test="finishedDate != null">
								<s:property value="finishedDate" />
							</s:if>
							<s:else>未</s:else>
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:form action="finish">
								<s:hidden name="item_id" value="%{id}" />
								<s:if test="finishedDate != null">
									<s:submit value="未完了" />
								</s:if>
								<s:else>
									<s:submit value="完了" />
								</s:else>
							</s:form>
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:form action="edit">
								<s:hidden name="item_id" value="%{id}" />
								<s:submit value="更新" />
							</s:form>
						</div>
					</td>
					<td style="" class="list">
						<div style="">
							<s:form action="delete">
								<s:hidden name="item_id" value="%{id}" />
								<s:submit value="削除" />
							</s:form>
						</div>
					</td>
				</tr>
			</s:iterator>
		</table>
	</s:else>
</body>
</html>