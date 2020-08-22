package com.project.flik.payload;

public class UserSummary {
	private Long id;
	private String email;
	private String fullName;
	private Long following;
	private Long followers;

	public UserSummary(Long id, String fullName, String email, Long following, Long followers) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.followers = followers;
		this.following = following;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getFollowing() {
		return following;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

	public Long getFollowers() {
		return followers;
	}

	public void setFollowers(Long followers) {
		this.followers = followers;
	}

}
