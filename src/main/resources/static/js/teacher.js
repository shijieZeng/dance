var teacher = {
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
    				    window.location.href = "/teacher/teacherDetail.html";
    				}
    			}
    		});
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
