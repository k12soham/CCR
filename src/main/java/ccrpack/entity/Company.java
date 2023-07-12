package ccrpack.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer company_id;
	private String company_name;
	private String company_address;
	private Long company_phone;
	private Long company_tan;

	@OneToOne(mappedBy = "company")
	private Hr hr;

	@OneToMany(mappedBy = "company")
	@JsonIgnore
	private List<RatingForm> ratingform;

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(Integer company_id, String company_name, String company_address, Long company_phone,
			Long company_tan, Hr hr, List<RatingForm> ratingform) {
		super();
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_address = company_address;
		this.company_phone = company_phone;
		this.company_tan = company_tan;
		this.hr = hr;
		this.ratingform = ratingform;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}

	public Long getCompany_phone() {
		return company_phone;
	}

	public void setCompany_phone(Long company_phone) {
		this.company_phone = company_phone;
	}

	public Long getCompany_tan() {
		return company_tan;
	}

	public void setCompany_tan(Long company_tan) {
		this.company_tan = company_tan;
	}

	public Hr getHr() {
		return hr;
	}

	public void setHr(Hr hr) {
		this.hr = hr;
	}

	public List<RatingForm> getRatingform() {
		return ratingform;
	}

	public void setRatingform(List<RatingForm> ratingform) {
		this.ratingform = ratingform;
	}

}
