package com.course.vo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by linxiao on 2018/8/14.
 */
public class CourseReq {
    @NotBlank(message = "课程名不能为空")
    private String courseName;
    @NotBlank(message = "课程描述不能为空")
    private String note;
    @NotNull(message = "时间不能为空")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date beginTime;
    @NotNull(message = "时间不能为空")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date endTime;
    private Integer numLimit;
    @NotNull(message = "上课老师不能为空")
    private Integer teacherId;

    public Integer getCurrNum() {
        return currNum;
    }

    public void setCurrNum(Integer currNum) {
        this.currNum = currNum;
    }

    private Integer currNum;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNumLimit() {
        return numLimit;
    }

    public void setNumLimit(Integer numLimit) {
        this.numLimit = numLimit;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "CourseReq={" +
                "courseName='" + courseName + '\'' +
                ", note='" + note + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", numLimit=" + numLimit +
                ", teacherId=" + teacherId +
                '}';
    }
}
