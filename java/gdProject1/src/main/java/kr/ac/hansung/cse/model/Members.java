package kr.ac.hansung.cse.model;

public class Members {

	private int id;
	private String userId;
	private String password;
	private String email;

	/* private String domain; */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*
	 * public String getDomain() { return domain; } public void setDomain(String
	 * domain) { this.domain = domain; }
	 */
}
