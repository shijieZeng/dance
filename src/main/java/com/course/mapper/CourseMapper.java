package com.course.mapper;

import com.course.entity.Course;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface CourseMapper {
    /**
     * 插入
     * @param course
     * @return
     */
    @Insert({"insert into course(coursename, note, begintime, endtime,duration,numlimit,teacherId,status,createtime) values(#{courseName}, #{note}, #{beginTime}, #{endTime}, #{duration}, #{numLimit}, #{teacherId}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCourse(Course course);

    /**
     * 更新
     * @param course
     * @return
     */
    @Update("update course set coursename=#{courseName}, note=#{note}, begintime=#{beginTime}, endtime=#{endTime},duration=#{duration},numlimit=#{numLimit},teacherId=#{teacherId},currNum=#{currNum} WHERE id=#{id}")
    int updateCourse(Course course);

    @Select("SELECT * FROM course where date_format(begintime,\"%Y-%m-%d\") BETWEEN #{beginTime} and #{endTime}")
    List<CourseResp> courseAll(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    @Select("SELECT * FROM course WHERE teacherId=#{teacherId} order by id")
    List<CourseResp> getcourseByTeacherId(Integer teacherId);

    @Select("SELECT * FROM course WHERE id=#{id}")
    Course getcourseById(Integer id);

    @Select("SELECT * FROM course WHERE id=#{id}")
    CourseResp getCourseRespById(Integer id);
    
}
