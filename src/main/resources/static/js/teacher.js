var teacher = {
//加载头部，左边菜单栏
    _init_ : function() {
        $("#header").load("/teacher/header.html");
        $("#left").load("/teacher/left.html");
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
                return teacher.checkForm(type);
            } );
        },
        //用户登录
        checkForm : function() {
    		var errFlag = false;
    		var username = $("#username").val();
    		if( username == null || username == ""  || username == "请输入登录名") {
                $("#usernamError").html("<em class=\"icon-biaozhi \"></em>请输入用户名aaa");
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
    			url: "/teacher/login",
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
    				    window.location.href = "/teacher/courseList.html";
    				}
    			}
    		});
    	},
    //异步加载课程数据
    success_course_list_init : function() {
        $.get("/teacher/successCourse", function(data){
            if(data.code == 0) {
                teacher.success_course_list_call_back(data.data); //填充数据
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
            //str += "<a onclick=\"user.viewTeacher("+item.teacherId+")\" href=\"javascript:void(0);\">［老师详情］</a></td>";

            str += "<td align=\"center\" class=\"td-90\">";
            //str += "<a onclick=\"user.cancelCourse("+item.crId+")\" href=\"javascript:void(0);\">［取消课程］</a></td>";
            str += "</tr>";
        });
        $("#tlist").html(str);
    },
    //查询老师详细信息，并填充数据
    teacher_detail : function(id) {
        $.get("/teacher/teacherById", {"id": id}, function(data){
            if(data.code == 0) {
                 $("#userName").html(data.data.userName);
                 $("#remark").html(data.data.remark);
                 var sex = "男";
                 if(data.data.gender == 0) {
                    sex = "男";
                 }else {
                    sex = "女";
                 }
                 $("#gender").html(sex);
                 $("#mobile").html(data.data.mobile);
            }
        });
        $("#closeBtn").click( function() {
            commonUtil.closeBtn();
        });
    }
};
