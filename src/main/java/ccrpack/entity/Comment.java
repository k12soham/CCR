package ccrpack.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Comment {

	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comment_id;
	private String comment;
	private String suggestion;
	private Boolean comment_approve;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "hr_id")
	private Hr hr;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(Integer comment_id, String comment, String suggestion, Boolean comment_approve, Candidate candidate,
			Hr hr) {
		super();
		this.comment_id = comment_id;
		this.comment = comment;
		this.suggestion = suggestion;
		this.comment_approve = comment_approve;
		this.candidate = candidate;
		this.hr = hr;
	}

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Boolean getComment_approve() {
		return comment_approve;
	}

	public void setComment_approve(Boolean comment_approve) {
		this.comment_approve = comment_approve;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Hr getHr() {
		return hr;
	}

	public void setHr(Hr hr) {
		this.hr = hr;
	}

}
