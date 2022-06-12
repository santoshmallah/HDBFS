package com.hdbfs.model;

import java.sql.Date;
import java.sql.Timestamp;

public class LogingVo {
	private String username;
	private String password;
	private String role;
	private String request;
	private String resposne;
	private Timestamp loggingtime;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Object getRequest() {
		return request;
	}
	
	public String getResposne() {
		return resposne;
	}
	public void setResposne(String resposne) {
		this.resposne = resposne;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	public Timestamp getLoggingtime() {
		return loggingtime;
	}
	public void setLoggingtime(Timestamp loggingtime) {
		this.loggingtime = loggingtime;
	}
	@Override
	public String toString() {
		return "LogingVo [username=" + username + ", password=" + password + ", role=" + role + ", request=" + request
				+ ", resposne=" + resposne + ", loggingtime=" + loggingtime + "]";
	}
}
