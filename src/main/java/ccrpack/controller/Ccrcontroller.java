package ccrpack.controller;

import java.util.List;

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
import ccrpack.entity.CcrAdmin;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandidateRepo;
import ccrpack.repo.CompanyRepo;
import ccrpack.repo.HrRepo;
import ccrpack.repo.RatingRepo;
import ccrpack.service.Ccrservice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@CrossOrigin(origins="http://localhost:3000")
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

	// Login CCR Admin
	@PostMapping(value = "/ccradminlogin")
	public ResponseEntity<String> ccrLogin(@RequestBody CcrAdmin ccrAdmin) {
		return ccrservice.ccrLogin(ccrAdmin);
	}

	// Login Candidate
	@PostMapping(value = "/candidatelogin")
	public ResponseEntity<String> candlogin(@RequestBody Candidate candidate) {
		return ccrservice.candlogin(candidate);
	}

	// Register Candidate
	@PostMapping(value = "/registercandidate")
	public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate) {
		return ccrservice.registerCandidate(candidate);
	}
	
	// HR Login
		@PostMapping(value = "/hrlogin")
		public ResponseEntity<String> hrlogin(@RequestBody Hr hr) {
			return ccrservice.hrlogin(hr);
		}

	
	//Company & HR Registration
	@PostMapping(value = "/addcomapny")
	public ResponseEntity<String> companyReg(@RequestBody Company company) {

		return ccrservice.companyReg(company);

	}

	//rating form data save for yes no questions and calculate total score 
	@PostMapping(value = "/saveYesNo")
	public ResponseEntity<String> saveYesNoAns(@RequestBody RatingForm ratingForm){
		return ccrservice.saveYesNoAns(ratingForm);
	}

	
  //////////////////////////////////

	@PutMapping(value = "/candchangepass")
	public ResponseEntity<String> changePassword(@RequestParam int candidate_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
		return ccrservice.changePassword(candidate_id, currentpass, newpass);
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

	@PostMapping(value = "/Adminaddrecruiter")
	public ResponseEntity<String> AdminAddrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
			@RequestParam boolean approver, @RequestParam boolean add_team) {

		return ccrservice.AdminAddrecruiter(hrid, hr_name, approver, add_team);

	}

	@PostMapping(value = "/TLaddrecruiter")
	public ResponseEntity<String> TLAddrecruiter(@RequestParam Integer hrid, @RequestParam String hr_name,
			@RequestParam boolean approver, @RequestParam boolean add_team) {

		return ccrservice.TLAddrecruiter(hrid, hr_name, approver, add_team);

	}

	@PostMapping(value = "/changeapprover")
	public ResponseEntity<String> ChangeApprover(@RequestParam Integer hrid, @RequestParam String hr_email) {

		return ccrservice.ChangeApprover(hrid, hr_email);

	}

}