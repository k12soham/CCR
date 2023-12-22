package ccrpack.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Lob;

import jakarta.persistence.OneToMany;

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
	private Integer candidate_otp;
	private Integer candidate_token;
//comment
	@Lob
	@Column(name = "aadhar_document")
	private byte[] aadharDocument;
	private String filePath;
	private String name;

	@OneToMany(mappedBy = "candidate")
	@JsonIgnore
	private List<RatingForm> ratingform;

	@OneToMany(mappedBy = "candidate")
	@JsonIgnore
	private List<Answer> answer;

	@OneToMany(mappedBy = "candidate")
	@JsonIgnore
	private List<Comment> comments;

//	@ManyToMany
//	@JoinTable(name = "hr_cand", joinColumns = @JoinColumn(name = "candidate_id"), inverseJoinColumns = @JoinColumn(name = "hr_id"))
//	List<Hr> likedHr;

	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Candidate(Integer candidate_id, Long candidate_aadhar, String candidate_name, String candidate_email,
			String candidate_password, String candidate_phone, String candidate_dob, Integer candidate_otp,
			Integer candidate_token, byte[] aadharDocument, String filePath, String name, List<RatingForm> ratingform,
			List<Answer> answer, List<Comment> comments) {
		super();
		this.candidate_id = candidate_id;
		this.candidate_aadhar = candidate_aadhar;
		this.candidate_name = candidate_name;
		this.candidate_email = candidate_email;
		this.candidate_password = candidate_password;
		this.candidate_phone = candidate_phone;
		this.candidate_dob = candidate_dob;
		this.candidate_otp = candidate_otp;
		this.candidate_token = candidate_token;
		this.aadharDocument = aadharDocument;
		this.filePath = filePath;
		this.name = name;
		this.ratingform = ratingform;
		this.answer = answer;
		this.comments = comments;
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

	public Integer getCandidate_otp() {
		return candidate_otp;
	}

	public void setCandidate_otp(Integer candidate_otp) {
		this.candidate_otp = candidate_otp;
	}

	public Integer getCandidate_token() {
		return candidate_token;
	}

	public void setCandidate_token(Integer candidate_token) {
		this.candidate_token = candidate_token;
	}

	public List<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}

	public byte[] getAadharDocument() {
		return aadharDocument;
	}

	public void setAadharDocument(byte[] aadharDocument) {
		this.aadharDocument = aadharDocument;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

//	public List<HrAdmin> getLikedHr() {
//		return likedHr;
//	}
//
//	public void setLikedHr(List<HrAdmin> likedHr) {
//		this.likedHr = likedHr;
//	}

}
