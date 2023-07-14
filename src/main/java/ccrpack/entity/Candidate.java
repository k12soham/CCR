package ccrpack.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Candidate {
	@Id
	@Column(name = "candidate_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer candidate_id;
	private Long candidate_aadhar;
	private String candidate_name;
	private String candidate_email;
	private String candidate_password;
	private String candidate_phone;
	private String candidate_dob;

	@OneToMany(mappedBy = "candidate")
	@JsonIgnore
	private List<RatingForm> ratingform;


//	@ManyToMany
//	@JoinTable(name = "hr_cand", joinColumns = @JoinColumn(name = "candidate_id"), inverseJoinColumns = @JoinColumn(name = "hr_id"))
//	List<Hr> likedHr;


	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Candidate(Integer candidate_id, Long candidate_aadhar, String candidate_name, String candidate_email,
			String candidate_password, String candidate_phone, String candidate_dob, List<RatingForm> ratingform,
			List<Hr> likedHr) {
		super();
		this.candidate_id = candidate_id;
		this.candidate_aadhar = candidate_aadhar;
		this.candidate_name = candidate_name;
		this.candidate_email = candidate_email;
		this.candidate_password = candidate_password;
		this.candidate_phone = candidate_phone;
		this.candidate_dob = candidate_dob;
		this.ratingform = ratingform;
		//this.likedHr = likedHr;
	}

	public Integer getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(Integer candidate_id) {
		this.candidate_id = candidate_id;
	}

	public Long getCandidate_aadhar() {
		return candidate_aadhar;
	}

	public void setCandidate_aadhar(Long candidate_aadhar) {
		this.candidate_aadhar = candidate_aadhar;
	}

	public String getCandidate_name() {
		return candidate_name;
	}

	public void setCandidate_name(String candidate_name) {
		this.candidate_name = candidate_name;
	}

	public String getCandidate_email() {
		return candidate_email;
	}

	public void setCandidate_email(String candidate_email) {
		this.candidate_email = candidate_email;
	}

	public String getCandidate_password() {
		return candidate_password;
	}

	public void setCandidate_password(String candidate_password) {
		this.candidate_password = candidate_password;
	}

	public String getCandidate_phone() {
		return candidate_phone;
	}

	public void setCandidate_phone(String candidate_phone) {
		this.candidate_phone = candidate_phone;
	}

	public String getCandidate_dob() {
		return candidate_dob;
	}

	public void setCandidate_dob(String candidate_dob) {
		this.candidate_dob = candidate_dob;
	}

	public List<RatingForm> getRatingform() {
		return ratingform;
	}

	public void setRatingform(List<RatingForm> ratingform) {
		this.ratingform = ratingform;
	}


//	public List<HrAdmin> getLikedHr() {
//		return likedHr;
//	}
//
//	public void setLikedHr(List<HrAdmin> likedHr) {
//		this.likedHr = likedHr;
//	}

}
