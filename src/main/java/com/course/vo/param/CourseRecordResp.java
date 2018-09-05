package com.course.vo.param;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by linxiao on 2018/9/5.
 */
public class CourseRecordResp {
    private Integer id;
    private String courseName;
    private String note;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date beginTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    private Integer duration;
    private Integer currNum;
    private Integer numLimit;
    private Integer teacherId;
    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer crId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCurrNum() {
        return currNum;
    }

    public void setCurrNum(Integer currNum) {
        this.currNum = currNum;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }
}
