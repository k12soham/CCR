package ccrpack.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ccrpack.entity.Candidate;
import ccrpack.entity.CcrAdmin;
import ccrpack.entity.Company;
import ccrpack.entity.Hr;
import ccrpack.entity.RatingForm;
import ccrpack.repo.CandidateRepo;
import ccrpack.repo.CcrRepo;
import ccrpack.repo.CompanyRepo;
import ccrpack.repo.HrRepo;
import ccrpack.repo.RatingRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class Ccrservice {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	HrRepo hrRepo;

	@Autowired
	RatingRepo ratingRepo;

	@Autowired
	CandidateRepo candidateRepo;
	
	
	@Autowired
	CcrRepo ccrRepo;

	Hr hr = new Hr();
	Company company = new Company();
	RatingForm ratingForm = new RatingForm();
	Candidate candidate = new Candidate();
	CcrAdmin ccrAdmin = new CcrAdmin();
	
	
	@PersistenceContext
	EntityManager entityManager;

	public ResponseEntity<String> ccrLogin(CcrAdmin ccrAdmin) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<CcrAdmin> cr = cb.createQuery(CcrAdmin.class);

			Root<CcrAdmin> root = cr.from(CcrAdmin.class);
			cr.select(root).where(cb.equal(root.get("ccr_email"), ccrAdmin.getCcr_email()),
					cb.equal(root.get("ccr_password"), ccrAdmin.getCcr_password()));

			TypedQuery<CcrAdmin> query = session.createQuery(cr);

			ccrAdmin = query.getSingleResult();

			if (ccrAdmin != null) {
				session.close();
				return ResponseEntity.status(HttpStatus.OK).body("CCR Admin Login Sucessfully....");
			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}
	

	public ResponseEntity<String> candlogin(Candidate candidate) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Candidate> cr = cb.createQuery(Candidate.class);

			Root<Candidate> root = cr.from(Candidate.class);
			cr.select(root).where(cb.equal(root.get("candidate_email"), candidate.getCandidate_email()),
					cb.equal(root.get("candidate_password"), candidate.getCandidate_password()));

			TypedQuery<Candidate> query = session.createQuery(cr);

			candidate = query.getSingleResult();

			if (candidate != null) {
				session.close();
				return ResponseEntity.status(HttpStatus.OK).body("Candidate Login Sucessfully");
			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}
	

	public ResponseEntity<String> registerCandidate(Candidate candidate) {
		Session session = entityManager.unwrap(Session.class);

		candidate.setCandidate_name(candidate.getCandidate_name());
		candidate.setCandidate_aadhar(candidate.getCandidate_aadhar());
		candidate.setCandidate_password(candidate.getCandidate_password());
		candidate.setCandidate_dob(candidate.getCandidate_dob());
		candidate.setCandidate_email(candidate.getCandidate_email());
		candidate.setCandidate_phone(candidate.getCandidate_phone());

		session.save(candidate);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Candidate Registered sucessfully");
	}

	

	public ResponseEntity<String> hrlogin(Hr hr2) {
		Session session = entityManager.unwrap(Session.class);
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);

			Root<Hr> root = cr.from(Hr.class);
			cr.select(root).where(cb.equal(root.get("hr_email"), hr.getHr_email()),
					cb.equal(root.get("hr_password"), hr.getHr_password()));

			TypedQuery<Hr> query = session.createQuery(cr);

			hr = query.getSingleResult();

			if (hr != null) {
				session.close();
				return ResponseEntity.status(HttpStatus.OK).body("Hr Login Sucessfully");
			}
		} catch (Exception e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
		}
		return null;
	}
	
	

	public ResponseEntity<String> companyReg(Company company) {
		Session session = entityManager.unwrap(Session.class);

		hr.setHr_name(company.getHr().getHr_name());
		hr.setHr_phone(company.getHr().getHr_phone());
		hr.setHr_email(company.getHr().getHr_email());
		hr.setHr_password(company.getHr().getHr_password());
		hr.setHr_role(company.getHr().getHr_role());
		hr.setRatingform(company.getHr().getRatingform());

		company.setCompany_name(company.getCompany_name());
		company.setCompany_address(company.getCompany_address());
		company.setCompany_phone(company.getCompany_phone());
		company.setCompany_tan(company.getCompany_tan());

		company.setHr(hr);
		session.save(hr);
		session.save(company);
		return ResponseEntity.status(HttpStatus.CREATED).body("comapny registered");
	}

	
	
	
	////////////////////////////
	public ResponseEntity<String> Rating(Boolean q1, Boolean q2, int total, int candidate_id, int total2, int rec_id) {
		Session session = entityManager.unwrap(Session.class);

		ratingForm.setQ1(q1);
		ratingForm.setQ2(q2);
		ratingForm.setRating_total(total);
		candidate.setCandidate_id(candidate_id);
		hr.setHr_id(rec_id);
		ratingForm.setCandidate(candidate);
		ratingForm.setHr(hr);

		hr = hrRepo.getById(rec_id);
		int a = hr.getApprover();
		hr = hrRepo.getById(a);
		int b = hr.getHr_id();

		ratingForm.setNew_request(true);
		ratingForm.setApprover_id(b);

		session.save(ratingForm);
		hrRepo.save(hr);
		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Rating added");

	}

	public List<RatingForm> Getrequest(int rec_id) {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<RatingForm> cr = cb.createQuery(RatingForm.class);
		Root<RatingForm> root = cr.from(RatingForm.class);

		cr.select(root).where((cb.equal(root.get("new_request"), true)), ((cb.equal(root.get("approver_id"), rec_id))));

		Query query = session.createQuery(cr);
		List<RatingForm> results = query.getResultList();

		session.close();
		return results;

	}

	public ResponseEntity<String> AdminAddrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {

		Session session = entityManager.unwrap(Session.class);

		hr.setHr_name(hr_name);
		hr.setAdded_by(hrid);
		session.save(hr);
		if (approver == true && add_team == true) {
			int a = hr.getHr_id();
			System.out.println(a);
			hr.setApprover(a);

			hr.setHr_role("Admin");
		} else if (approver == false && add_team == true) {
			hr.setApprover(hrid);
			hr.setHr_role("TeamLead");
		} else {
			hr.setApprover(hrid);
			hr.setHr_role("Rec");
		}

		hrRepo.save(hr);

		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("Admin saved");
	}

	public ResponseEntity<String> TLAddrecruiter(Integer hrid, String hr_name, boolean approver, boolean add_team) {
		Session session = entityManager.unwrap(Session.class);

		hr.setHr_name(hr_name);
		hr.setAdded_by(hrid);
		session.save(hr);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);
		Root<Hr> root = cr.from(Hr.class);
		cr.select(root).where((cb.equal(root.get("hr_admin_id"), hrid)));
		Query query = session.createQuery(cr);
		Hr z = (Hr) query.getSingleResult();
		int b = z.getApprover();
		System.out.println(b);

		int a = 5;

		if (approver == false && add_team == true) {
			hr.setApprover(b);
			hr.setHr_role("TeamLead");
		} else {
			hr.setApprover(b);
			hr.setHr_role("Rec");
		}

		hrRepo.save(hr);

		session.close();
		return ResponseEntity.status(HttpStatus.CREATED).body("TL saved");

	}

	public ResponseEntity<String> ChangeApprover(Integer hrid, String hr_email) {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<Hr> cr = cb.createQuery(Hr.class);
		Root<Hr> root = cr.from(Hr.class);
		cr.select(root).where((cb.equal(root.get("hr_email"), hr_email)));
		Query query = session.createQuery(cr);
		Hr z = (Hr) query.getSingleResult();
		System.out.println(z);
		if (z == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");

		} else {

			z.setApprover(hrid);
			hrRepo.save(z);
			return ResponseEntity.status(HttpStatus.CREATED).body("Approver changed");

		}

	}

	public ResponseEntity<String> changePassword(int candidate_id, String currentpass, String newpass) {

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
			candidate = candidateRepo.getById(candidate_id);
			candidate.setCandidate_password(newpass);
			candidateRepo.save(candidate);
			session.close();

			return ResponseEntity.status(HttpStatus.CREATED).body("Password Changed Sucessfully");
		} catch (NoResultException e) {
			session.close();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current password doesnt matched");
		}

	}



}