var teacher = {
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