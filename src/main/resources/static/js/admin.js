var admin = {
    //加载头部，左边菜单栏
    _init_ : function() {
        $("#header").load("/admin/header.html");
        $("#left").load("/admin/left.html");
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
                    return admin.checkForm(type);
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
        			url: "/admin/login",
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
        				    window.location.href = "/admin/courseList.html";
        				}
        			}
        		});
        	},
    //添加课程初始化方法
    course_add_init : function() {
        //获取所有老师，填充数据
        $.get("/teacher/teacherAll", function(data){
            var targetNode = document.getElementById("teacherId");
            var optionNode = document.createElement('option');
            optionNode.value = -1;
            optionNode.innerHTML = '请选择老师';
            targetNode.appendChild(optionNode);

            $.each(data.data, function(index, item) {
                optionNode = document.createElement('option');
                optionNode.value = item.id;
                optionNode.innerHTML = item.userName;
                targetNode.appendChild(optionNode);
            });
        });
        $("#addCourseBtn").click(function(){
            admin.add_course_btn();
        });
        $("#closeBtn").click( function() {
            commonUtil.closeBtn();
        });
    },
    //添加课程信息
    add_course_btn : function() {
        var courseName = $("#courseName").val();
        if( courseName == null || courseName == "" ) {
            alert("请输入课程名称");
            return false;
        }
        var note = $("#note").val();
        if( note == null || note == "" ) {
            alert("请输入课程说明");
            return false;
        }
        var beginTime = $("#beginTime").val();
        if( beginTime == null || beginTime == "" ) {
            alert("请输入上课时间");
            return false;
        }
        var endTime = $("#endTime").val();
        if( endTime == null || endTime == "" ) {
            alert("请输入结束时间");
            return false;
        }
        var numLimit = $("#numLimit").val();
        if( numLimit == null || numLimit == "" ) {
            alert("请输入人数限制");
            return false;
        }
        var teacherId = $("#teacherId").val();
        if( teacherId == null || teacherId == "-1" ) {
            alert("请选择授课老师");
            return false;
        }

        $("#addCourseBtn").hide();
        $('#addCourseBtn').after("<span id='waitInfo' class='waitInfo'>正在保存数据，请稍等！</span>");
        $.ajax({
            type: "post",
            url: "/admin/saveCourse",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({"courseName":courseName,"note":note,"beginTime":beginTime,"endTime":endTime,"numLimit":numLimit,"teacherId":teacherId}),
            dataType: "json",
            success: function(data){
                if(data.code != 0) {
                    commonUtil.clearWaitInfo();
                    $("#addCourseBtn").show();
                }
                else{
                    window.location.href = "/admin/courseList.html";
                    //alert("添加成功");
                }
            }
        });
    },
    //异步加载课程数据
    course_list_init : function() {
        $.get("/course/courseAll", function(data){
            if(data.code == 0) {
                admin.course_list_call_back(data.data); //填充数据
            }
        });
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
                str += "<td align=\"center\" class=\"td-90\">可预约";
                str += "<a onclick=\"admin.edit_course("+item.id+")\" href=\"javascript:void(0);\">［修改］</a></td>";
            }
            str += "</tr>";
        });
        $("#tlist").html(str);
    },
    //修改课程信息
    edit_course : function(id) {
        var url = '/admin/modifyCourse.html?tid='+id;
        url = encodeURI(url);
        commonUtil.popup_page(402,420, url,'');
    },
    //
    course_edit_init : function(id) {
        admin.course_add_init();
        //填充旧数据
        $.get("/course/getCourseById",{"tid": id}, function(data){
            if(data.code == 0) {
                $("#cid").val(data.data.id);
                $("#courseName").val(data.data.courseName);
                $("#note").val(data.data.note);
                $("#beginTime").val(data.data.beginTime);
                $("#endTime").val(data.data.endTime);
                $("#numLimit").val(data.data.numLimit);
                $("#teacherId").val(data.data.teacherId);
            }
        });
        // click事件
        $("#modifyCourseBtn").click(function(){
            admin.modify_course_btn();
        });
    },
    //修改
    modify_course_btn : function() {
        var courseName = $("#courseName").val();
        if( courseName == null || courseName == "" ) {
            alert("请输入课程名称");
            return false;
        }
        var note = $("#note").val();
        if( note == null || note == "" ) {
            alert("请输入课程说明");
            return false;
        }
        var beginTime = $("#beginTime").val();
        if( beginTime == null || beginTime == "" ) {
            alert("请输入上课时间");
            return false;
        }
        var endTime = $("#endTime").val();
        if( endTime == null || endTime == "" ) {
            alert("请输入结束时间");
            return false;
        }
        var numLimit = $("#numLimit").val();
        if( numLimit == null || numLimit == "" ) {
            alert("请输入人数限制");
            return false;
        }
        var teacherId = $("#teacherId").val();
        if( teacherId == null || teacherId == "-1" ) {
            alert("请选择授课老师");
            return false;
        }
        //修改的课程id
        var id = $("#cid").val();

        $("#addCourseBtn").hide();
        $('#addCourseBtn').after("<span id='waitInfo' class='waitInfo'>正在保存数据，请稍等！</span>");
        $.ajax({
            type: "post",
            url: "/admin/updateCourse?id="+id,
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({"courseName":courseName,"note":note,"beginTime":beginTime,"endTime":endTime,"numLimit":numLimit,"teacherId":teacherId}),
            dataType: "json",
            success: function(data){
                if(data.code != 0) {
                    commonUtil.clearWaitInfo();
                    $("#addCourseBtn").show();
                }
                else{
                    parent.window.location.href = "/admin/courseList.html";
                    //alert("添加成功");
                }
            }
        });
    },
	
	 //异步加载学员信息
    student_list_init : function() {
        $.get("/student/studentAll", function(data){
            if(data.code == 0) {
                admin.student_list_call_back(data.data); //填充数据
            }
        });
    },
    //回调方法，填充学员信息列表数据
    student_list_call_back : function(data) {
        var str = "";
        $.each(data, function(index, item){
            str += '<tr class="trA" >';
            str += "<td align=\"center\" class=\"td-100\">"+item.id+"</td>";
            str += "<td align=\"center\" class=\"td-210\">"+item.loginName+"</td>";
			str += "<td align=\"center\" class=\"td-80\">"+item.createTime+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.mobile+"</td>";
            str += "<td align=\"center\" class=\"td-120\">"+item.address+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.remainNum+"</td>";
            /*str += "<td align=\"center\" class=\"td-80\">"+item.currNum+"</td>";
            str += "<td align=\"center\" class=\"td-80\">"+item.numLimit+"</td>";
            str += "<td align=\"center\" class=\"td-80\">";
            str += "<a onclick=\"user.viewTeacher("+item.teacherId+")\" href=\"javascript:void(0);\">［老师详情］</a></td>";
            if(item.status == 1) {
                str += "<td align=\"center\" class=\"td-90\">已约满</td>";
            } else {
                str += "<td align=\"center\" class=\"td-90\">可预约";
                str += "<a onclick=\"admin.edit_course("+item.id+")\" href=\"javascript:void(0);\">［修改］</a></td>";
            }*/
            str += "</tr>";
        });
        $("#tlist").html(str);
    }	

    //异步加载课程数据
    teacher_list_init : function() {
        $.get("/teacher/teacherAll", function(data){
            if(data.code == 0) {
                admin.teacher_list_call_back(data.data); //填充数据
            }
        });
    },
    //回调方法，填充课程列表数据
    teacher_list_call_back : function(data) {
        var str = "";
        $.each(data, function(index, item){
            str += '<tr class="trA" >';
            str += "<td align=\"center\" >"+item.userName+"</td>";
            str += "<td align=\"center\" >"+item.gender+"</td>";
            str += "<td align=\"center\" >"+item.mobile+"</td>";
            str += "<td align=\"center\" >"+item.address+"</td>";
            str += "<td align=\"center\" class=\"td-80\">";
            str += "</tr>";
        });
        $("#tlist").html(str);
    }
};
