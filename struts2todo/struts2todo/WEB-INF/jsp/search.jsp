<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>検索結果</title>
<link rel="STYLESHEET" href="todo.css" type="text/css">
</head>
<body>
	<h1>検索結果</h1>
	<hr>
	<s:form action="list">
		<s:submit value="戻る" align="left" />
	</s:form>
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
					test="expireDate.getTime() < currentTime && finishedDate == null">
					<s:set var="divStyle" value="'color: #ff0000;'" />
				</s:if>
				<s:else>
					<s:set var="divStyle" value="''" />
				</s:else>
				<tr>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
							<s:property value="name" />
						</div>
					</td>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
							<s:property value="user.name" />
						</div>
					</td>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
							<s:property value="expireDate" />
						</div>
					</td>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
							<s:if test="finishedDate != null">
								<s:property value="finishedDate" />
							</s:if>
							<s:else>未</s:else>
						</div>
					</td>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
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
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
							<s:form action="edit">
								<s:hidden name="item_id" value="%{id}" />
								<s:submit value="更新" />
							</s:form>
						</div>
					</td>
					<td style="${tdStyle}" class="list">
						<div style="${divStyle}">
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
	<s:form action="list">
		<s:submit value="戻る" align="left" />
	</s:form>
</body>
</html>