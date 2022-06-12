package com.hdbfs;

import org.springframework.boot.test.context.SpringBootTest;

import com.hdbfs.model.Role;


@SpringBootTest
class HdbfsprojectApplicationTests {
	
	void contextLoads() {
		Role admin = new Role("ROLE_ADMIN");
		Role editor = new Role("ROLE_USER");
	}

}
