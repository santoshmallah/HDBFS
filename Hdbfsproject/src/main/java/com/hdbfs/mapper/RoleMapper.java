package com.hdbfs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hdbfs.model.Role;
import com.hdbfs.model.User;

public class RoleMapper implements RowMapper<Role>{

	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role=new Role();
		role.setUsername(rs.getString("username"));
		role.setName(rs.getString("name"));
		return role;
	}

}
