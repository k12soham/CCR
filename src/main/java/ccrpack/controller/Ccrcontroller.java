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

//	 @PostMapping(value ="/addcandidate")
//     public ResponseEntity<String> createCandidate(@RequestBody Candidate candidate) {
//         ccrService.createCandidate(candidate);
//         return ResponseEntity.status(HttpStatus.CREATED).body("Candidate data submitted");
//     }

	@PostMapping(value = "/candidateregi")
	public ResponseEntity<String> candregi(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password,
			@RequestParam String candidate_name, @RequestParam String candidate_email,
			@RequestParam String candidate_phone, @RequestParam String candidate_dob) {

		Session session = entityManager.unwrap(Session.class);
		cand.setCandidate_name(candidate_name);
		cand.setCandidate_aadhar(candidate_aadhar);
		cand.setCandidate_password(candidate_password);
		cand.setCandidate_dob(candidate_dob);
		cand.setCandidate_email(candidate_email);
		cand.setCandidate_phone(candidate_phone);
		System.out.println("registeration sucess");
		session.save(cand);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate registered");
	}

	@PostMapping(value = "/candidatelogin")
	public ResponseEntity<?> candlogin(@RequestParam Long candidate_aadhar, @RequestParam String candidate_password) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);
		cr.select(root).where(cb.equal(root.get("candidate_aadhar"), candidate_aadhar),
				cb.equal(root.get("candidate_password"), candidate_password));
		TypedQuery<Candidate> query = session.createQuery(cr);
		Candidate cand = ((org.hibernate.query.Query<Candidate>) query).uniqueResult();
		if (cand != null) {
			System.out.println("Login Candidate success");
			session.save(cand);
		} else {
			System.out.println("Candidate not found");
		}
		session.close();
		return new ResponseEntity<>(cand, HttpStatus.OK);
	}

	@PutMapping(value = "/candchangepass")
	public ResponseEntity<?> changePassword(@RequestParam int candidate_id, @RequestParam String currentpass,
			@RequestParam String newpass) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);
		Root<Candidate> root = cr.from(Candidate.class);
		cr.select(root).where(cb.equal(root.get("candidate_id"), candidate_id),
				cb.equal(root.get("candidate_password"), currentpass));
		Query query = session.createQuery(cr);
		Candidate results = null;
		try {
			results = (Candidate) query.getSingleResult();
			cand = candinter.getById(candidate_id);
			cand.setCandidate_password(newpass);
			candinter.save(cand);
			session.close();
			return new ResponseEntity<>(cand, HttpStatus.OK);
		} catch (NoResultException e) {
			session.close();
			return (ResponseEntity<?>) ResponseEntity.badRequest().body(" Current Password doesn't match...");
		}
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

}
