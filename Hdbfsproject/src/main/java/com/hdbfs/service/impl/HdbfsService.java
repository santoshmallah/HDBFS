package com.hdbfs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hdbfs.dao.imp.HdbfsdaoImpl;
import com.hdbfs.model.LogingVo;
import com.hdbfs.model.User;


@Service
public class HdbfsService {
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	HdbfsdaoImpl dbHdbfsdaoImpl;
	
	public User save(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		
		return dbHdbfsdaoImpl.addUser(user);
	}
	
	public void logInfo(LogingVo logingVo){
		try {
			dbHdbfsdaoImpl.logInfo(logingVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generatePDF() {
		try {
			dbHdbfsdaoImpl.generatePdf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
