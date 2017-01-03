package com.jia.kaoqin.mapper;

import com.jia.kaoqin.po.DaoxiuEntity;
import com.jia.kaoqin.po.SMSNotifyUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jiaxl on 2016/9/29.
 */
public interface KaoqinMapper {
    /**
     * 查询当日是否成功提交倒休
     * @param entity
     * @return
     */
    @Select("select count(*) from daoxiulog where username=#{entity.username} and " +
            "to_days(start_time)=to_days(#{entity.startTime}) and status='提交'")
    boolean selectTodayRecord(@Param("entity")DaoxiuEntity entity);

    /**
     * 插入倒休记录
     * @param entity
     */
    @Insert("insert into daoxiulog select #{entity.username},#{entity.subject},#{entity.summaryId},#{entity.affairId},'提交',#{entity.type},#{entity.startTime},#{entity.createTime},now(),null")
    void insertLogRecord(@Param("entity")DaoxiuEntity entity);

    /**
     * 撤销倒休更新记录
     * @param entity
     * @return
     */
    @Update("update daoxiulog set status='撤销',chexiao_time=now() where summary_id=#{entity.summaryId} and affair_id=#{entity.affairId}")
    int updateStatus(@Param("entity")DaoxiuEntity entity);

    @Select("select summary_id,affair_id from daoxiujilu where username=#{username}")
    DaoxiuEntity selectSummaryIdAndAffairId(@Param("username")String username);

    /**
     * 插入倒休新记录前，删除旧记录
     * @param username
     */
    @Delete("delete from daoxiujilu where username=#{username}")
    void deleteCurrentRecord(@Param("username")String username);

    /**
     * 插入当前倒休记录
     * @param entity
     */
    @Insert("insert into daoxiujilu select #{entity.username},#{entity.subject},#{entity.summaryId}," +
            "#{entity.affairId},#{entity.createTime},now()")
    void insertCurrentRecord(@Param("entity") DaoxiuEntity entity);

    /**
     * 查询考勤工号
     * @param name
     * @return
     */
    @Select("select employee_id from employees where employee_name=#{name} limit 1")
    Integer selectEmployeeId(@Param("name")String name);

    /**
     * 查询下班打卡短信提醒的用户
     * @return
     */
    @Select("select *from sms_user where enabled = 0")
    List<SMSNotifyUser> selectSMSNotifyUsers();

    /**
     * 插入下班打卡短信提醒用户
     */
    @Insert("insert into sms_user(employee_id,phone,enabled) values(#{user.employeeId},#{user.phone},0)")
    void insertSMSNotifyUser(@Param("user")SMSNotifyUser user);

    @Delete("delete from sms_user where phone = #{user.phone} and employee_id = #{user.employeeId}")
    int deleteSMSNotifyUser(@Param("user")SMSNotifyUser user);
}
