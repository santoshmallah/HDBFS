package com.hdbfs.controler;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hdbfs.configuration.JwtTokenUtil;
import com.hdbfs.model.AuthRequest;
import com.hdbfs.model.AuthResponse;
import com.hdbfs.model.User;
import com.hdbfs.model.UserDTO;
import com.hdbfs.service.impl.HdbfsService;
import com.hdbfs.util.HdbfsUtil;


/**
 * @author santosh Mallah
 * @author santoshmallah121@gmail.com
 *
 */
@Controller
@RestController
public class Controler {
	
	@Autowired 
	AuthenticationManager authManager;
	
	@Autowired 
	JwtTokenUtil jwtUtil;
	
	@Autowired
	HdbfsService hdbfsService;
	
	@PutMapping("/createUsers")
	public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
		UserDTO userDto = null;
		try {
			User createdUser = hdbfsService.save(user);
			if(createdUser.getMessage()==null) {
				createdUser.setMessage("User Registartion successfully");
				userDto = new UserDTO(createdUser.getId(), createdUser.getEmail(),createdUser.getMessage());
			}else {
				userDto = new UserDTO(createdUser.getId(), createdUser.getEmail(),createdUser.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(), request.getPassword())
			);
			User user = (User) authentication.getPrincipal();
			
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
			
			hdbfsService.logInfo(HdbfsUtil.setLogVo(user, request.toString(), response.toString()));
			
			return ResponseEntity.ok().body(response);
			
		} catch (BadCredentialsException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	/**
	 * @param webpage url 
	 * {@summary} url is hardcoded in dao layer
	 * {@summary} this will generate pdf file of webpage which will be store in your project location
	 * */
	@GetMapping("/getPdf")
	@RolesAllowed("ROLE_ADMIN")
	public String getDocument() {
		try {
			System.out.println("Hello");
//			hdbfsService.generatePDF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Pdf Generated Successfully";
	}

}
