var user = {
    //加载头部，左边菜单栏
    _init_ : function() {
        $("#header").load("/student/header.html");
        $("#left").load("/student/left.html");
    },
    //登录初始化事件
    login_init : function(type) {
        $("#username").focus( function(){
            if($("#username").val() != "" && $("#username").val() == "请输入登录名") {
                $("#username").val("");
            }
            $("#usernamError").html("");
        } );
        $("#password").focus( function(){
            if($("#password").val() != "" && $("#password").val() == "请输入密码") {
                $("#password").val("");
            }
            $("#passwordError").html("");
        } ).keydown(function(event){
            //回车
            if(event.keyCode == 13){
                $("#btnLogin").trigger("click");
            }
        });

        $("#btnLogin").click( function(){
            return user.checkForm(type);
        } );
    },
    //用户登录
    checkForm : function() {
		var errFlag = false;
		var username = $("#username").val();
		if( username == null || username == ""  || username == "请输入登录名") {
            $("#usernamError").html("<em class=\"icon-biaozhi \"></em>请输入用户名");
           errFlag  = true;
        }
        var password = $("#password").val();
        if( password == null || password == "" || username == "请输入密码") {
            $("#passwordError").html("<em class=\"icon-biaozhi \"></em>请输入密码");
            errFlag  = true;
        }

		if(errFlag)
			return false;

		$("#btnLogin").hide();
		$('#btnLogin').after("<span id='waitInfo' class='waitInfo'>正在登录，请稍等！</span>");
		$.ajax({
			type: "post",
			url: "/student/login",
			data: {"loginName":username,"password":password},
			dataType: "json",
			success: function(data){
				if(data.code != 0) {
					errFlag  = true;
					$("#j_userPasswordError").show();
					commonUtil.clearWaitInfo();
					$("#btnLogin").show();
				}
				else{
				    window.location.href = "/student/courseList.html";
				}
			}
		});
	},
	//异步加载课程数据
    course_list_init : function() {
        $.get("/course/courseAll", function(data){
            if(data.code == 0) {
                user.course_list_call_back(data.data); //填充数据
            }
        });
    },
    //预约课程
    reserveCourse : function(id) {
        $.post("/student/reserveCourse", {"cid":id},
            function (data){
                if(data.code == 0) {
                    alert("预约成功");
                    window.location.href = "/student/courseList.html";
                }else {
                    alert(data.msg);
                }
            }
        );
    },
    //回调方法，填充课程列表数据
    course_list_call_back : function(data) {
        var str = "";
        $.each(data, function(index, item){
            str += '<tr class="trA" >';
            str += "<td align=\"center\" class=\"td-100\">"+item.courseName+"</td>";
            str += "<td align=\"center\" class=\"td-210\">"+item.note+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.beginTime+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.endTime+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.duration+"分钟</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.currNum+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.numLimit+"</td>";
            str += "<td align=\"center\" class=\"td-80\">";
            str += "<a onclick=\"user.viewTeacher("+item.teacherId+")\" href=\"javascript:void(0);\">［老师详情］</a></td>";

            if(item.status == 1) {
                str += "<td align=\"center\" class=\"td-90\">已约满</td>";
            } else {
                str += "<td align=\"center\" class=\"td-90\">";
                str += "<a onclick=\"user.reserveCourse("+item.id+")\" href=\"javascript:void(0);\">［预约课程］</a></td>";
            }
            str += "</tr>";
        });
        $("#tlist").html(str);
    },
    //查看老师详细信息
    viewTeacher : function(id) {
        var url = '/teacher/teacherDetail.html?tid='+id;
        url = encodeURI(url);
        commonUtil.popup_page(402,400, url,'');
    },
    //异步加载课程数据
    success_course_list_init : function() {
        $.get("/student/successCourse", function(data){
            if(data.code == 0) {
                user.success_course_list_call_back(data.data); //填充数据
            }
        });
    },
    //回调方法，填充课程列表数据
    success_course_list_call_back : function(data) {
        var str = "";
        $.each(data, function(index, item){
            str += '<tr class="trA" >';
            str += "<td align=\"center\" class=\"td-100\">"+item.courseName+"</td>";
            str += "<td align=\"center\" class=\"td-210\">"+item.note+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.beginTime+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.endTime+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.duration+"分钟</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.currNum+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.numLimit+"</td>";
            str += "<td align=\"center\" class=\"td-80\">";
            str += "<a onclick=\"user.viewTeacher("+item.teacherId+")\" href=\"javascript:void(0);\">［老师详情］</a></td>";

            str += "<td align=\"center\" class=\"td-90\">";
            str += "<a onclick=\"user.cancelCourse("+item.crId+")\" href=\"javascript:void(0);\">［取消课程］</a></td>";
            str += "</tr>";
        });
        $("#tlist").html(str);
    },
    //预约课程
    cancelCourse : function(crId) {
        $.post("/student/cancelCourse", {"crId":crId},
            function (data){
                if(data.code == 0) {
                    alert("取消成功");
                    window.location.href = "/student/course_success.html";
                }else {
                    alert(data.msg);
                }
            }
        );
    },

    //修改密码
    change_pwd_do : function() {
        var oldPwd = $("#oldPwd").val();
        if( oldPwd == null || oldPwd == "") {
            alert("请输入原密码");
            return false;
        }
        var newPwd = $("#newPwd").val();
        if( newPwd == null || newPwd == "") {
            alert("请输入新密码");
            return false;
        }
        var newPwdAgain = $("#newPwdAgain").val();
        if( newPwdAgain == null || newPwdAgain == "") {
            alert("请输入确认密码");
            return false;
        }
        if (newPwd != newPwdAgain) {
            alert("新密码两次输入不一致");
            return false;
        }

        //$("#btnLogin").hide();
        //$('#btnLogin').after("<span id='waitInfo' class='waitInfo'>正在登录，请稍等！</span>");
        $.ajax({
            type: "post",
            url: "/student/change_pwd",
            data: {"oldPwd":oldPwd,"newPwd":newPwd},
            dataType: "json",
            success: function(data){
                if(data.code != 0) {
                    alert(data.msg);
                    //$("#j_userPasswordError").show();
                    commonUtil.clearWaitInfo();
                    //$("#btnLogin").show();
                }
                else{
                    alert("修改成功");
                    window.location.href = "/student/courseList.html";
                }
            }
        });
    },

    //修改密码
    change_pwd : function() {
       $("#changePwdBtn").click(function(){
            user.change_pwd_do();
        });
       $("#closeBtn").click( function() {
            commonUtil.closeBtn();
       });
    }
};