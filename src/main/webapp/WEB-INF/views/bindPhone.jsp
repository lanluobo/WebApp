<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2016/11/29
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>下班打卡提醒</title>

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/vue.1.0.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-modal.css">
    <style>
        .wrap{
            width: 450px;
            height: 400px;
            /*border: 1px solid gray;*/
            text-align: center;
            margin: auto;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 100px;
        }
    </style>
</head>
<body>

<div class="container-fluid wrap" id="bindPhone">

    <div class="">
        <h2 style="padding-bottom: 40px;color:#287dd3;">下班打卡提醒服务</h2>
        <p style="color:rgba(84, 104, 179, 0.99)">该功能会在18:10、18:30、20:45检测未打卡用户</p>
        <p style="color: rgba(84, 104, 179, 0.99)">系统会给未打卡用户发送一条信息到绑定的手机号</p>
        <br>
    </div>

    <form class="form-horizontal">

        <div class="form-group">
            <label class="control-label col-sm-3 col-md-3">手机号:</label>
            <div class="col-sm-9 col-md-9">
                <input type="text" class="form-control" v-model="phoneNum" placeholder="请输入手机号码"/>
            </div>
        </div>
        <br>
        <div class="form-group">
            <label class="control-label col-sm-3 col-md-3">工号:</label>
            <div class="col-sm-9 col-md-9">
                <input type="text" class="form-control" v-model="workNum" placeholder="请输入4位工号"/>
            </div>
        </div>
        <br>
        <div class="form-group">
            <label class="control-label col-sm-3 col-md-3">验证码:</label>
            <div class="col-sm-5 col-md-5">
                <input type="text" class="form-control" v-model="verificationNum"placeholder="请输入验证码" />
            </div>
            <div class="col-sm-4 col-md-4">
                <button type="button" class="btn btn-success" @click="getVerificationNum">获取验证码</button>
            </div>
        </div>
        <br>
    </form>
    <button type="button" class="btn btn-success" style="margin-right: 40px" @click="bindPhone">&nbsp;&nbsp;开&nbsp;&nbsp;通&nbsp;&nbsp;服&nbsp;&nbsp;务&nbsp;&nbsp;</button>
    <button type="button" class="btn btn-primary" @click="unbindPhone">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp闭&nbsp;&nbsp;服&nbsp;&nbsp;务&nbsp;&nbsp;</button>
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
<script>
    var bindPhone = new Vue({
        el:"#bindPhone",
        data:{
            phoneNum:"",
            workNum:"",
            verificationNum:"",
            tipMessage:""
        },
        methods:{
            bindPhone: function(){
                var self =this;
                if(self.phoneNum == ""){
                    $("#modal-body").html("手机号不能为空！");
                    $("#myModal").modal('show');
                    return;
                }else if(!/^[1][0-9]{10}$/.test(self.phoneNum)){
                    $("#modal-body").html("请输入正确的手机号！");
                    $("#myModal").modal('show');
                    return;
                }
                if(self.workNum == ""){

                    $("#modal-body").html("工号不能为空！");
                    $("#myModal").modal('show');
                    return;
                }else if(!/^[0-9]{4}$/.test(self.workNum)){

                    $("#modal-body").html("请输入正确的工号！");
                    $("#myModal").modal('show');
                    return;
                }
                if(self.verificationNum == ""){

                    $("#modal-body").html("请输入验证码！");
                    $("#myModal").modal('show');
                    return;
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/bind',
                    data: {
                        phone: self.phoneNum,
                        employeeId: self.workNum,
                        code: self.verificationNum
                    },
                    type: 'post',
                    success: function (data) {
                        $("#modal-body").html(data.message);
                        $("#myModal").modal('show');
                    }
                })
            },
            unbindPhone: function(){
                var self =this;
                if(self.phoneNum == ""){
                    $("#modal-body").html("手机号不能为空！");
                    $("#myModal").modal('show');
                    return;
                }else if(!/^[1][0-9]{10}$/.test(self.phoneNum)){
                    $("#modal-body").html("请输入正确的手机号！");
                    $("#myModal").modal('show');
                    return;
                }
                if(self.workNum == ""){

                    $("#modal-body").html("工号不能为空！");
                    $("#myModal").modal('show');
                    return;
                }else if(!/^[0-9]{4}$/.test(self.workNum)){

                    $("#modal-body").html("请输入正确的工号！");
                    $("#myModal").modal('show');
                    return;
                }
                if(self.verificationNum == ""){

                    $("#modal-body").html("请输入验证码！");
                    $("#myModal").modal('show');
                    return;
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/unbind',
                    data: {
                        phone: self.phoneNum,
                        employeeId: self.workNum,
                        code: self.verificationNum
                    },
                    type: 'post',
                    success: function (data) {
                        $("#modal-body").html(data.message);
                        $("#myModal").modal('show');
                    }
                })
            },
            getVerificationNum: function(){
                var self = this;
                if(self.phoneNum == ""){
                    $("#modal-body").html("手机号不能为空！");
                    $("#myModal").modal('show');
                    return;
                }else if(!/^[1][0-9]{10}$/.test(self.phoneNum)){
                    $("#modal-body").html("请输入正确的手机号！");
                    $("#myModal").modal('show');
                    return;
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/sendCode',
                    data: {
                        phone: self.phoneNum
                    },
                    type: 'post',
                    success: function (data) {
                        $("#modal-body").html(data.message);
                        $("#myModal").modal('show');
                    }
                })
            }
        }
    });

</script>
</body>
</html>
