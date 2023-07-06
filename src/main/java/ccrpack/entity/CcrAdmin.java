package ccrpack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//This is for CCR Admin & Super Admin

@Entity
public class CcrAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ccr_admin_id;
	private String ccr_name;
	private Long ccr_phone;
	private String ccr_email;
	private String ccr_password;
	private String ccr_role;

	public CcrAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CcrAdmin(Integer ccr_admin_id, String ccr_name, Long ccr_phone, String ccr_email, String ccr_password,
			String ccr_role) {
		super();
		this.ccr_admin_id = ccr_admin_id;
		this.ccr_name = ccr_name;
		this.ccr_phone = ccr_phone;
		this.ccr_email = ccr_email;
		this.ccr_password = ccr_password;
		this.ccr_role = ccr_role;
	}

	public Integer getCcr_admin_id() {
		return ccr_admin_id;
	}

	public void setCcr_admin_id(Integer ccr_admin_id) {
		this.ccr_admin_id = ccr_admin_id;
	}

	public String getCcr_name() {
		return ccr_name;
	}

	public void setCcr_name(String ccr_name) {
		this.ccr_name = ccr_name;
	}

	public Long getCcr_phone() {
		return ccr_phone;
	}

	public void setCcr_phone(Long ccr_phone) {
		this.ccr_phone = ccr_phone;
	}

	public String getCcr_email() {
		return ccr_email;
	}

	public void setCcr_email(String ccr_email) {
		this.ccr_email = ccr_email;
	}

	public String getCcr_password() {
		return ccr_password;
	}

	public void setCcr_password(String ccr_password) {
		this.ccr_password = ccr_password;
	}

	public String getCcr_role() {
		return ccr_role;
	}

	public void setCcr_role(String ccr_role) {
		this.ccr_role = ccr_role;
	}

}
