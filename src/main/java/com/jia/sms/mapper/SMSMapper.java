package com.jia.sms.mapper;

import com.jia.sms.po.SMS;
import com.jia.sms.po.SMSRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * Created by jiaxl on 2016/11/5.
 */
public interface SMSMapper {
    @Select("select * from sms_list where available=0")
    List<SMS>getSMSList();

    @Select("select * from sms_list where available=0 and isbug=0")
    List<SMS>getSMSListCode();

    @Insert("insert into sms_record(phone,type,content,success) select #{record.phone},#{record.type},#{record.content},#{record.success}")
    void insertSMSRecord(@Param("record") SMSRecord record);
}
