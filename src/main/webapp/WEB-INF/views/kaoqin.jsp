<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>考勤查询</title>

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-modal.css">

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<body>
	<br>
	<br>

	<div align="center">
		<p><strong>一键倒休：</strong>9点前提交当天9点-10点倒休</p>
		<form method="post" class="form-inline" action="daoxiu">
			 <input type="text" id="username" class="form-control "
				placeholder="OA用户名" size="8%"> <input type="password"
				id="password" class="form-control " placeholder="OA密码" size="8%"><br>
			<br>
		</form>
		<button class="btn btn-success" id="daoxiu" value="daoxiu">一键倒休</button>
		&nbsp;
		<button class="btn btn- btn-primary" id="chexiao" value="chexiao">撤销倒休</button>
		<br><br>
		<a href="bindPhone"><strong>听说你习惯性忘打卡？</strong></a>
		<br><br>
	</div>
	<div align="center">
		<form method="post" class="form-inline form-search" id="kaoqinForm"action="${pageContext.request.contextPath}/getData" onsubmit="return submitForm()">
			 <input type="text" name="name" class="form-control "
				placeholder="输入姓名或工号" size="13%"> &nbsp; <input
				type="submit" class="btn btn-success" value="考勤查询">
		</form>
	</div>

	<br>
	<c:if test="${!empty requestScope.name}">

		<div align="center">
			<font color="#3a3d59">当前记录：<strong>${requestScope.name}</strong>&nbsp;&nbsp;共<strong>${fn:length(requestScope.data)}</strong>天
			</font>
		</div>
	</c:if>

	<table class="table table-hover table-responsive" align="center">
		<tr align="center">
			<th align="center">日 期</th>
			<th align="center">星期</th>
			<th align="center">上班时间</th>
			<th align="center">下班时间</th>
			<th align="center">状态</th>
		</tr>
		<tbody>
			<c:forEach var="entity" items="${requestScope.data}">

				<tr
					<c:choose>
					    <c:when test="${entity.status =='正常'}">
					       class="success"
					    </c:when>
					    <c:when test="${entity.status =='迟到'}">
					       class="danger"
					    </c:when>
                        <c:when test="${entity.status =='旷工'}">
                            class="danger"
                        </c:when>
					    <c:when test="${entity.status =='早退'}">
					       class="warning"
					    </c:when>
					</c:choose>
					align="left">
					<td>${ entity.date}</td>
					<td>${ entity.day}</td>
					<td>${ entity.startTime}</td>
					<td>${ entity.endTime}</td>
					<td>${ entity.status}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div
		style="text-align: center; padding: 8px; background-color: #dddde0;">
		<font color="#4f5151">数据来源于</font> <a
			href="http://kq.channelsoft.com:49527" target="black"><font
			color="#3e47f8"><strong>云考勤系统</strong></font> </a> <br>
	</div>
	
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		  >
		<div class="modal-dialog" style="width:40%;">
			<div class="modal-content ">
				<div class="modal-header center">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title blue" id="myModalLabel">信息提示</h4>
				</div>
				<div class="modal-body " align="center">
					<h3 id="modal-body">test</h3>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal">知道了</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var flag=false;
		jQuery('#daoxiu').click(function() {
			if(flag){
				return;
			}
			flag=true;
			var username = $('#username').val();
			var password = $('#password').val();
			if (username == '' || password == '') {
				flag=false;
				$("#modal-body").html("OA用户名或密码不能为空");
				$("#myModal").modal('show');
				return;
			}
			$.ajax({
				url: '${pageContext.request.contextPath}/daoxiu',
				data: {
					username: $('#username').val(),
					password: $('#password').val(),
					action: 'daoxiu'
				},
				type: 'post',
				success: function (data) {
					flag = false;
					$("#modal-body").html(data.message);
					$("#myModal").modal('show');
				},
				error: function () {
					flag = false;
				}
		});
		});

		jQuery('#chexiao').click(function() {
			if(flag){
				return;
			}
			flag=true;
			var username = $('#username').val();
			var password = $('#password').val();
			if (username == '' || password == '') {
				flag=false;
				$("#modal-body").html("OA用户名或密码不能为空");
				$("#myModal").modal('show');
				return;
			}
			$.ajax({
				url : '${pageContext.request.contextPath}/daoxiu',
				data : {
					username : $('#username').val(),
					password : $('#password').val(),
					action : 'chexiao'
				},
				type : 'post',
				success : function(data) {
					$("#modal-body").html(data.message);
					$("#myModal").modal('show');
					flag=false;
				},
				error : function() {
					flag=false;
				}
			});
		});
		
		
		function submitForm(){
			if(flag){
				return false;
			}
			 flag=true;
			 return true;
		}
	</script>

</body>

</html>