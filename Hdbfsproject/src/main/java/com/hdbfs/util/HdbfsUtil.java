package com.hdbfs.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hdbfs.model.LogingVo;
import com.hdbfs.model.Role;
import com.hdbfs.model.User;

public class HdbfsUtil {

	
	public static java.sql.Date getSQLDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
	public static LogingVo setLogVo(User user,String reString,String respoString) {
		LogingVo logingVo2=new LogingVo();
		try {
			List<Role> role=new ArrayList<Role>();
			role.addAll(user.getRoles());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			logingVo2.setUsername(user.getUsername());
			logingVo2.setPassword(user.getPassword());
			logingVo2.setRole(role.toString());
			logingVo2.setLoggingtime(timestamp);
			logingVo2.setRequest(reString);
			logingVo2.setResposne(respoString);
		} catch (Exception e) {
		}
		return logingVo2;
		
	}
}
