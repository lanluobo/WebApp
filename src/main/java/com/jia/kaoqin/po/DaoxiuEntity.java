package com.jia.kaoqin.po;

public class DaoxiuEntity {

	public String moduleId;
	public String dataId;
	public String  id;
	public String startTime;
	public String endTime;
	public	String reason;
	public	String username;
	public	String password;
	public	String summaryId;
	public	String affairId;
	public  String type;
	public	String createTime;
	public String action;
	public	String subject;
	
	public DaoxiuEntity(){
		
	}


	public DaoxiuEntity(String user, String psd){
		
		this.username=user;
		this.password=psd;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}

	public String getAffairId() {
		return affairId;
	}

	public void setAffairId(String affairId) {
		this.affairId = affairId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Entity [moduleId=" + moduleId + ", dataId=" + dataId + ", id="
				+ id + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", reason=" + reason + ", username=" + username
				+ ", password=" + password + ", summaryId=" + summaryId
				+ ", affairId=" + affairId + ", createTime=" + createTime
				+ ", subject=" + subject + "]";
	}

}
