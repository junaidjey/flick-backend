package com.project.flik.payload;

import javax.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by junaid on 02/08/17.
 */

public class SignUpRequest {
//    @NotBlank
//    @Size(min = 4, max = 40)
//    private String name;

//    @NotBlank
//    @Size(min = 4, max = 40)
//    private String firstName;
//    
//    @NotBlank
//    @Size(min = 4, max = 40)
//	private String lastName;
//    
//    @NotBlank
//    @Size(min = 3, max = 15)
//    private String username;

	@NotBlank
	@Size(max = 100)
	private String fullName;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

//	private String username;

//	@NotBlank
//  @Size(min = 3, max = 15)
//	private String birthDate;
//	
//	@NotBlank
//    @Size(min = 3, max = 15)
//	private String phoneNumber;
//	
	private String role;
//	
//	private MultipartFile profilePic;

//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
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

//	public String getBirthDate() {
//		return birthDate;
//	}
//
//	public void setBirthDate(String birthDate) {
//		this.birthDate = birthDate;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public String getJobType() {
//		return jobType;
//	}
//
//	public void setJobType(String jobType) {
//		this.jobType = jobType;
//	}
//
//	public MultipartFile getProfilePic() {
//		return profilePic;
//	}
//
//	public void setProfilePic(MultipartFile profilePic) {
//		this.profilePic = profilePic;
//	}

//	@Override
//	public String toString() {
//		return "SignUpRequest [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
//				+ ", email=" + email + ", password=" + password + ", birthDate=" + birthDate + ", phoneNumber="
//				+ phoneNumber + ", jobType=" + jobType + ", profilePic=" + profilePic + "]";
//	}

}
