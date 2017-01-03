package com.jia.kaoqin.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jia.kaoqin.util.KaoqinUtil;

public class KaoqinEntity {

	public Date startTime;
	public Date endTime;
	public String status = "";

	public static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Override
	
	public String toString() {
		return "Entity [getStartTime()="  + getStartTime() +  ", getEndTime()="
				+ getEndTime() + ", getDay()=" + getDay() + ", getStatus()="
				+ getStatus() + "]";
	}

	public KaoqinEntity(Date start, Date end) {
		this.startTime = start;
		this.endTime = end;
	}

	public String getStartTime() {
		return format.format(startTime);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		if (startTime == endTime) {
			return "";
		} else {
			return format.format(endTime);
		}

	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@SuppressWarnings("deprecation")
	public String getDay() {
		return KaoqinUtil.getDay(startTime.getDay());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return dateFormat.format(startTime);
	}
}
