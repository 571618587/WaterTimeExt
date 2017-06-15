<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--
    samson
-->
<!DOCTYPE >
<html>
<head>
<%@ include file="/resources/public/header.jspf"%>
<link href="${wt}/resources/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${wt}/resources/js/login.js"></script>
<script type="text/javascript">
	if (top.location != this.location) {
		top.location = this.location;
	}
</script>
<body class="login">
	<c:if test="${!empty sessionScope.sysUser}">
		<c:redirect url="/send_index.jhtml"></c:redirect>
	</c:if>
	<div class="login_m">
		<div class="login_logo">
			<img src="${wt}/resources/img/logo.png">
		</div>
		<div class="login_boder">
			<form id="ff" method="post">
				<div class="login_padding" id="login_model">
					<h2>USERNAME</h2>
					<label> <input type="text" placeholder="请输入用户名" id="account" value="" name="account" class="txt_input txt_input2">
					</label>
					<h2>PASSWORD</h2>
					<label> <input type="password" placeholder="请输入密码" name="password" value="" id="password" class="txt_input">
					</label>
					<p class="forgot"></p>
					<div class="rem_sub">
						<label id="button"> </label>
					</div>
				</div>
			</form>
		</div>
		<!--login_boder end-->
	</div>
	<!--login_m end-->
	<br>
	<br>

</body>
</html>
