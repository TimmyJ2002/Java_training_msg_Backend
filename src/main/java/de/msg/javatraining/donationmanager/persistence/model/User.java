package de.msg.javatraining.donationmanager.persistence.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

@Table(	name = "user",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="firstname")
	@NonNull
	private String firstName;

	@Column(name="lastname")
	@NonNull
	private String lastName;

	@Column(name="email")
	@NonNull
	private String email;

	@Column(name="mobilenumber")
	private String mobileNumber;

			@Column(name="isactive")
	private boolean isActive;

	@Column(name="logincount")
	private int logincount;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;


//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(	name = "user_campaign",
//			joinColumns = @JoinColumn(name = "user_id"),
//			inverseJoinColumns = @JoinColumn(name = "campaign_id"))
//	private List<Campaign> campaigns;

//	@OneToMany(
//			cascade = CascadeType.ALL,
//			orphanRemoval = true,
//			mappedBy = "createdBy"
//	)
//	private List<Donation> donationListCreated;
//
//	@OneToMany(
//			cascade = CascadeType.ALL,
//			orphanRemoval = true,
//			mappedBy = "approvedBy"
//	)
//	private List<Donation> donationListApproved;

//	@OneToMany(
//			cascade = CascadeType.ALL,
//			orphanRemoval = true,
//			mappedBy = "notificationReceiver"
//	) 	private List<Notification> notifications;

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(Long id, String username, String password, @NonNull String firstName, @NonNull String lastName, @NonNull String email, String mobileNumber, boolean isActive, int loginCount, List<Role> roles, List<Campaign> campaigns, List<Donation> donationListCreated, List<Donation> donationListApproved, List<Notification> notifications) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.isActive = isActive;
		this.logincount = loginCount;
		this.roles = roles;
//		this.campaigns = campaigns;
//		this.donationListCreated = donationListCreated;
//		this.donationListApproved = donationListApproved;
//		this.notifications = notifications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@NonNull
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NonNull String firstName) {
		this.firstName = firstName;
	}
	@NonNull
	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NonNull String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setActive(boolean active) {
		isActive = active;
	}

	public int getLoginCount() {
		return logincount;
	}

	public void setLoginCount(int loginCount) {
		this.logincount = loginCount;
	}

//	public List<Campaign> getCampaigns() {
//		return campaigns;
//	}
//
//	public void setCampaigns(List<Campaign> campaigns) {
//		this.campaigns = campaigns;
//	}

//	public List<Donation> getDonationListCreated() {
//		return donationListCreated;
//	}
//	public void setDonationListCreated(List<Donation> donationListCreated) {
//		this.donationListCreated = donationListCreated;
//	}
//
//	public List<Donation> getDonationListApproved() {
//		return donationListApproved;
//	}
//
//	public void setDonationListApproved(List<Donation> donationListApproved) {
//		this.donationListApproved = donationListApproved;
//	}

//	public List<Notification> getNotifications() {
//		return notifications;
//	}
//
//	public void setNotifications(List<Notification> notifications) {
//		this.notifications = notifications;
//	}
}
