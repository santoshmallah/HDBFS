package com.hdbfs.dao.imp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.hdbfs.mapper.RoleMapper;
import com.hdbfs.mapper.UserMapper;
import com.hdbfs.model.LogingVo;
import com.hdbfs.model.Role;
import com.hdbfs.model.User;
import com.pdfcrowd.Pdfcrowd;

@PropertySource({ "classpath:postgres.properties" })
@Component
public class HdbfsdaoImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${ADD_USER}")
	private String addUser;
	
	@Value("${ADD_LOG}")
	private String addLog;
	
	@Value("${FIND_USER}")
	private String findUser;
	
	@Value("${GET_ROLE}")
	private String getrole;
	
	@Value("${ADD_ROLE}")
	private String addRole;
	
	public User addUser(User user) {
		try {
			if(findByEmail(user.getEmail())==null) {
				List<Role> roles=new ArrayList<Role>();
				roles.addAll(user.getRoles());
				for(Role r:roles) {
					addRole(user,user.getEmail(),r.getName());
				}
				jdbcTemplate.update(addUser,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, user.getId());
						ps.setString(2, user.getEmail());
						ps.setString(3, user.getPassword());
					}
				});
			}else {
				if(findByEmail(user.getEmail()).isPresent()) {
					user.setMessage("User Already Exist");
				}else {
					List<Role> roles=new ArrayList<Role>();
					roles.addAll(user.getRoles());
					for(Role r:roles) {
						addRole(user,user.getEmail(),r.getName());
					}
					jdbcTemplate.update(addUser,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user.getId());
							ps.setString(2, user.getEmail());
							ps.setString(3, user.getPassword());
						}
					});
				}
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User addRole(User user,String username,String role) {
		try {
			if(findRoleByName(username)!=null) {
				user.setMessage("User Role Already Exist");
			}else {
				jdbcTemplate.update(addRole,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, username);
						ps.setString(2, role);
					}
				});
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public Optional<User> findByEmail(String email) {
		List<User> user =new ArrayList<User>();
		Set<Role> role=new HashSet<Role>();
		try {
			role=getroles(email);
			user=jdbcTemplate.query(findUser, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, email);
				}
			},new UserMapper());
			
			if(role.size()>0) {
				user.get(0).setRoles(role);
			}
			if(user.size()>0) {
				Optional<User> userOp=Optional.of(user.get(0));
				return userOp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Role> findRoleByName(String email) {
		List<Role> roles=new ArrayList<Role>();
		try {
			roles=jdbcTemplate.query(getrole, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, email);
				}
			},new RoleMapper());
			
			
			if(roles.size()>0) {
				Optional<Role> userOp=Optional.of(roles.get(0));
				return userOp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateLogInfo(LogingVo logingVo) {
		try {
			jdbcTemplate.update(addLog,new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, logingVo.getUsername());
					ps.setString(2, logingVo.getPassword());
					ps.setString(3, logingVo.getRole());
					ps.setObject(4, logingVo.getRequest());
					ps.setObject(5, logingVo.getResposne());
					ps.setTimestamp(6, logingVo.getLoggingtime());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Inserted Successfully";
	}
	
	protected Set<Role> getroles(String email) {
		List<Role> roles=new ArrayList<Role>();
		Set<Role> roleset=new HashSet<Role>();
		try {
			roles=jdbcTemplate.query(getrole,new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, email);
				}
			},new RoleMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		roleset.addAll(roles);
		return roleset;
	}
	
	public void logInfo(LogingVo logingVo) {
		try {
			jdbcTemplate.update(addLog,new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, logingVo.getUsername());
					ps.setString(2, logingVo.getPassword());
					ps.setString(3, logingVo.getRole());
					ps.setObject(4, logingVo.getRequest());
					ps.setObject(5, logingVo.getResposne());
					ps.setTimestamp(6, logingVo.getLoggingtime());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generatePdf() {
		try {
            Pdfcrowd.HtmlToPdfClient client =
                new Pdfcrowd.HtmlToPdfClient("sam1234", "dc28b3cea255e0dd8588ffd3348bb2fa");
            client.setNoMargins(true);
            client.setCustomJavascript("libPdfcrowd.removeZIndexHigherThan({zlimit: 90});");
            client.setRenderingMode("viewport");
            client.setSmartScalingMode("viewport-fit");
            client.setExtractMetaTags(true);
            client.convertUrlToFile("https://aiv3portal.autoinspekt.com/report/view/MjkzNzQ0MjI4/Q1ZSVEwyOTM3NDQyMjg=", "result.pdf");
        }
        catch(Pdfcrowd.Error why) {
            System.err.println("Pdfcrowd Error: " + why);
            throw why;
        }
        catch(IOException why) {
            System.err.println("IO Error: " + why);
        }
    }
}
