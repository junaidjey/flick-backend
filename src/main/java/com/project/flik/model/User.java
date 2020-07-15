package com.project.flik.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.project.flik.model.audit.DateAudit;

/**
 * Created by junaid on 01/08/17.
 */

@Entity
@Table(name = "users", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {
//            "username"
//        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @NotBlank
//    @Size(max = 40)
//    private String firstName;
//    
//    @NotBlank
//    @Size(max = 40)
//	private String lastName;
//
//    @NotBlank
//    @Size(max = 15)
//    private String username;

	
  @NotBlank
  @Size(max = 100)
  private String fullName;

	
    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;
    
//    @NotBlank
//	private Date birthDate;
//	
//	@NotBlank
//    @Size(max = 15)
//	private String phoneNumber;
//	
//	private String profilePic;
//	
//	private String imageName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String fullName, String email, String password) {
//        this.firstName = firstName;
//        this.username = userName;
    	this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

//    public User(String firstName,String lastName, String userName, String email, String password, Date birthDate, String phoneNumber) {
////        this.firstName = firstName;
////        this.lastName = lastName;
////        this.username = userName;
//        this.email = email;
//        this.password = password;
////        this.birthDate = birthDate;
////        this.phoneNumber = phoneNumber;
//    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
//	public void setUsername(String userName) {
//		this.username = userName;
//	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public Date getBirthDate() {
//		return birthDate;
//	}
//
//	public void setBirthDate(Date birthDate) {
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

	public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

//	public String getProfilePic() {
//		return profilePic;
//	}
//
//	public void setProfilePic(String profilePic) {
//		this.profilePic = profilePic;
//	}
//
//	public String getImageName() {
//		return imageName;
//	}
//
//	public void setImageName(String imageName) {
//		this.imageName = imageName;
//	}
}