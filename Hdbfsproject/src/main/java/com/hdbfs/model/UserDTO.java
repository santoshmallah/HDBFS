package com.hdbfs.model;

public class UserDTO {
	private Integer id;
	private String email;
	private String message;

	public UserDTO() {
	}

	public UserDTO(Integer id, String email,String message) {
		this.id = id;
		this.email = email;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", message=" + message + "]";
	}

}
