package ccrpack.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class HrAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hr_admin_id;
	private String hr_name;
	private Long hr_phone;
	private String hr_email;
	private String hr_password;
	private String hr_role;

	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToMany(mappedBy = "hrAdmin")
	@JsonIgnore
	private List<RatingForm> ratingform;

	private Integer added_by;

	private Integer approver;

	// @ManyToMany(mappedBy = "likedHr")
	// List<Candidate> likes;

	public HrAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HrAdmin(Integer hr_admin_id, String hr_name, Long hr_phone, String hr_email, String hr_password,
			String hr_role, Company company, List<RatingForm> ratingform, Integer added_by, Integer approver) {
		super();
		this.hr_admin_id = hr_admin_id;
		this.hr_name = hr_name;
		this.hr_phone = hr_phone;
		this.hr_email = hr_email;
		this.hr_password = hr_password;
		this.hr_role = hr_role;
		this.company = company;
		this.ratingform = ratingform;
		this.added_by = added_by;
		this.approver = approver;
	}

	public Integer getHr_admin_id() {
		return hr_admin_id;
	}

	public void setHr_admin_id(Integer hr_admin_id) {
		this.hr_admin_id = hr_admin_id;
	}

	public String getHr_name() {
		return hr_name;
	}

	public void setHr_name(String hr_name) {
		this.hr_name = hr_name;
	}

	public Long getHr_phone() {
		return hr_phone;
	}

	public void setHr_phone(Long hr_phone) {
		this.hr_phone = hr_phone;
	}

	public String getHr_email() {
		return hr_email;
	}

	public void setHr_email(String hr_email) {
		this.hr_email = hr_email;
	}

	public String getHr_password() {
		return hr_password;
	}

	public void setHr_password(String hr_password) {
		this.hr_password = hr_password;
	}

	public String getHr_role() {
		return hr_role;
	}

	public void setHr_role(String hr_role) {
		this.hr_role = hr_role;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<RatingForm> getRatingform() {
		return ratingform;
	}

	public void setRatingform(List<RatingForm> ratingform) {
		this.ratingform = ratingform;
	}

	public Integer getAdded_by() {
		return added_by;
	}

	public void setAdded_by(Integer added_by) {
		this.added_by = added_by;
	}

	public Integer getApprover() {
		return approver;
	}

	public void setApprover(Integer approver) {
		this.approver = approver;
	}

}