package ccrpack.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.persister.collection.mutation.RowMutationOperations.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ccrpack.entity.Candidate;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandidateRepo;
import ccrpack.repo.CompanyRepo;
import ccrpack.repo.HrRepo;
import ccrpack.repo.RatingRepo;
import ccrpack.service.Ccrservice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@CrossOrigin
@RestController
public class Ccrcontroller {
	@Autowired
	CompanyRepo comp;

	@Autowired
	HrRepo hri;

	@Autowired
	RatingRepo ri;

	@Autowired
	CandidateRepo candinter;

	@Autowired
	Ccrservice ccrservice;

	Hr hra = new Hr();
	Company cm = new Company();
	RatingForm rf = new RatingForm();
	Candidate cand = new Candidate();

	@PersistenceContext
	EntityManager entityManager;

    /////////Yash///////////////////////

//	@PostMapping(value = "/candidateregi")
//	public ResponseEntity<String> candregi(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password,
//			@RequestParam String candidate_name, @RequestParam String candidate_email,
//			@RequestParam String candidate_phone, @RequestParam String candidate_dob)
//	{
//			return ccrservice.candregi(candidate_aadhar, candidate_password,candidate_name,candidate_email,candidate_phone,candidate_dob);
//	}
	
	
//	@PostMapping(value = "/candidatelogin")
//	public ResponseEntity<String> candlogin(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password)
//	{
//		return ccrservice.candlogin(candidate_aadhar, candidate_password);
//	}
//	
	
	
	@PostMapping(value = "/candidatelogin")
	public ResponseEntity<?> candlogin(@RequestBody Candidate candidate)
	{
		return ccrservice.candlogin(candidate);
	}
	
	@PutMapping(value = "/candchangepass")
	public ResponseEntity<String> changePassword(@RequestParam int candidate_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
			return ccrservice.changePassword(candidate_id, currentpass,newpass);
	}


//	@PostMapping(value = "/addcomapny")
//	public ResponseEntity<String> companyreg(@RequestParam String cname, @RequestParam Long tan,
//			@RequestParam String hr_name, @RequestParam Long phone, @RequestParam String role) {
//
//		return ccrservice.companyreg(cname, tan, hr_name, phone, role);
//
//	}
	
	@PostMapping(value = "/addcomapny")
	public ResponseEntity<String> companyReg(@RequestBody Company company) {

		return ccrservice.companyReg(company);

	}

	@PostMapping(value = "/rating")
	public ResponseEntity<String> Rating(@RequestParam Boolean q1, @RequestParam Boolean q2, @RequestParam int total,
			@RequestParam int candidate_id, @RequestParam int rec_id) {

		return ccrservice.Rating(q1, q2, total, candidate_id, total, rec_id);

	}

	@GetMapping(value = "/getnew")
	public List<RatingForm> Getrequest(@RequestParam int rec_id) {

		return ccrservice.Getrequest(rec_id);

	}

	@PostMapping(value = "/addAdminrecruiter")
	public ResponseEntity<String> AddAdminrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
			@RequestParam boolean approver, @RequestParam boolean add_team) {

		return ccrservice.AddAdminrecruiter(hrid, hr_name, approver, add_team);

	}

	@PostMapping(value = "/addTLrecruiter")
	public ResponseEntity<String> AddTLrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
			@RequestParam boolean approver, @RequestParam boolean add_team) {

		return ccrservice.AddTLrecruiter(hrid, hr_name, approver, add_team);

	}

	@PostMapping(value = "/changeapprover")
	public ResponseEntity<String> ChangeApprover(@RequestParam Integer hrid, @RequestParam String hr_email) {

		return ccrservice.ChangeApprover(hrid, hr_email);

	}
	
	//Register Candidate to CCR
	@PostMapping(value ="/registercandidate")
	 public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate) {
		 return ccrservice.registerCandidate(candidate);
		}
}