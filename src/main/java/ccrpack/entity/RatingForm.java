package ccrpack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RatingForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rating_id;
	private boolean q1;
	private boolean q2;
	private boolean q3;
	private boolean q4;
	private boolean q5;
	private boolean q6;
	private boolean q7;
	private boolean q8;
	private boolean q9;
	private boolean q10;
	private Integer rating_total;
	private boolean approve;
	private boolean new_request;
	
	
//	private int category1Score;
//	private int category2Score;
//	private int category3Score;
//	private int category4Score;
//	private int category5Score;
//	private String category1;
//	private String category2;
//	private String category3;
//	private String category4;
//	private String category5;


	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "hr_id")
	private Hr hr;

	private Integer approver_id;

//	@ManyToOne
//	@JoinColumn(name = "company_tan")
//	private Company company;

	public RatingForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingForm(Integer rating_id, boolean q1, boolean q2, boolean q3, boolean q4, boolean q5, boolean q6,
			boolean q7, boolean q8, boolean q9, boolean q10, Integer rating_total, boolean approve, boolean new_request,
			Candidate candidate, Hr hr, Integer approver_id, Company company) {
		super();
		this.rating_id = rating_id;
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.q4 = q4;
		this.q5 = q5;
		this.q6 = q6;
		this.q7 = q7;
		this.q8 = q8;
		this.q9 = q9;
		this.q10 = q10;
		this.rating_total = rating_total;
		this.approve = approve;
		this.new_request = new_request;
		this.candidate = candidate;
		this.hr = hr;
		this.approver_id = approver_id;
	}

	public Integer getRating_id() {
		return rating_id;
	}

	public void setRating_id(Integer rating_id) {
		this.rating_id = rating_id;
	}

	public boolean isQ1() {
		return q1;
	}

	public void setQ1(boolean q1) {
		this.q1 = q1;
	}

	public boolean isQ2() {
		return q2;
	}

	public void setQ2(boolean q2) {
		this.q2 = q2;
	}

	public boolean isQ3() {
		return q3;
	}

	public void setQ3(boolean q3) {
		this.q3 = q3;
	}

	public boolean isQ4() {
		return q4;
	}

	public void setQ4(boolean q4) {
		this.q4 = q4;
	}

	public boolean isQ5() {
		return q5;
	}

	public void setQ5(boolean q5) {
		this.q5 = q5;
	}

	public boolean isQ6() {
		return q6;
	}

	public void setQ6(boolean q6) {
		this.q6 = q6;
	}

	public boolean isQ7() {
		return q7;
	}

	public void setQ7(boolean q7) {
		this.q7 = q7;
	}

	public boolean isQ8() {
		return q8;
	}

	public void setQ8(boolean q8) {
		this.q8 = q8;
	}

	public boolean isQ9() {
		return q9;
	}

	public void setQ9(boolean q9) {
		this.q9 = q9;
	}

	public boolean isQ10() {
		return q10;
	}

	public void setQ10(boolean q10) {
		this.q10 = q10;
	}

	public Integer getRating_total() {
		return rating_total;
	}

	public void setRating_total(Integer rating_total) {
		this.rating_total = rating_total;
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

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public boolean isNew_request() {
		return new_request;
	}

	public void setNew_request(boolean new_request) {
		this.new_request = new_request;
	}

	public Integer getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(Integer approver_id) {
		this.approver_id = approver_id;
	}

//	public int getCategory1Score() {
//		return category1Score;
//	}
//
//	public void setCategory1Score(int category1Score) {
//		this.category1Score = category1Score;
//	}
//
//	public int getCategory2Score() {
//		return category2Score;
//	}
//
//	public void setCategory2Score(int category2Score) {
//		this.category2Score = category2Score;
//	}
//
//	public int getCategory3Score() {
//		return category3Score;
//	}
//
//	public void setCategory3Score(int category3Score) {
//		this.category3Score = category3Score;
//	}
//
//	public int getCategory4Score() {
//		return category4Score;
//	}
//
//	public void setCategory4Score(int category4Score) {
//		this.category4Score = category4Score;
//	}
//
//	public int getCategory5Score() {
//		return category5Score;
//	}
//
//	public void setCategory5Score(int category5Score) {
//		this.category5Score = category5Score;
//	}
//
//	public String getCategory1() {
//		return category1;
//	}
//
//	public void setCategory1(String category1) {
//		this.category1 = category1;
//	}
//
//	public String getCategory2() {
//		return category2;
//	}
//
//	public void setCategory2(String category2) {
//		this.category2 = category2;
//	}
//
//	public String getCategory3() {
//		return category3;
//	}
//
//	public void setCategory3(String category3) {
//		this.category3 = category3;
//	}
//
//	public String getCategory4() {
//		return category4;
//	}
//
//	public void setCategory4(String category4) {
//		this.category4 = category4;
//	}
//
//	public String getCategory5() {
//		return category5;
//	}
//
//	public void setCategory5(String category5) {
//		this.category5 = category5;
//	}
//	
	

}
